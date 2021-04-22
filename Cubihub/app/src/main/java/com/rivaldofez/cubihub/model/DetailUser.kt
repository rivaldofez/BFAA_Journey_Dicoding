package com.rivaldofez.cubihub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUser(
    val login: String,
    val name: String,
    val type: String,
    val id: Int,
    val avatar_url: String,
    val public_repos: Int,
    val followers: Int,
    val location: String,
    val following: Int,
) : Parcelable