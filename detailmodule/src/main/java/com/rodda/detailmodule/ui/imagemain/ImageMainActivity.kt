package com.rodda.detailmodule.ui.imagemain


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.rodda.detailmodule.databinding.ImageMainActivityBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ImageMainActivity : AppCompatActivity() {

    private lateinit var imageMainBinding : ImageMainActivityBinding
    private lateinit var currentPhotoPath : String

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val bundle = result.data?.extras
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",Locale("indonesia")).format(Date())
            val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            File.createTempFile(
                "JPEG_${timeStamp}",
                ".jpg",
                storageDir
            ).apply {
                currentPhotoPath = absolutePath
            }
            val imageMain = bundle?.get("data") as Bitmap
            imageMainBinding.imgMain.setImageBitmap(imageMain)
            imageMainBinding.btnNextDetail.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageMainBinding = ImageMainActivityBinding.inflate(layoutInflater)
        setContentView(imageMainBinding.root)

        imageMainBinding.btnFotoMain.setOnClickListener {
            val intentPicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startForResult.launch(intentPicture)
            } catch (e : ActivityNotFoundException){
                Toast.makeText(this, e.message,Toast.LENGTH_SHORT).show()
            }
        }
    }
}