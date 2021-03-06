package com.example.childdevelopment.overview

import java.util.*

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

val allMilestonesList =
        hashMapOf<String, List<String>>(allAgesList[0] to listOf<String>("Milestone 1", "Milestone 2"),
                allAgesList[1] to listOf<String>("Milestone 2"))