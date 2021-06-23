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

class MilestonesAdapter :
    ListAdapter<String, MilestonesAdapter.MilestonesViewHolder>(DiffCallback){
    class MilestonesViewHolder(private var binding: LinearViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(milestoneText: String) {
            //binding.item = milestoneText
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MilestonesViewHolder {
        return MilestonesViewHolder(LinearViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MilestonesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}