package com.rivaldofez.consumerapp.listener

import android.view.View
import com.rivaldofez.consumerapp.model.User

interface OnItemClickListener {
    fun onItemClick(item: View, userSearch: User)
}