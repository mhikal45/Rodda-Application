package com.singgihrp.usermg.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Resulys(
    var name: String,
    var location: String,
    var time: String,
    var image: ArrayList<String>,
    var prediction: ArrayList<String>
):Parcelable
