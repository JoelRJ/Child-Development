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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.childdevelopment.database.MilestoneApplication
import com.example.childdevelopment.databinding.FragmentActivitiesListBinding


class ActivitiesListFragment : Fragment() {
    private val viewModel: OverviewViewModel by activityViewModels() {
        OverviewViewModelFactory(
            (activity?.application as MilestoneApplication)
        )
    }

    private var _binding: FragmentActivitiesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActivitiesListBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ActivitiesAdapter(viewModel)
        binding.recyclerView.adapter = adapter
        viewModel.currentActivities.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        // Add divider between milestones
        val decoration  = DividerItemDecoration(binding.recyclerView.context, LinearLayout.VERTICAL)
        binding.recyclerView.addItemDecoration(decoration)

        binding.executePendingBindings()
        Log.d("ActivitieslistFragment", "Got here!")
    }
}