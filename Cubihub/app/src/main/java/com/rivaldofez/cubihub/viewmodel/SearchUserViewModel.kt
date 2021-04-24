package com.rivaldofez.cubihub.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.repository.SearchUserRepository

class SearchUserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SearchUserRepository(application)
    val showProgress: LiveData<Boolean>
    val listSearchedUser : LiveData<ArrayList<User>>

    init {
        this.listSearchedUser = repository.listSearchedUser
        this.showProgress = repository.showProgress
    }

    fun changeState(){
        repository.changeState()
    }

    fun searchUser(query: String){
        repository.searchUser(query)
    }
}