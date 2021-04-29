package com.rivaldofez.cubihub.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.model.UserList
import com.rivaldofez.cubihub.repository.SearchUserRepository

class SearchUserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SearchUserRepository(application)
    val showProgress: LiveData<Boolean>
    val errorState : LiveData<Boolean>
    val listSearchedUser : LiveData<UserList>

    init {
        this.listSearchedUser = repository.listSearchedUser
        this.showProgress = repository.showProgress
        this.errorState = repository.errorState
    }

    fun searchUsers(keyword: String){
        repository.searchUsers(keyword)
    }
}