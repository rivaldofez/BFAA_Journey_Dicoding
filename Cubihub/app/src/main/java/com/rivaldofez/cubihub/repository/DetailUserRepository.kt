package com.rivaldofez.cubihub.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.rivaldofez.cubihub.BuildConfig
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.network.RetroInstance
import com.rivaldofez.cubihub.network.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserRepository(val application: Application) {
    val detailUser = MutableLiveData<DetailUser>()
    val showProgress = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Boolean>()

    fun loadDetailUser(username :String){
        showProgress.value = true
        val retroInstance = RetroInstance.getRetroFitInstance().create(RetrofitService::class.java)
        val call = retroInstance.getDetailUser(BuildConfig.API_TOKEN, username)
        call.enqueue(object: Callback<DetailUser>{
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                if(response.isSuccessful){
                    detailUser.postValue(response.body())
                }else{
                    detailUser.postValue(null)
                }
                showProgress.value = false
                errorState.value = false
            }
            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                detailUser.postValue(null)
                showProgress.value = false
                errorState.value = true
            }
        })
    }

    fun changeState(){
        showProgress.value = !(showProgress.value != null && showProgress.value!!)
    }
}