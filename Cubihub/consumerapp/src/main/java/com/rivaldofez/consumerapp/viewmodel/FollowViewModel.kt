package com.rivaldofez.cubihub.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.repository.FollowRepository

class FollowViewModel: ViewModel() {

    private lateinit var repository :FollowRepository
    lateinit var listFollowsUser: LiveData<ArrayList<User>>
    lateinit var showProgress: LiveData<Boolean>
    var errorState = false

    fun getFollowUser(username: String, option: String) {
        repository.getFollowUser(username, option)
    }

    fun initializeModel(context: Context){
        repository = FollowRepository(context)
        this.listFollowsUser = repository.listFollowsUser
        this.showProgress = repository.showProgress
    }
}