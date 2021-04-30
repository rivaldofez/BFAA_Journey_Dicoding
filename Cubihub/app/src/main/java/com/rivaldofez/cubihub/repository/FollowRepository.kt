package com.rivaldofez.cubihub.repository

import androidx.lifecycle.MutableLiveData
import com.rivaldofez.cubihub.BuildConfig
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.network.RetroInstance
import com.rivaldofez.cubihub.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowRepository {
    val listFollowsUser = MutableLiveData<ArrayList<User>>()
    val showProgress = MutableLiveData<Boolean>()

    fun getFollowUser(username: String, option: String){
        showProgress.value = true
        val retroInstance = RetroInstance.getRetroFitInstance().create(RetrofitService::class.java)
        val call = retroInstance.getFollowInfo(BuildConfig.API_TOKEN,username,option)
        call.enqueue(object : Callback<List<User>>{
            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                if(response.isSuccessful){
                    listFollowsUser.postValue(response.body() as ArrayList<User>?)
                }else{
                    listFollowsUser.postValue(null)
                }
                showProgress.value = false
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                listFollowsUser.postValue(null)
                showProgress.value = false
            }
        })
    }
}