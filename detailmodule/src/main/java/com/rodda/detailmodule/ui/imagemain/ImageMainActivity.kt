package com.rodda.detailmodule.ui.imagemain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodda.detailmodule.R

class ImageMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_image_main, ImageMainFragment.newInstance())
                .commitNow()
        }
    }
}