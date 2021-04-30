package com.rivaldofez.consumerapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rivaldofez.consumerapp.model.User
import com.rivaldofez.consumerapp.repository.FollowRepository

class FollowViewModel: ViewModel() {

    private lateinit var repository : FollowRepository
    lateinit var listFollowsUser: LiveData<ArrayList<User>>
    lateinit var showProgress: LiveData<Boolean>

    fun getFollowUser(username: String, option: String) {
        repository.getFollowUser(username, option)
    }

    fun initializeModel(){
        repository = FollowRepository()
        this.listFollowsUser = repository.listFollowsUser
        this.showProgress = repository.showProgress
    }
}