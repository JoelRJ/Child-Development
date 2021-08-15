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
import com.example.childdevelopment.database.Milestone
import com.example.childdevelopment.databinding.MilestonesViewItemBinding
import com.example.childdevelopment.network.MilestonesOption

class MilestonesAdapter(val fragment: MilestonesListFragment) :
    ListAdapter<Milestone, MilestonesAdapter.MilestonesViewHolder>(DiffCallback){

    class MilestonesViewHolder(private var binding: MilestonesViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(milestone: Milestone, fragment: MilestonesListFragment) {
            binding.apply {
                textItem.text = milestone.milestone
                milestoneCategory.text = milestone.category
                seeActivitiesButton.setOnClickListener {
                    fragment.showActivities(milestone)
                }
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [Milestone] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Milestone>() {
        override fun areItemsTheSame(oldItem: Milestone, newItem: Milestone): Boolean {
            Log.d("MilestonesAdapter", "Here4")
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Milestone, newItem: Milestone): Boolean {
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
        holder.bind(item, fragment)
    }
}