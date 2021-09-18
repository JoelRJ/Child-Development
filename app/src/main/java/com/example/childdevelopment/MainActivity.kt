package com.example.childdevelopment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.childdevelopment.databinding.ActivityMainBinding
import android.content.SharedPreferences
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.childdevelopment.database.MilestoneApplication
import com.example.childdevelopment.overview.AgesListFragment
import com.example.childdevelopment.overview.ChildrenProfilesFragment
import com.example.childdevelopment.overview.OverviewViewModel
import com.example.childdevelopment.overview.OverviewViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        // Set default selectedItemId to Browse All
        binding.bottomNavigation.selectedItemId = R.id.nav_graph_home

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_graph_profiles -> {
                    Log.d("MainActivity:BottomNav", "view profile")
                    loadFragment(ChildrenProfilesFragment())
                    true
                }
                R.id.nav_graph_home -> {
                    Log.d("MainActivity:BottomNav", "browse all")
                    val browseFragment = AgesListFragment()
                    loadFragment(browseFragment)
                    true
                }
                R.id.search -> {
                    Log.d("MainActivity:BottomNav", "Search")
                    true
                }
                else -> false
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun loadFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, fragment)
            transaction.commit()
        }
    }
}