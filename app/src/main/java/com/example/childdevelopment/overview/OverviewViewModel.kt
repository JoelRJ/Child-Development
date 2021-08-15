package com.example.childdevelopment.overview

import android.util.Log
import androidx.lifecycle.*
import com.example.childdevelopment.database.Milestone
import com.example.childdevelopment.database.MilestoneDao
import com.example.childdevelopment.network.AgesOption
import com.example.childdevelopment.network.ApiService
import com.example.childdevelopment.network.MilestoneApi
import com.example.childdevelopment.network.MilestonesOption
import com.example.childdevelopment.overview.allAgesList
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [AgesListFragment].
 */
class OverviewViewModel(private val milestoneDao: MilestoneDao) : ViewModel() {

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

    // Activities for the chosen milestone
    private val _milestoneActivities = MutableLiveData<List<String>>()
    val milestoneActivities: LiveData<List<String>> = _milestoneActivities

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
                }
            }
        }

    }

    private fun getMilestoneActivities() {
        _milestoneActivities.value = listOf("Activity1", "Activity2")
    }

    fun chooseAge(ageText: String) {
        _currentAge = ageText

        currentMilestones = milestoneDao.getMilestones(_currentAge).asLiveData()
        // Get all milestones of certain age from server
        val newList = mutableListOf<MilestonesOption>()

        Log.d("OverviewViewModel", _milestonesFromServer.value.toString())
        Log.d("OverviewViewModel:Age", _currentAge)
        for (element in _milestonesFromServer.value!!) {
            Log.d("OverviewViewModel:b4", element.toString())
            if (element.ageRange == _currentAge) {

                Log.d("OverviewViewModel:Insid", element.toString())
                newList.add(element)
            }

        }
        //_currentMilestones.value = newList
        Log.d("OverviewViewModel:after", currentMilestones.value.toString())
    }

    fun chooseMilestone(milestone: Milestone) {
        //_milestoneActivities.value = milestone.activities
        Log.d("OverviewViewModel", milestoneActivities.value.toString())
    }
}

class OverviewViewModelFactory(private val milestoneDao: MilestoneDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OverviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OverviewViewModel(milestoneDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}