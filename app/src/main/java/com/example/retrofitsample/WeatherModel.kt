package com.example.retrofitsample

data class WeatherModel(
    val latitude: String = "",
    val longitude: String = "",
    val time: String,
    val temperature: String,
)