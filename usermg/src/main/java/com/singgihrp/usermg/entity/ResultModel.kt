package com.singgihrp.usermg.entity

import com.google.firebase.Timestamp

class ResultModel {
    var nama: String? = null
    var lokasi: String? = null
    var waktu: Timestamp? =  null
    var image: ArrayList<String>? = null
    var prediction: ArrayList<String>? = null

    constructor()
}