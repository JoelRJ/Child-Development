package com.example.childdevelopment.overview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.childdevelopment.R
import com.example.childdevelopment.databinding.LinearViewItemBinding

class AgesAdapter :
    ListAdapter<String, AgesAdapter.AgesViewHolder>(DiffCallback){
    class AgesViewHolder(private var binding: LinearViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(ageText: String) {
                binding.item = ageText
                binding.executePendingBindings()
            }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [String] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgesViewHolder {
        return AgesViewHolder(LinearViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AgesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}