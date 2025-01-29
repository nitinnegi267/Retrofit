package com.example.retrofitsample.presentation

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitsample.Constants
import com.example.retrofitsample.R
import com.example.retrofitsample.data.WeatherApi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity(), RecyclerViewAdapter.ItemClickListener {


    private val viewModel by viewModels<MainActivityViewModel>()// Make lateinit and initialize later.
    private lateinit var mRecyclerView: RecyclerView
    private var mAdapter: RecyclerViewAdapter = RecyclerViewAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        val button = findViewById<Button>(R.id.button)
        mRecyclerView = findViewById(R.id.recyclerview)
        mRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = mAdapter
        }

        button.setOnClickListener {
            fetchWeatherDetails()
            //WeatherApi
        }
        lifecycleScope.launch {
            viewModel.weatherState.collect {
                when (it) {
                    is WeatherState.Success -> {
                        mAdapter.updateData(it.list)
                    }

                    is WeatherState.Error -> {
                        Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

//        viewModel.weatherState.observe(this) {
//
//        }
    }

    private fun fetchWeatherDetails() {
        viewModel.getWeatherDetails(
            Constants.FORECAST,
            "33",
            "33",
            Constants.HOURLY
        )
    }

    override fun onItemClick(view: View?, position: Int) {

    }
}