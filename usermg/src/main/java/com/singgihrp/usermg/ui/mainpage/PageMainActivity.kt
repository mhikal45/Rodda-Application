package com.singgihrp.usermg.ui.mainpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.singgihrp.usermg.R
import com.singgihrp.usermg.databinding.ActivityPageMainBinding
import com.singgihrp.usermg.ui.support.LoadingDialog
import kotlin.system.exitProcess

class PageMainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPageMainBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var fAuth: FirebaseAuth

    companion object{
        const val TAG = "Page Main"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPageMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarPageMain.toolbar)

        firestore = Firebase.firestore
        fAuth = FirebaseAuth.getInstance()
        val userId = fAuth.currentUser?.uid
        val loadingDialog = LoadingDialog(this)

        loadingDialog.startDialog()

        binding.appBarPageMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val name: TextView = navView.getHeaderView(0).findViewById(R.id.tv_name)
        val phone: TextView = navView.getHeaderView(0).findViewById(R.id.tv_phone)
        val email: TextView = navView.getHeaderView(0).findViewById(R.id.tv_email)

        val navController = findNavController(R.id.nav_host_fragment_content_page_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_logout
            ), drawerLayout
        )
        val documentReference = firestore.collection("users").document(userId.toString())
        documentReference.get()
            .addOnSuccessListener {
                loadingDialog.dismissDialog()
                name.text = it.getString("fullName")
                phone.text = it.getString("phone")
                email.text = it.getString("email")
            }.addOnFailureListener{
                loadingDialog.dismissDialog()
                Log.e(TAG, it.toString())
            }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_page_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        moveTaskToBack(true);
        exitProcess(-1)
    }
}