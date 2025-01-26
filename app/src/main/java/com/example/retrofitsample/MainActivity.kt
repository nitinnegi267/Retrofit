package com.example.retrofitsample

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
        }

        viewModel.weatherDetails.observe(this) {
            mAdapter.updateData(it)
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

    override fun onItemClick(view: View?, position: Int) {

    }
}