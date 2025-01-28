package com.example.retrofitsample.data

import com.example.retrofitsample.Constants
import com.example.retrofitsample.Resource
import com.example.retrofitsample.domain.WeatherEntity
import com.example.retrofitsample.domain.WeatherModel
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(
    private val api: WeatherApi
) {

    suspend fun getWeatherInfo(
        url: String,
        latitude: String,
        longitude: String,
        hourly: String
    ): Resource<List<WeatherModel>> {

        val response = try {
            val successResponse =
                api.getWeatherDetail(url, latitude, longitude, hourly).toWeatherModel()
            Resource.Success(successResponse)

        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Error")
        }

        return response
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
