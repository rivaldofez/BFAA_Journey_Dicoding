package com.rivaldofez.consumerapp.network

import com.rivaldofez.consumerapp.model.User
import com.rivaldofez.cubihub.model.DetailUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RetrofitService {
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