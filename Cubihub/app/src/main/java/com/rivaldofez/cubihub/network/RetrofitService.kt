package com.rivaldofez.cubihub.network

import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.model.UserList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {

//    @Header("Authorization")
//    @GET("search/users?q=msuva")
//    fun getSearchedUsers(): Call<UserList>

    @GET("search/users")
    fun searchUsers(
        @Header("Authorization") token : String,
        @Query("q") keyword: String
    ): Call<UserList>
}