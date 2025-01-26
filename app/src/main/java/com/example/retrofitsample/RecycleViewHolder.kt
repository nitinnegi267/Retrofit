package com.example.retrofitsample

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleViewHolder(itemView: View, mClickListener: RecyclerViewAdapter.ItemClickListener) : RecyclerView.ViewHolder(itemView) {
    var textViewTime: TextView = itemView.findViewById<TextView>(R.id.textViewTime)
    var textViewTemperature: TextView = itemView.findViewById<TextView>(R.id.textViewTemperature)

    init {
        itemView.setOnClickListener { view ->
            mClickListener.onItemClick(view, adapterPosition)
        }
    }
}
