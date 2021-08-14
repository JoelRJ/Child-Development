package com.example.childdevelopment.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.childdevelopment.databinding.FragmentActivitiesListBinding


class ActivitiesListFragment : Fragment() {
    private val viewModel: OverviewViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentActivitiesListBinding = FragmentActivitiesListBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.recyclerView.adapter = ActivitiesAdapter()

        // Add divider between milestones
        val decoration  = DividerItemDecoration(binding.recyclerView.context, LinearLayout.VERTICAL)
        binding.recyclerView.addItemDecoration(decoration)

        binding.executePendingBindings()
        Log.d("ActivitieslistFragment", "Got here!")
        return binding.root
    }
}