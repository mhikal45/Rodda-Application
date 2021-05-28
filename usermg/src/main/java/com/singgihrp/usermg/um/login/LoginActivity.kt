package com.singgihrp.usermg.um.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.singgihrp.usermg.R
import com.singgihrp.usermg.databinding.ActivityLoginBinding
import com.singgihrp.usermg.um.register.RegistrerFormActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)

        loginBinding.tvRegis.setOnClickListener{
            startActivity(Intent(this, RegistrerFormActivity::class.java))
        }
    }
}