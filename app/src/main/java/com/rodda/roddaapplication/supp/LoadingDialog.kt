package com.rodda.roddaapplication.supp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.View
import com.rodda.roddaapplication.R
import com.rodda.roddaapplication.databinding.LoadingScreenBinding

class LoadingDialog(private val mActivity: Activity){
    private lateinit var dialog: AlertDialog

    private val loadingScreenBinding = LoadingScreenBinding.inflate(mActivity.layoutInflater)


    fun startDialog(){
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(loadingScreenBinding.root)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    fun startReportDialog(text : String){
        loadingScreenBinding.tvLoading.text = text
        val builder = AlertDialog.Builder(mActivity)
        builder.setView(loadingScreenBinding.root)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    fun finishDialog(state : Boolean) {
        loadingScreenBinding.pbLoading.visibility = View.INVISIBLE
        loadingScreenBinding.imgState.visibility = View.VISIBLE
        loadingScreenBinding.btnClear.visibility = View.VISIBLE
        if (state) {
            loadingScreenBinding.imgState.setImageResource(R.drawable.ic_baseline_check_24)
            loadingScreenBinding.tvLoading.text = "Laporan Anda Berhasil Dikirim"
        } else {
            loadingScreenBinding.imgState.setImageResource(R.drawable.ic_baseline_close_24)
            loadingScreenBinding.tvLoading.text = "Terjadi Kesalahan, Coba Lagi Nanti"
        }
        loadingScreenBinding.btnClear.setOnClickListener {
            dialog.dismiss()
            mActivity.finish()
        }
        dialog.show()
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}