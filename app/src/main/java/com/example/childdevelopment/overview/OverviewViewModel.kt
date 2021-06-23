package com.example.childdevelopment.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.childdevelopment.network.AgesOption
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

    private val _milestones = MutableLiveData<HashMap<String, List<String>>>()

    private val _currentMilestones = MutableLiveData<List<String>>()
    val currentMilestones: LiveData<List<String>> = _currentMilestones

    private var _currentAge = String()
    val currentAge: String = _currentAge

    init {
        getAges()
        Log.d("OverviewViewModel:Ages", ages.value.toString())
        getMilestones()
        Log.d("OverviewViewModel:Miles", _milestones.value.toString())
    }

    private fun getAges() {
        val allAgesList: List<String> =
            listOf("0 to 1 month",
                "1 to 2 months",
                "2 to 4 months",
                "4 to 6 months",
                "6 to 9 months",
                "9 to 12 months",
                "12 to 18 months",
                "18 to 24 months",
                "2 to 3 years",
                "3 to 4 years",
                "4 to 5 years")

        var marsPhotosList: MutableList<AgesOption> = mutableListOf()

        allAgesList.forEachIndexed { index, s ->
            marsPhotosList.add(AgesOption(index.toString(), s))
        }

        _ages.value = marsPhotosList
    }

    private fun getMilestones() {
        _milestones.value = allMilestonesList
    }

    fun chooseAge(ageText: String) {
        _currentAge = ageText
        _currentMilestones.value = _milestones.value!![ageText]
        Log.d("OverviewViewModel", _currentAge)
    }
}
