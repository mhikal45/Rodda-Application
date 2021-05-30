package com.singgihrp.usermg.um.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.singgihrp.usermg.MainActivity
import com.singgihrp.usermg.R
import com.singgihrp.usermg.databinding.ActivityLoginBinding
import com.singgihrp.usermg.um.register.RegistrerFormActivity
import kotlin.math.log

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        fbAuth = FirebaseAuth.getInstance()

        loginBinding.tvRegis.setOnClickListener(this)
        loginBinding.btnLogin.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_regis -> {
                startActivity(Intent(this,RegistrerFormActivity::class.java))
            }
            R.id.btn_login -> {
                login()
            }
        }
    }

    fun login(){
        val email = loginBinding.editUsername.text.toString()
        val password = loginBinding.editPassword.text.toString()

        if(email.isEmpty()){
            loginBinding.editUsername.error = "Required"
            return
        }
        if(password.isEmpty()){
            loginBinding.editPassword.text.toString()
            return
        }

        loginBinding.pbLogin.visibility = View.VISIBLE

        fbAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener {
                if(it.isSuccessful){
                    loginBinding.pbLogin.visibility = View.INVISIBLE
                    startActivity(Intent(this, MainActivity::class.java))
                    Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show()

                }else{
                    loginBinding.pbLogin.visibility = View.INVISIBLE
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                }
            })

    }

    override fun onStart() {
        super.onStart()
        if(fbAuth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}