package com.example.childdevelopment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.childdevelopment.databinding.ActivityMainBinding
import android.content.SharedPreferences
import androidx.fragment.app.activityViewModels
import com.example.childdevelopment.database.MilestoneApplication
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


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}