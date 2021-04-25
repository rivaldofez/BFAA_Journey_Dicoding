package com.rivaldofez.cubihub.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.model.UserList
import com.rivaldofez.cubihub.repository.DetailUserRepository
import com.rivaldofez.cubihub.repository.SearchUserRepository
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DetailUserRepository(application)
    val showProgress: LiveData<Boolean>
    val detailUser : LiveData<DetailUser>

    init {
        this.showProgress = repository.showProgress
        this.detailUser = repository.detailUser
    }

    fun changeState(){
        repository.changeState()
    }

    fun loadDetailUser(username :String){
        repository.loadDetailUser(username)
    }
}