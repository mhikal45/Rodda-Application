package com.singgihrp.usermg.ui.mainpage.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.singgihrp.usermg.databinding.FragmentLogoutBinding
import com.singgihrp.usermg.ui.um.login.LoginActivity


class LogoutFragment : DialogFragment() {

    private lateinit var binding : FragmentLogoutBinding
    private lateinit var fAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentLogoutBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fAuth = FirebaseAuth.getInstance()

        binding.tvNo.setOnClickListener{
            dismiss()
        }
        binding.tvYes.setOnClickListener{
            fAuth.signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }
}