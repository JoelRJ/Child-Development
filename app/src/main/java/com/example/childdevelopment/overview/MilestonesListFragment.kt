package com.example.childdevelopment.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.childdevelopment.databinding.FragmentMilestonesListBinding
import com.example.childdevelopment.network.MilestonesOption

class MilestonesListFragment : Fragment() {
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
        val binding: FragmentMilestonesListBinding = FragmentMilestonesListBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.recyclerView.adapter = MilestonesAdapter()

        // Add divider between milestones
        val decoration  = DividerItemDecoration(binding.recyclerView.context, VERTICAL)
        binding.recyclerView.addItemDecoration(decoration)

        binding.executePendingBindings()
        Log.d("MilestoneListFragment", "Got here!")
        Log.d("MilestoneListFragment", viewModel.currentMilestones.value.toString())
        return binding.root
    }

    fun showActivities(milestone: MilestonesOption) {
        viewModel.chooseMilestone(milestone)
    }
}