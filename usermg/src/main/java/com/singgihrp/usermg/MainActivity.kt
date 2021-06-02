package com.singgihrp.usermg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.singgihrp.usermg.databinding.ActivityMainBinding
import com.singgihrp.usermg.ui.um.login.LoginActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fAuth = FirebaseAuth.getInstance()

        binding.btnVerifikasi.setOnClickListener{
            fAuth.currentUser!!.sendEmailVerification()
                .addOnSuccessListener {
                    Toast.makeText(this, "Verified", Toast.LENGTH_SHORT).show()
                    binding.btnVerifikasi.visibility = View.GONE
                }
                .addOnFailureListener{

                }
        }
        binding.btnLogot.setOnClickListener{
            fAuth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}