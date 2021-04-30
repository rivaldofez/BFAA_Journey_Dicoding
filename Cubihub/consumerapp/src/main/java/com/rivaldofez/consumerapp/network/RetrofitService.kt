package com.rivaldofez.cubihub.network

import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.model.UserList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("search/users")
    fun searchUsers(
        @Header("Authorization") token : String,
        @Query("q") keyword: String
    ): Call<UserList>

    @GET("users/{username}")
    fun getDetailUser(
        @Header("Authorization") token: String,
        @Path("username") username: String
    ):Call<DetailUser>

    @GET("users/{username}/{option}")
    fun getFollowInfo(
        @Header("Authorization") token: String,
        @Path("username") username: String,
        @Path("option") option: String
    ):Call<List<User>>
}