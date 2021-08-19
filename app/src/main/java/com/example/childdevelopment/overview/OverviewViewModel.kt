package com.example.childdevelopment.overview

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.example.childdevelopment.database.*
import com.example.childdevelopment.network.AgesOption
import com.example.childdevelopment.network.ApiService
import com.example.childdevelopment.network.MilestoneApi
import com.example.childdevelopment.network.MilestonesOption
import com.example.childdevelopment.overview.allAgesList
import kotlinx.coroutines.launch


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
    lateinit var currentMilestones: LiveData<List<Milestone>> //= milestoneDao.getMilestones("0 to 1 Month").asLiveData()

    // Activities for current Milestone
    lateinit var currentActivities: LiveData<List<Activity>>



    init {



        getAges()
        Log.d("OverviewViewModel:Ages", ages.value.toString())
        getMilestones()
        Log.d("OverviewViewModel:Miles", _milestonesFromServer.value.toString())
    }

    private fun getAges() {
        _ages.value = allAgesList
    }

    // API request from server for all milestones
    private fun getMilestones() {
        //_milestones.value = allMilestonesList
        Log.d("APIBefore", "Here")
        viewModelScope.launch {
            try {
                val listResult = MilestoneApi.retrofitService.getMilestones()
                _milestonesFromServer.value = listResult
                Log.d("ViewModel: API_Success", listResult.toString())
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
                    Log.d("ViewModel: Room", element.toString())
                    milestoneDao.addMilestone(element.id, element.milestone, element.category, element.ageRange)

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