package com.example.retrofitsample

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface WeatherApi {
    @GET
    suspend fun getWeatherDetail(
        @Url url: String,
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("hourly") hourly: String,
    ): WeatherEntity
}
