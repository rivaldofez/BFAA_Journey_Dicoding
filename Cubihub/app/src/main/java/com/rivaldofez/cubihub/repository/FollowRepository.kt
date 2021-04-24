package com.rivaldofez.cubihub.repository

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowRepository(val application: Context) {
    val listFollowsUser = MutableLiveData<ArrayList<User>>()
    val showProgress = MutableLiveData<Boolean>()
    var errorState = false

    fun loadFollowUser(username: String, option: String){
        showProgress.value = true
        val followersItems = ArrayList<User>()

        val client = AsyncHttpClient()
        val url = application.getString(R.string.follow_url,username,option)
        client.addHeader("Authorization", application.getString(R.string.token))
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?
            ) {
                val result = String(responseBody!!)
                try {
                    val item_users = JSONArray(result)
                    followersItems.clear()
                    for(i in 0 until item_users.length()){
                        val item = item_users.getJSONObject(i)
                        val temp = User(
                                login = item.getString("login"),
                                type = item.getString("type"),
                                html_url = item.getString("html_url"),
                                avatar_url = item.getString("avatar_url"),
                                id = item.getInt("id"),
                        )
                        followersItems.add(temp)
                    }
                    listFollowsUser.postValue(followersItems)
                    errorState = false

                    showProgress.value = false
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
            ) {
                errorState = true
                showProgress.value = false
            }
        })
    }

    fun changeState(){
        showProgress.value = !(showProgress.value != null && showProgress.value!!)
    }
}