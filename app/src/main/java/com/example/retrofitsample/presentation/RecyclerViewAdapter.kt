package com.example.retrofitsample.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitsample.R
import com.example.retrofitsample.domain.WeatherModel

class RecyclerViewAdapter(mainActivity: MainActivity) : RecyclerView.Adapter<RecycleViewHolder>() {

    private var mClickListener: ItemClickListener = mainActivity

    private var weatherModelList = listOf<WeatherModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val mContext = parent.context
        val layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater.inflate(R.layout.recyclerview_row, parent, false)
        return RecycleViewHolder(view, mClickListener)
    }

    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val weatherModel = weatherModelList[position]
        holder.textViewTime.text = weatherModel.time
        holder.textViewTemperature.text = weatherModel.temperature
    }

    override fun getItemCount(): Int {
        return weatherModelList.size
    }

    fun updateData(weatherModel: List<WeatherModel>) {
        weatherModelList = weatherModel
        notifyDataSetChanged()
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}