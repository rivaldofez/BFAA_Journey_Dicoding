package com.rivaldofez.cubihub.repository

import androidx.lifecycle.MutableLiveData
import com.rivaldofez.cubihub.BuildConfig
import com.rivaldofez.cubihub.model.UserList
import com.rivaldofez.cubihub.network.RetroInstance
import com.rivaldofez.cubihub.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserRepository {
    val listSearchedUser = MutableLiveData<UserList>()
    val showProgress = MutableLiveData<Boolean>()
    var errorState = MutableLiveData<Boolean>()

    fun searchUsers(keyword: String){
        showProgress.value = true
        val retroInstance = RetroInstance.getRetroFitInstance().create(RetrofitService::class.java)
        val call = retroInstance.searchUsers(BuildConfig.API_TOKEN,keyword)
        call.enqueue(object: Callback<UserList>{
            override fun onResponse(call: Call<UserList>, response: Response<UserList>) {
                if(response.isSuccessful){
                    listSearchedUser.postValue(response.body())
                }else{
                    listSearchedUser.postValue(null)
                }
                showProgress.value = false
                errorState.value = false
            }
            override fun onFailure(call: Call<UserList>, t: Throwable) {
                listSearchedUser.postValue(null)
                showProgress.value = false
                errorState.value = true
            }
        })
    }
}

