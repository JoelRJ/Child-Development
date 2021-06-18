/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.childdevelopment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.childdevelopment.overview.allMilestonesList

/**
 * Adapter for the [RecyclerView] in [DetailActivity].
 */
class MilestonesAdapter(private val age: String, context: Context) :
    RecyclerView.Adapter<MilestonesAdapter.MilestoneViewHolder>() {

    private val filteredMilestones: List<String>

    init {
        // Retrieve the list of words from res/values/arrays.xml
        val milestones = allMilestonesList

        filteredMilestones = milestones[age]!!
    }

    class MilestoneViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val button = view.findViewById<TextView>(R.id.text_item)
    }

    override fun getItemCount(): Int = filteredMilestones.size

    /**
     * Creates new views with R.layout.item_view as its template
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MilestoneViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.linear_view_item, parent, false)

        return MilestoneViewHolder(layout)
    }

    /**
     * Replaces the content of an existing view with new data
     */
    override fun onBindViewHolder(holder: MilestoneViewHolder, position: Int) {

        val item = filteredMilestones[position]
        // Needed to call startActivity
        val context = holder.view.context

        // Set the text of the WordViewHolder
        holder.button.text = item

        holder.button.setOnClickListener {
            Log.d("MilestonesAdapter", "Button clicked!")
        }


    }
}