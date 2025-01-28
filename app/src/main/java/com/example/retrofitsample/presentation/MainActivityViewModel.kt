package com.example.retrofitsample.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitsample.Resource
import com.example.retrofitsample.data.WeatherRepository
import com.example.retrofitsample.domain.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _weatherState = MutableSharedFlow<WeatherState>()//
    var weatherState: SharedFlow<WeatherState> = _weatherState
    private var job: Job? = null

    fun getWeatherDetails(
        url: String, latitude: String, longitude: String, hourly: String
    ) {
        job?.cancel()
        job = viewModelScope.launch(dispatcher) {
            val response = weatherRepository.getWeatherInfo(url, latitude, longitude, hourly)

            when (response) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    withContext(Main) {
                        val data = response.data as List<WeatherModel>
                        _weatherState.emit(WeatherState.Success(data))
                    }
                }

                is Resource.Error -> {
                    withContext(Main) {
                        _weatherState.emit(WeatherState.Error("Error"))
                    }
                }

                is Resource.Internet -> {}
            }
        }
    }
}
