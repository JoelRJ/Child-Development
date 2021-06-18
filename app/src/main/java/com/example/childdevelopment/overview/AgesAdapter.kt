package com.example.childdevelopment.overview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.childdevelopment.R

class AgesAdapter : RecyclerView.Adapter<AgesAdapter.AgesViewHolder>(){
    class AgesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button = view.findViewById<TextView>(R.id.button_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgesViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.linear_view_item, parent, false)

        return AgesViewHolder(layout)
    }

    override fun onBindViewHolder(holder: AgesViewHolder, position: Int) {
        val item = allAgesList.get(position)
        holder.button.text = item

        holder.button.setOnClickListener {
            val action = AgesListFragmentDirections.actionAgesListFragmentToMilestonesListFragment(age = holder.button.text.toString())
            holder.view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return allAgesList.size
    }
}