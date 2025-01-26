package com.example.retrofitsample

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    suspend fun getWeatherInfo(
        url: String,
        latitude: String,
        longitude: String,
        hourly: String
    ): List<WeatherModel> {

        val api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASEURL)
            .build()
            .create(WeatherApi::class.java)

        return api.getWeatherDetail(url, latitude, longitude, hourly).toWeatherModel()
    }
}

fun WeatherEntity.toWeatherModel(): List<WeatherModel> {
    return this.hourly.time.mapIndexed { index, time ->
        WeatherModel(
            time = time,
            temperature = this.hourly.temperature2m[index]
        )
    }
}
