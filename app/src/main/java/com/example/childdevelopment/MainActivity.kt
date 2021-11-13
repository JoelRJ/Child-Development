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
import androidx.navigation.ui.setupWithNavController
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

        //setupActionBarWithNavController(navController)
        binding.bottomNavigation.setupWithNavController(navController)

        // Set default selectedItemId to Browse All
        binding.bottomNavigation.selectedItemId = R.id.home


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.profiles -> {
                    Log.d("MainActivity:BottomNav", "view profile")
                    navController.navigate(R.id.childrenProfilesFragment)
                    true
                }
                R.id.home -> {
                    Log.d("MainActivity:BottomNav", "browse all")
                    navController.navigate(R.id.agesListFragment)
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
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }
}