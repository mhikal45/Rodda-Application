package com.rodda.firstpage.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String,
    val email: String,
    val fullName: String,
    val phone: String
): Parcelable
