package com.singgihrp.usermg.ui.mainpage

import android.os.Bundle
import android.view.View
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
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.singgihrp.usermg.R
import com.singgihrp.usermg.databinding.ActivityPageMainBinding
import com.singgihrp.usermg.databinding.NavHeaderPageMainBinding

class PageMainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPageMainBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var fAuth: FirebaseAuth
    private lateinit var name: TextView
    private lateinit var email: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPageMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarPageMain.toolbar)

        firestore = Firebase.firestore
        fAuth = FirebaseAuth.getInstance()
        val userId = fAuth.currentUser?.uid

        binding.appBarPageMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        name = navView.getHeaderView(0).findViewById(R.id.tv_name)
        email = navView.getHeaderView(0).findViewById(R.id.tv_email)

        val navController = findNavController(R.id.nav_host_fragment_content_page_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_logout
            ), drawerLayout
        )
        val documentReference = firestore.collection("users").document(userId.toString())
        documentReference.get()
            .addOnSuccessListener {
                name.text = it.getString("fullName")
                email.text = it.getString("email")
            }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_page_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}