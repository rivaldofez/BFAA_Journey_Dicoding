package com.rivaldofez.cubihub.listener

import android.view.View
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.model.User

interface OnFavoriteClickListener {
    fun onFavoriteDetail(item: View, favoriteUser: DetailUser)
}