package com.singgihrp.usermg.um.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.singgihrp.usermg.R
import com.singgihrp.usermg.databinding.ActivityRegistrerFormBinding
import com.singgihrp.usermg.um.login.LoginActivity

class RegistrerFormActivity : AppCompatActivity(){

    lateinit var auth: FirebaseAuth
    private lateinit var registerBinding: ActivityRegistrerFormBinding
    private lateinit var db : FirebaseFirestore

    companion object {
        const val TAG = "Register Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrer_form)
        registerBinding = ActivityRegistrerFormBinding.inflate(layoutInflater)

        db = FirebaseFirestore.getInstance()

        registerBinding.tvLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    fun register(v: View){
        val rName = registerBinding.editRealname.text.toString()
        val email = registerBinding.editEmail.text.toString()
        val numbTelp = registerBinding.editNumb.text.toString()
        val password = registerBinding.editPassword.text.toString()


        //if(rName.isEmpty()||email.isEmpty()||numbTelp.isEmpty()||password.isEmpty()) return
        val user = hashMapOf(
            "full_name" to "AAAA",
            "email" to "AAAAA",
            "no_telp" to "AAAAA",
            "password" to "AAAAA"
        )

        db.collection("Users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
        /*when{
            rName.isEmpty() -> {
                registerBinding.editRealname.error = "Tidak Boleh Kosong"
                return
            }
            email.isEmpty() -> {
                registerBinding.editEmail.error = "Tidak Boleh Kosong"
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                registerBinding.editEmail.error = "Valid Email"
                return
            }
            password.isEmpty() -> {
                registerBinding.editPassword.error = "Tidak Boleh Kosong"
                return
            }
            numbTelp.isEmpty() -> {
                registerBinding.editNumb.error = "Tidak Boleh Kosong"
                return
            }
            else ->{
                val user = hashMapOf(
                    "full_name" to rName,
                    "email" to email,
                    "no_telp" to numbTelp,
                    "password" to password
                )

                db.collection("Users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
        }
*/
    }
}