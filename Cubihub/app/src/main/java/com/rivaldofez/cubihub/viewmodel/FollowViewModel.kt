package com.rivaldofez.cubihub.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.repository.FollowRepository
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowViewModel: ViewModel() {

    private lateinit var repository :FollowRepository
    lateinit var listFollowsUser: LiveData<ArrayList<User>>
    lateinit var showProgress: LiveData<Boolean>
    var errorState = false

    fun changeState(){
        repository.changeState()
    }

    fun loadFollowUser(username: String, option: String) {
        repository.loadFollowUser(username, option)
    }

    fun initializeModel(context: Context){
        repository = FollowRepository(context)
        this.listFollowsUser = repository.listFollowsUser
        this.showProgress = repository.showProgress
    }

}