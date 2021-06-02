package com.rodda.detailmodule.ui.dataform

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.rodda.detailmodule.databinding.ActivityDataFormBinding
import java.lang.Exception
import java.util.*

class DataFormActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_MAIN = "image_main"
        const val IMAGE_DETAIL = "image_detail"

        private const val PERMISSION_ID = 10
    }

    private lateinit var activityDataFormBinding: ActivityDataFormBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val lastLocation : Location = p0.lastLocation
            activityDataFormBinding.etLokasi.setText(getAddressLine(lastLocation.longitude,lastLocation.latitude))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDataFormBinding = ActivityDataFormBinding.inflate(layoutInflater)
        setContentView(activityDataFormBinding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        activityDataFormBinding.btnLocation.setOnClickListener {
            try {
                requestPermission()
                getCurrentLocation()
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
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



}