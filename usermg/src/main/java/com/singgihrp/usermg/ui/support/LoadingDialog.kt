package com.singgihrp.usermg.ui.support

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.singgihrp.usermg.R

class LoadingDialog(val mActivity: Activity){
    private lateinit var dialog: AlertDialog

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