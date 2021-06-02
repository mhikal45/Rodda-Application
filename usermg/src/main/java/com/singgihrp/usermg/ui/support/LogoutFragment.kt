package com.singgihrp.usermg.ui.support

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
        val loading = LoadingDialog(requireActivity())
        val handler = Handler()

        binding.tvNo.setOnClickListener{
            dismiss()
        }
        binding.tvYes.setOnClickListener{
            loading.startDialog()
            handler.postDelayed({
                dismiss()
                loading.dismissDialog()
                fAuth.signOut()
                startActivity(Intent(activity, LoginActivity::class.java))
                Toast.makeText(activity, "Keluar", Toast.LENGTH_SHORT).show()
            }, 2000)
        }
    }
}