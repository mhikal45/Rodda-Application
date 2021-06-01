package com.rodda.detailmodule.ui.imagedetail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rodda.detailmodule.databinding.ActivityImageDetailBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ImageDetailActivity : AppCompatActivity() {

    companion object {
        const val imageMainPath = "image_main_path"
    }

    private lateinit var imageDetailBinding: ActivityImageDetailBinding
    private lateinit var currentPhotoPath : String
    private lateinit var imageAdapter : ImageDetailAdapter

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            imageAdapter.addToList(currentPhotoPath)
            imageDetailBinding.btnNextForm.visibility = View.VISIBLE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageDetailBinding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(imageDetailBinding.root)

        imageAdapter = ImageDetailAdapter()
        imageDetailBinding.rvImgDetail.layoutManager = GridLayoutManager(this,4)
        imageDetailBinding.rvImgDetail.adapter = imageAdapter

        imageDetailBinding.btnFotoDetail.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                    imageMain ->
                val photoFile = try {
                    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("indonesia")).format(
                        Date()
                    )
                    val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    File.createTempFile(
                        "JPEG_${timeStamp}",
                        ".jpg",
                        storageDir
                    ).apply {
                        currentPhotoPath = absolutePath
                    }
                } catch (e : IOException) {
                    Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
                }
                photoFile?.also {
                    val photoURI : Uri = FileProvider.getUriForFile(
                        this,"com.rodda.detailmodule", it as File
                    )
                    imageMain.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
                    startForResult.launch(imageMain)
                }
            }
        }
    }
}