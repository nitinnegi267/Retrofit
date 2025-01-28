package com.example.retrofitsample.presentation

import com.example.retrofitsample.Resource
import com.example.retrofitsample.domain.WeatherModel

//data class WeatherState(
//    var isLoading: Boolean = false,
//    var success: Int = 0,
//    var data: List<WeatherModel> = emptyList(),
//    var error: String = "",
//    var internet: Boolean = false
//)

sealed class WeatherState {
    data class Success(val list: List<WeatherModel>) : WeatherState()
    data class Error(val message: String) : WeatherState()
}


