package com.rivaldofez.cubihub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User(
    val login: String,
    val type: String,
    val html_url: String,
    val avatar_url: String,
    val id: Int,
):Parcelable