package com.example.retrofitsample

class WeatherRepository(
    private val api: WeatherApi
) {

    suspend fun getWeatherInfo(
        url: String,
        latitude: String,
        longitude: String,
        hourly: String
    ): List<WeatherModel> {

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
