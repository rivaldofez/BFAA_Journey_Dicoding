package com.rivaldofez.cubihub.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldofez.cubihub.BuildConfig
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.model.UserList
import com.rivaldofez.cubihub.network.RetroInstance
import com.rivaldofez.cubihub.network.RetrofitService
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserRepository(val application: Application) {
    val listSearchedUser = MutableLiveData<UserList>()
    val showProgress = MutableLiveData<Boolean>()
    var errorState = false

    fun searchUsers(keyword: String){
        showProgress.value = true
        val retroInstance = RetroInstance.getRetroFitInstance().create(RetrofitService::class.java)
        val call = retroInstance.searchUsers("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",keyword)
        call.enqueue(object: Callback<UserList>{
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if(response.isSuccessful){
                    listSearchedUser.postValue(response.body())
                }else{
                    listSearchedUser.postValue(null)
                }
                showProgress.value = false
            }

            override fun onFailure(call: Call<UserList>, t: Throwable) {
                listSearchedUser.postValue(null)
                showProgress.value = false
            }
        })
    }

    fun changeState(){
        showProgress.value = !(showProgress.value != null && showProgress.value!!)
    }
}

