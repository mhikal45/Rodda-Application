package com.rodda.detailmodule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodda.detailmodule.ui.dataform.DataFormFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DataFormFragment.newInstance())
                .commitNow()
        }
    }
}