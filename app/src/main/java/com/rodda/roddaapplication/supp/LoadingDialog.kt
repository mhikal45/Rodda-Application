package com.rodda.roddaapplication.supp

import android.app.Activity
import android.app.AlertDialog
import com.rodda.roddaapplication.R

class LoadingDialog(val mActivity: Activity){
    private lateinit var dialog: AlertDialog

    companion object{
        fun getInstance(title: String){

        }
    }

    fun startDialog(){
        val builder = AlertDialog.Builder(mActivity)
        val inflater  = mActivity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_screen, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}