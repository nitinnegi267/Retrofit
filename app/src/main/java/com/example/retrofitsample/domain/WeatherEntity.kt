package com.example.retrofitsample.domain

import com.google.gson.annotations.SerializedName

data class WeatherEntity(
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("elevation")
    val elevation: String,
    @SerializedName("hourly_units")
    val hourlyUnits: HourlyUnits,
    @SerializedName("hourly")
    val hourly: Hourly,
    @SerializedName("reason")
    val reason: String?
) {
    data class HourlyUnits(
        @SerializedName("time")
        val time: String,
        @SerializedName("temperature_2m")
        val temperature2m: String,
    )

    data class Hourly(
        @SerializedName("time")
        val time: List<String> = emptyList(),
        @SerializedName("temperature_2m")
        val temperature2m: List<String> = emptyList(),
    )
}
