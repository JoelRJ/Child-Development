package com.example.childdevelopment.network

data class MilestonesOption(
    val id: Int,
    val milestone: String,
    val category: String,
    val ageRange: String,
    val activities: List<String>
)