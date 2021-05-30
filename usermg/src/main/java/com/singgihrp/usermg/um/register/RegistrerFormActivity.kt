package com.singgihrp.usermg.um.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.singgihrp.usermg.R
import com.singgihrp.usermg.databinding.ActivityRegistrerFormBinding
import com.singgihrp.usermg.um.login.LoginActivity

class RegistrerFormActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegistrerFormBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrer_form)
        registerBinding = ActivityRegistrerFormBinding.inflate(layoutInflater)

        registerBinding.tvLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}