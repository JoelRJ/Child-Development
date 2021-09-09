package com.example.childdevelopment.overview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.childdevelopment.database.Activity
import com.example.childdevelopment.databinding.ActivitiesViewItemBinding
import com.example.childdevelopment.network.MilestonesOption


class ActivitiesAdapter(val viewModel: OverviewViewModel) :
    ListAdapter<Activity, ActivitiesAdapter.ActivitiesViewHolder>(DiffCallback){

    class ActivitiesViewHolder(private var binding: ActivitiesViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(activity: Activity, viewModel: OverviewViewModel) {
            binding.checkBox.isChecked = activity.isChecked == 1

            Log.d("Checkbox", activity.isChecked.toString())

            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                Log.d("Checkbox", "Clicked! ${isChecked.toString()}")
                if (isChecked) {
                    activity.isChecked = 1
                    Log.d("isChecked", activity.activity)
                }
                else {
                    activity.isChecked = 0
                }

                viewModel.checkActivity(activity)
            }

            binding.apply {
                textItem.text = activity.activity
                textItem.setOnClickListener {
                    checkBox.performClick()
                }
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [MilestonesOption] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Activity>() {
        override fun areItemsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Activity, newItem: Activity): Boolean {
            return oldItem.activity == newItem.activity
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesViewHolder {
        return ActivitiesViewHolder(ActivitiesViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ActivitiesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, viewModel)
    }
}