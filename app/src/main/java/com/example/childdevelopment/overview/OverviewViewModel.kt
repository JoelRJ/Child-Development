package com.example.childdevelopment.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.childdevelopment.network.AgesOption
import com.example.childdevelopment.network.MilestonesOption
import com.example.childdevelopment.overview.allAgesList
import kotlinx.coroutines.launch

/**
 * The [ViewModel] that is attached to the [AgesListFragment].
 */
class OverviewViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    private val _ages = MutableLiveData<List<AgesOption>>()
    val ages: LiveData<List<AgesOption>> = _ages

    private var _currentAge = String()
    val currentAge: String = _currentAge

    private val _milestones = MutableLiveData<List<MilestonesOption>>()

    private val _currentMilestones = MutableLiveData<MutableList<MilestonesOption>>()
    val currentMilestones: LiveData<MutableList<MilestonesOption>> = _currentMilestones

    private val _milestoneActivities = MutableLiveData<List<String>>()
    val milestoneActivities: LiveData<List<String>> = _milestoneActivities

    init {
        getAges()
        Log.d("OverviewViewModel:Ages", ages.value.toString())
        getMilestones()
        Log.d("OverviewViewModel:Miles", _milestones.value.toString())
    }

    private fun getAges() {
        _ages.value = allAgesList
    }

    private fun getMilestones() {
        _milestones.value = allMilestonesList
    }

    fun chooseAge(ageText: String) {
        _currentAge = ageText

        val newList = mutableListOf<MilestonesOption>()

        Log.d("OverviewViewModel", allMilestonesList.toString())
        for (element in allMilestonesList) {
            Log.d("OverviewViewModel:b4", element.toString())
            if (element.ageRange == _currentAge) {

                Log.d("OverviewViewModel:Insid", element.toString())
                newList.add(element)
            }
        }
        _currentMilestones.value = newList
        Log.d("OverviewViewModel:after", _currentMilestones.value.toString())
    }

    fun chooseMilestone(milestone: MilestonesOption) {
        _milestoneActivities.value = milestone.activities
    }
}
