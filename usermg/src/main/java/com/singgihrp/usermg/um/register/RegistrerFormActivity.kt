package com.singgihrp.usermg.um.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.singgihrp.usermg.MainActivity
import com.singgihrp.usermg.R
import com.singgihrp.usermg.databinding.ActivityRegistrerFormBinding
import com.singgihrp.usermg.um.login.LoginActivity


class RegistrerFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var registerBinding: ActivityRegistrerFormBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var userID: String

    companion object{
        const val TAG = "Result Regist"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegistrerFormBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        registerBinding.btnRegister.setOnClickListener(this)
        registerBinding.tvLogin.setOnClickListener(this)

    }

    fun register(){
        val fullName = registerBinding.editRealname.text.toString()
        val email = registerBinding.editEmail.text.toString()
        val phone = registerBinding.editNumb.text.toString()
        val password = registerBinding.editPassword.text.toString()

        if(fullName.isEmpty()){
            registerBinding.editRealname.error = "Required"
            return
        }
        if(email.isEmpty()){
            registerBinding.editEmail.error = "Required"
            return
        }
        if(phone.isEmpty()){
            registerBinding.editNumb.error = "Required"
            return
        }
        if(password.isEmpty()){
            registerBinding.editPassword.error = "Required"
            return
        }

        registerBinding.pbRegis.visibility = View.VISIBLE

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener {
                if(it.isSuccessful){
                    val user = hashMapOf(
                        "fullName" to fullName,
                        "email" to email,
                        "phone" to phone
                    )
                    userID = firebaseAuth.currentUser?.uid.toString()
                    db.collection("users")
                        .document(userID)
                        .set(user)
                        .addOnCompleteListener(this, OnCompleteListener {
                            if(it.isSuccessful){
                                Log.d(TAG, "onSuccess: User data added to store")
                            }else{
                                Log.d(TAG, "onFail: User data added to store")
                            }
                        })
                    registerBinding.pbRegis.visibility = View.INVISIBLE
                    Toast.makeText(this, "Berhasil Register", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity ::class.java))
                    finish()
                }else{
                    registerBinding.pbRegis.visibility = View.INVISIBLE
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                }
            })

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_register -> {
                register()
            }
            R.id.tv_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}