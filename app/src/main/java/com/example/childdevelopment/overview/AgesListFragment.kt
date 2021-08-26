package com.example.childdevelopment.overview

import android.content.Context
import android.content.SharedPreferences
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
    private lateinit var sharedPref: SharedPreferences
    private val viewModel: OverviewViewModel by activityViewModels() {
        OverviewViewModelFactory(
            (activity?.application as MilestoneApplication)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        // Access the sharedPreferences value for database version, version 0 if no value exists
        sharedPref = this.getActivity()?.getSharedPreferences(
            getString(com.example.childdevelopment.R.string.file_key),
            Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getString(com.example.childdevelopment.R.string.default_database_version)
        val databaseVersion = sharedPref.getString(getString(com.example.childdevelopment.R.string.database_version), defaultValue)

        viewModel.databaseVersion = databaseVersion!!

        // Put database version into sharedPreferences IF database version has been changed
        sharedPref  = this.getActivity()?.getSharedPreferences(getString(com.example.childdevelopment.R.string.file_key), Context.MODE_PRIVATE)
            ?: return
        with (sharedPref.edit()) {
            putString(getString(R.string.database_version), "version 1")
            apply()
        }

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
        Log.d("fragment_activities", selectedAge)
        viewModel.chooseAge(selectedAge)

        val action = AgesListFragmentDirections.
            actionAgesListFragmentToMilestonesListFragment(age = selectedAge)
        findNavController().navigate(action)
    }
}