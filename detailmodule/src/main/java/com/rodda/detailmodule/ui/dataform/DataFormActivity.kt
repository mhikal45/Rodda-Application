package com.rodda.detailmodule.ui.dataform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodda.detailmodule.R

class DataFormActivity : AppCompatActivity() {
    companion object {
        const val IMAGE_MAIN = "image_main"
        const val IMAGE_DETAIL = "image_detail"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_form)
    }
}