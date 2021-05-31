package com.rodda.detailmodule.ui.imagedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodda.detailmodule.R

class ImageDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_image_detail,ImageDetailFragment.newInstance())
                .commitNow()
        }
    }
}