package com.example.retrofitsample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.retrofitsample.domain.WeatherModel
import com.example.retrofitsample.presentation.MainActivityViewModel
import com.example.retrofitsample.presentation.WeatherState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {

    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var root: ComposeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.compose_activity)
        root = findViewById(R.id.compose_view)
        fetchWeatherDetails()

        lifecycleScope.launch {
            viewModel.weatherState.collect {
                when (it) {
                    is WeatherState.Success -> {
                        root.setContent {
                            Row {
                                WeatherList(it.list)
                            }
                        }
                    }

                    is WeatherState.Error -> {
                        Toast.makeText(this@ComposeActivity, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    @Composable
    fun WeatherList(list: List<WeatherModel>) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFFEDEADE))
                .padding(start = 10.dp, top = 10.dp, end = 10.dp, bottom = 10.dp)
        ) {

            items(list.size) {

                Card(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color.Red),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 10.dp
                        )
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(
                            start = 20.dp, top = 20.dp, end = 20.dp, bottom = 20.dp
                        )
                    ) {

                        Text(
                            text = "Time : ".plus(list[it].time),
                            color = Color.Black,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(2f)
                        )

                        Text(
                            text = "Temp : ".plus(list[it].temperature),
                            color = Color.Black,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }

    private fun fetchWeatherDetails() {
        viewModel.getWeatherDetails(
            Constants.FORECAST,
            "33",
            "33",
            Constants.HOURLY
        )
    }
}