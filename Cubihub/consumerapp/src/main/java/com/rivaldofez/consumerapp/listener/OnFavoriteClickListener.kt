package com.rivaldofez.consumerapp.listener

import android.view.View
import com.rivaldofez.cubihub.model.DetailUser

interface OnFavoriteClickListener {
    fun onFavoriteDetail(item: View, favoriteUser: DetailUser)
}