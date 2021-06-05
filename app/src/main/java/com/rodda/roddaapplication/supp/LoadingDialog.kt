package com.rodda.roddaapplication.supp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.rodda.roddaapplication.MainActivity
import com.rodda.roddaapplication.R
import com.rodda.roddaapplication.databinding.LoadingScreenBinding

class LoadingDialog(private val mActivity: Activity){
    private lateinit var dialog: AlertDialog
//    private val textLoading : TextView = mActivity.findViewById(R.id.tv_loading)
//    private val imageStatus : ImageView = mActivity.findViewById(R.id.img_state)
//    private val progressBar: ProgressBar = mActivity.findViewById(R.id.pb_loading)

    private val loadingScreenBinding = LoadingScreenBinding.inflate(mActivity.layoutInflater)


    fun startDialog(){
        val builder = AlertDialog.Builder(mActivity)
//        val inflater  = mActivity.layoutInflater
        builder.setView(loadingScreenBinding.root)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    fun startReportDialog(text : String){
        loadingScreenBinding.tvLoading.text = text
        val builder = AlertDialog.Builder(mActivity)
//        val inflater  = mActivity.layoutInflater
        builder.setView(loadingScreenBinding.root)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    fun finishDialog(state : Boolean) {
        loadingScreenBinding.pbLoading.visibility = View.INVISIBLE
        loadingScreenBinding.imgState.visibility = View.VISIBLE
        if (state) {
            loadingScreenBinding.imgState.setImageResource(R.drawable.ic_baseline_check_24)
            loadingScreenBinding.tvLoading.text = "Laporan Anda Berhasil Dikirim"
        } else {
            loadingScreenBinding.imgState.setImageResource(R.drawable.ic_baseline_close_24)
            loadingScreenBinding.tvLoading.text = "Terjadi Kesalahan, Coba Lagi Nanti"
        }
        dialog.setCancelable(true)
        dialog.show()
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}