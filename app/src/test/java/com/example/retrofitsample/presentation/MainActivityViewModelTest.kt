package com.example.retrofitsample.presentation

import com.example.retrofitsample.Resource
import com.example.retrofitsample.data.WeatherRepository
import com.example.retrofitsample.domain.WeatherModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityViewModelTest {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mockRepo: WeatherRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())
    private val coroutineScope = TestScope(testDispatcher)
    private val dispatcher: CoroutineDispatcher = Dispatchers.Unconfined

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true, relaxed = true)
        Dispatchers.setMain(testDispatcher)

        mockRepo = mockk()
        viewModel = MainActivityViewModel(mockRepo, dispatcher)
    }

    @Test
    fun `test getWeatherDetails for success`() = runTest {

        // GIVEN
        val url = "url"
        val latitude = "latitude"
        val longitude = "longitude"
        val hourly = "hourly"

        val weatherModel1 = WeatherModel("12", "14", "2pm", "22C")
        val weatherModel2 = WeatherModel("16", "18", "4pm", "25C")
        val listWeatherModel = listOf(weatherModel1, weatherModel2)

        coEvery {
            mockRepo.getWeatherInfo(url, latitude, longitude, hourly)
        } returns (Resource.Success(listWeatherModel))

        val emittedList = mutableListOf<WeatherState>()

        coroutineScope.launch { viewModel.weatherState.toList(emittedList) }

        // WHEN
        viewModel.getWeatherDetails(url, latitude, longitude, hourly)

        // THEN
        assertEquals(
            emittedList[0],
            WeatherState.Success(listWeatherModel)
        )
    }

    @Test
    fun `test getWeatherDetails for failure`() {

        // GIVEN
        val url = "url"
        val latitude = "latitude"
        val longitude = "longitude"
        val hourly = "hourly"

        coEvery {
            mockRepo.getWeatherInfo(url, latitude, longitude, hourly)
        } returns (Resource.Error("Error"))

        val emittedList = mutableListOf<WeatherState>()

        coroutineScope.launch { viewModel.weatherState.toList(emittedList) }

        // WHEN
        viewModel.getWeatherDetails(url, latitude, longitude, hourly)

        // THEN
        assertEquals(
            emittedList[0],
            WeatherState.Error("Error")
        )
    }

    @After
    fun tearDown() {

    }

}
