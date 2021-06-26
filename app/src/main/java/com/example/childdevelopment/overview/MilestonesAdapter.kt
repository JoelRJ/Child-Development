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
import com.example.childdevelopment.databinding.MilestonesViewItemBinding
import com.example.childdevelopment.network.MilestonesOption

class MilestonesAdapter :
    ListAdapter<MilestonesOption, MilestonesAdapter.MilestonesViewHolder>(DiffCallback){
    class MilestonesViewHolder(private var binding: MilestonesViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(milestone: MilestonesOption) {
            Log.d("MilestoneAdapter", "Here1")
            binding.item = milestone
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [MilestonesOption] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<MilestonesOption>() {
        override fun areItemsTheSame(oldItem: MilestonesOption, newItem: MilestonesOption): Boolean {
            Log.d("MilestonesAdapter", "Here4")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MilestonesOption, newItem: MilestonesOption): Boolean {
            Log.d("MilestonesAdapter", "Here5")
            return oldItem.milestone == newItem.milestone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MilestonesViewHolder {
        Log.d("MilestoneAdapter", "Here2")
        return MilestonesViewHolder(MilestonesViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MilestonesViewHolder, position: Int) {
        Log.d("MilestoneAdapter", "Here3")
        val item = getItem(position)
        holder.bind(item)
    }
}