package com.example.retrofitsample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository

) : ViewModel() {

    private val _weatherDetails = MutableLiveData<List<WeatherModel>>(emptyList())//
    val weatherDetails: LiveData<List<WeatherModel>> = _weatherDetails
    private var job: Job? = null

    fun getWeatherDetails(
        url: String,
        latitude: String,
        longitude: String,
        hourly: String
    ) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            val response = weatherRepository.getWeatherInfo(url, latitude, longitude, hourly)
            withContext(Main) {
                _weatherDetails.value = response
            }
        }
    }
}