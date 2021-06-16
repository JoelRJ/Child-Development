package com.example.childdevelopment.overview

import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.childdevelopment.R
import com.example.childdevelopment.overview.allAgesList

class AgesAdapter : RecyclerView.Adapter<AgesAdapter.AgesViewHolder>(){
    class AgesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.text_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgesViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.linear_view_item, parent, false)

        return AgesViewHolder(layout)
    }

    override fun onBindViewHolder(holder: AgesViewHolder, position: Int) {
        val item = allAgesList.get(position)
        holder.textView.text = item.toString()

        holder.textView.setOnClickListener {
            Log.d("AgesAdapter", "Made it to click!")
        }
    }

    override fun getItemCount(): Int {
        return allAgesList.size
    }
}