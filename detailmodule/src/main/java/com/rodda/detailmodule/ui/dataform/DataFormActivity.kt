package com.rodda.detailmodule.ui.dataform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodda.detailmodule.R

class DataFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_form)

        if (savedInstanceState == null ) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container_data_form,DataFormFragment.newInstance())
                .commitNow()
        }
    }
}