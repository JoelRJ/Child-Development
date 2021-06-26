package com.example.childdevelopment.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.childdevelopment.databinding.ActivitiesViewItemBinding
import com.example.childdevelopment.network.MilestonesOption


class ActivitiesAdapter :
    ListAdapter<String, ActivitiesAdapter.ActivitiesViewHolder>(DiffCallback){
    class ActivitiesViewHolder(private var binding: ActivitiesViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(activity: String) {
            binding.item = activity
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [MilestonesOption] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        return ActivitiesViewHolder(ActivitiesViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}