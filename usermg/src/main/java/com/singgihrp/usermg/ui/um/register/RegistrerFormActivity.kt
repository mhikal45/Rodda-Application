package com.singgihrp.usermg.ui.um.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
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
import com.singgihrp.usermg.ui.mainpage.PageMainActivity
import com.singgihrp.usermg.ui.support.LoadingDialog
import com.singgihrp.usermg.ui.um.login.LoginActivity


class RegistrerFormActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var registerBinding: ActivityRegistrerFormBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var userID: String
    private lateinit var loadingDialog: LoadingDialog

    companion object{
        const val TAG = "Result Regist"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegistrerFormBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        loadingDialog = LoadingDialog(this)

        registerBinding.btnRegister.setOnClickListener(this)
        registerBinding.tvLogin.setOnClickListener(this)

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

    private fun register(){
        val fullName = registerBinding.editRealname.text.toString()
        val email = registerBinding.editEmail.text.toString()
        val counCode = registerBinding.editCountryCode.text.toString()
        val phone = registerBinding.editNumb.text.toString()
        val password = registerBinding.editPassword.text.toString()
        val confirmPassword = registerBinding.editPasswordConfirm.text.toString()
        val fullPhoneNumb = "+$counCode$phone"

        if(fullName.isEmpty()){
            registerBinding.editRealname.error = "Required"
            return
        }
        if(email.isEmpty()){
            registerBinding.editEmail.error = "Required"
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registerBinding.editEmail.error = "Need Email Format"
            return
        }
        if(counCode.isEmpty()){
            registerBinding.editCountryCode.error = "Required"
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
        if(confirmPassword.isEmpty()){
            registerBinding.editPasswordConfirm.error = "Required"
            return
        }
        if(confirmPassword != password){
            registerBinding.editPasswordConfirm.error = "Password doesn't match"
            return
        }

        loadingDialog.startDialog()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, OnCompleteListener {
                if(it.isSuccessful){
                    val user = hashMapOf(
                        "fullName" to fullName,
                        "email" to email,
                        "phone" to fullPhoneNumb
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
                    loadingDialog.dismissDialog()
                    Toast.makeText(this, "Berhasil Register", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, PageMainActivity::class.java))
                    finish()
                }else{
                    loadingDialog.dismissDialog()
                    Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
                }
            })
    }
}