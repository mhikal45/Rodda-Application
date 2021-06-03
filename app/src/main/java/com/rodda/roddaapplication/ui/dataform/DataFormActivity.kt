package com.rodda.roddaapplication.ui.dataform

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.rodda.roddaapplication.R
import com.rodda.roddaapplication.databinding.ActivityDataFormBinding
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DataFormActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val IMAGE_MAIN = "image_main"
        const val IMAGE_DETAIL = "image_detail"

        private const val PERMISSION_ID = 10
    }

    private lateinit var dataViewModel : DataFormViewModel
    private lateinit var activityDataFormBinding: ActivityDataFormBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var imageMain : String? = ""
    private val imageDetail : ArrayList<String> = ArrayList()
    private var firebaseAuth: FirebaseAuth? = null
    private var storage : FirebaseStorage? = null
    private var storageReference : StorageReference? = null
    private var firestore : FirebaseFirestore? = null
    private val uploadUrl = ArrayList<String>()
    private var id = ""
    private var fullName : String? = "Hikal"
    private var time = SimpleDateFormat("dd:MM:yyyy_HH:mm:ss",Locale("Indonesia")).format(Date())
    private var location : String? = "Tegal"

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation : Location = p0.lastLocation
            activityDataFormBinding.etLokasi.setText(getAddressLine(lastLocation.longitude,lastLocation.latitude))
            location = getAddressLine(lastLocation.longitude,lastLocation.latitude)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDataFormBinding = ActivityDataFormBinding.inflate(layoutInflater)
        setContentView(activityDataFormBinding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        dataViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(DataFormViewModel::class.java)

        activityDataFormBinding.btnLocation.setOnClickListener (this)

        firebaseAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storageReference = storage!!.reference

        id = firebaseAuth?.currentUser?.uid.toString()

        val documentReference = firestore?.collection("user")?.document(id)
        documentReference?.get()?.addOnSuccessListener {
            fullName = it.getString("fullName")
        }

        imageMain = intent.getStringExtra(IMAGE_MAIN)
        imageDetail.addAll(intent.getStringArrayListExtra(IMAGE_DETAIL)!!)

        activityDataFormBinding.btnKirim.setOnClickListener(this)
    }

    private fun checkPermission () : Boolean {
        return ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
        ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission () {
        ActivityCompat.requestPermissions(this,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),
        PERMISSION_ID)
    }

    private fun isLocationEnable () : Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun getCurrentLocation () {
        if (checkPermission()) {
            if (isLocationEnable()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task ->
                    val location : Location? = task.result
                    if (location == null) {
                        val requestLocation = newLocationData()
                        fusedLocationProviderClient.requestLocationUpdates(requestLocation,locationCallback,
                            Looper.myLooper()!!)
                    } else {
                        activityDataFormBinding.etLokasi.setText(getAddressLine(location.longitude,location.latitude))
                        this.location = getAddressLine(location.longitude,location.latitude)
                    }
                }
            } else {
                Toast.makeText(this,"Silahkan nyalakan GPS anda terlebih dahulu",Toast.LENGTH_SHORT).show()
            }
        }
        else {
            requestPermission()
        }
    }

    private fun newLocationData(): LocationRequest {
        return LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }
    }

    private fun getAddressLine(longitude: Double, latitude: Double): String {
        Log.d("Longtitude",longitude.toString())
        Log.d("Latitude",latitude.toString())
        val geocoder = Geocoder(this, Locale.getDefault())
        val address = geocoder.getFromLocation(latitude, longitude, 1)

        return address[0].getAddressLine(0)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
             R.id.btn_kirim-> {
                activityDataFormBinding.progressbar.visibility = View.VISIBLE
                if (imageMain != null && imageDetail.isNotEmpty()) {
                    val mainRef = storageReference!!.child("images/*")
                    mainRef.putFile(Uri.fromFile(File(imageMain!!))).addOnSuccessListener {
                        mainRef.downloadUrl.addOnCompleteListener {
                            uploadUrl.add(it.toString())
                        }
                    }
                    for (i in imageDetail) {
                        val imageRef = storageReference!!.child("images/*")
                        imageRef.putFile(Uri.fromFile(File(i))).addOnSuccessListener {
                            imageRef.downloadUrl.addOnCompleteListener {
                                uploadUrl.add(it.toString())
                                if (uploadUrl.size == imageDetail.size) {
                                    activityDataFormBinding.progressbar.visibility = View.INVISIBLE
                                    dataViewModel.postReport(fullName!!,location!!,time,uploadUrl)
                                    Toast.makeText(this,"Berhasil Mengirim Laporan",Toast.LENGTH_SHORT).show()
                                }
                            }.addOnCanceledListener {
                                activityDataFormBinding.progressbar.visibility = View.INVISIBLE
                                Toast.makeText(this,"Gagal mendapatkan link",Toast.LENGTH_SHORT).show()
                            }
                        }.addOnFailureListener{
                            activityDataFormBinding.progressbar.visibility = View.INVISIBLE
                            Log.d("Gagal upload",it.message!!)
                            Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            R.id.btn_location-> {
                try {
                    requestPermission()
                    getCurrentLocation()
                } catch (e : Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


}