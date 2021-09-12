package com.example.childdevelopment.overview

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.example.childdevelopment.database.*
import com.example.childdevelopment.network.*
import com.example.childdevelopment.overview.allAgesList
import kotlinx.coroutines.launch
import java.net.HttpURLConnection
import java.net.URL


/**
 * The [ViewModel] that is attached to the [AgesListFragment].
 */
class OverviewViewModel(val application: MilestoneApplication) : ViewModel() {
    private val milestoneDao: MilestoneDao = application.database.milestoneDao()
    private val activityDao: ActivityDao = application.database.activityDao()

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    // Ages options for the first list
    private val _ages = MutableLiveData<List<AgesOption>>()
    val ages: LiveData<List<AgesOption>> = _ages

    // Age chosen by user
    private var _currentAge = String()
    val currentAge: String = _currentAge

    // Milestones taken from server
    private val _milestonesFromServer = MutableLiveData<List<MilestonesOption>>()

    // Milestones which apply to currentAge
    lateinit var currentMilestones: LiveData<List<Milestone>>

    // Activities for current Milestone
    lateinit var currentActivities: LiveData<List<Activity>>

    // Database version
    lateinit var databaseVersion: String
    lateinit var serverVersion: String

    private lateinit var sharedPref: SharedPreferences

    init {
        getAges()
        checkDatabase()
        Log.d("OverviewViewModel:Ages", ages.value.toString())
    }

    private fun checkDatabase() {
        viewModelScope.launch {
            try {
                val response = MilestoneApi.retrofitService.getDatabaseVersion()
                serverVersion = response.body()?.database_version.toString()
                getRoomVersion()
                Log.d("ViewModel", serverVersion)

                if (serverVersion != databaseVersion) {
                    getMilestones()
                    Log.d("ViewModel", "Updated dataset because $serverVersion != $databaseVersion")
                }
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                Log.d("API", e.message.toString())
                Log.d("API", e.toString())
            }
        }

    }

    private fun getAges() {
        _ages.value = allAgesList
    }

    // Get local database version from sharedPreferences
    private fun getRoomVersion() {
        // Access the sharedPreferences value for database version, version 0 if no value exists
        sharedPref = application.getSharedPreferences(
            application.resources.getString(com.example.childdevelopment.R.string.file_key),
            Context.MODE_PRIVATE)
        val defaultValue = application.resources.getString(com.example.childdevelopment.R.string.default_database_version)
        databaseVersion = sharedPref.getString(application.getString(com.example.childdevelopment.R.string.database_version), defaultValue)!!

        // Put database version into sharedPreferences
        with (sharedPref.edit()) {
            putString(application.resources.getString(com.example.childdevelopment.R.string.database_version), serverVersion)
            apply()
        }
    }

    // API request from server for all milestones
    private fun getMilestones() {
        Log.d("APIBefore", "Here")
        viewModelScope.launch {
            try {
                val listResult = MilestoneApi.retrofitService.getMilestones()
                _milestonesFromServer.value = listResult
                Log.d("ViewModel:API_Success", listResult.toString())
                milestoneDao.deleteAll()
                activityDao.deleteAll()
                loadRoom()
            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
                Log.d("API", e.message.toString())
                Log.d("API", e.toString())
            }
        }
    }

    private fun loadRoom() {
        viewModelScope.launch {
            // Replace Room milestones with server milestones
            if (_milestonesFromServer.value != null) {
                for (element in _milestonesFromServer.value!!) {
                    Log.d("ViewModel:Room", element.toString())

                    var hasActivity = if(element.activities.isNotEmpty()) 1 else 0

                    milestoneDao.addMilestone(element.id, element.milestone, element.category, element.ageRange, hasActivity)

                    // Add activities to Activity entity
                    for (activity in element.activities) {
                        activityDao.addActivity(element.id, activity)
                    }
                }
            }
        }
    }

    fun chooseAge(ageText: String) {
        _currentAge = ageText


        // Because using LiveData already handled on background thread
        currentMilestones = milestoneDao.getMilestones(_currentAge).asLiveData()

        //_currentMilestones.value = newList
        Log.d("OverviewViewModel:after", currentMilestones.toString())
    }

    fun chooseMilestone(milestone: Milestone) {
        currentActivities = activityDao.getActivities(milestone.id).asLiveData()
        Log.d("ViewModel:Activity", currentActivities.toString())
    }

    fun checkActivity(activity: Activity) {
        viewModelScope.launch {
            activityDao.updateCheckbox(activity.id, activity.isChecked)
        }
    }
}

class OverviewViewModelFactory(private val application: MilestoneApplication) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OverviewViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}