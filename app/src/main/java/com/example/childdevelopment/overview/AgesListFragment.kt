package com.example.childdevelopment.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.childdevelopment.R
import com.example.childdevelopment.database.MilestoneApplication
import com.example.childdevelopment.databinding.FragmentAgesListBinding

class AgesListFragment : Fragment() {
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
        val binding: FragmentAgesListBinding = FragmentAgesListBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel and this fragment
        binding.viewModel = viewModel

        binding.recyclerView.adapter = AgesAdapter(this@AgesListFragment)

        // Add divider between ages
        val decoration  = DividerItemDecoration(binding.recyclerView.context, LinearLayout.VERTICAL)
        binding.recyclerView.addItemDecoration(decoration)

        binding.executePendingBindings()

        return binding.root
    }

    fun goToNextScreen(selectedAge: String) {
        viewModel.chooseAge(selectedAge)

        val action = AgesListFragmentDirections.
            actionAgesListFragmentToMilestonesListFragment(age = selectedAge)
        findNavController().navigate(action)
    }
}