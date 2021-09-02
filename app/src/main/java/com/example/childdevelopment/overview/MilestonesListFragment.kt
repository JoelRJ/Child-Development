package com.example.childdevelopment.overview

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.childdevelopment.R
import com.example.childdevelopment.database.Milestone
import com.example.childdevelopment.database.MilestoneApplication
import com.example.childdevelopment.databinding.FragmentMilestonesListBinding
import com.example.childdevelopment.network.MilestonesOption

class MilestonesListFragment : Fragment() {
    private val viewModel: OverviewViewModel by activityViewModels() {
        OverviewViewModelFactory(
            (activity?.application as MilestoneApplication)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentMilestonesListBinding = FragmentMilestonesListBinding.inflate(inflater)

        val adapter = MilestonesAdapter(this@MilestonesListFragment)
        binding.recyclerView.adapter = adapter
        viewModel.currentMilestones.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)

        binding.executePendingBindings()
        Log.d("MilestoneListFragment", "Got here!")
        return binding.root
    }

    fun showActivities(milestone: Milestone) {
        Log.d("MilestonesListFragment", "Here0")
        viewModel.chooseMilestone(milestone)
        Log.d("MilestonesListFragment", "Here1")
        findNavController().navigate(R.id.action_milestonesListFragment_to_activitiesListFragment)
    }
}