package com.rivaldofez.consumerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rivaldofez.consumerapp.repository.DetailUserRepository
import com.rivaldofez.cubihub.model.DetailUser

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