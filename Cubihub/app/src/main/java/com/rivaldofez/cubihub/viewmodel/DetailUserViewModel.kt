package com.rivaldofez.cubihub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.repository.DetailUserRepository

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DetailUserRepository()
    val showProgress: LiveData<Boolean>
    val detailUser : LiveData<DetailUser>

    init {
        this.showProgress = repository.showProgress
        this.detailUser = repository.detailUser
    }

    fun loadDetailUser(username :String){
        repository.loadDetailUser(username)
    }
}