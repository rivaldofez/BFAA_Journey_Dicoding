package com.rivaldofez.myworkmanager

import com.squareup.moshi.Json

data class Response(
    val id: Int,
    val name: String,
    @Json(name = "weather")
    val weatherList: List<Weather>,
    val main: Main,
)