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
import com.example.childdevelopment.databinding.AgesViewItemBinding
import com.example.childdevelopment.network.AgesOption

class AgesAdapter(val fragment: AgesListFragment) :
    ListAdapter<AgesOption, AgesAdapter.AgesViewHolder>(DiffCallback){
    class AgesViewHolder(private var binding: AgesViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(age: AgesOption, fragment: AgesListFragment) {
            binding.item = age
            binding.ageFragment = fragment
            binding.executePendingBindings()
            binding.imageView.setImageResource(age.imageId)
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [AgesOption] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<AgesOption>() {
        override fun areItemsTheSame(oldItem: AgesOption, newItem: AgesOption): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AgesOption, newItem: AgesOption): Boolean {
            return oldItem.ageRange == newItem.ageRange
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgesViewHolder {
        return AgesViewHolder(AgesViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AgesViewHolder, position: Int) {
        Log.d("AgesAdapter", "Here1")
        val item = getItem(position)
        holder.bind(item, fragment)
    }
}