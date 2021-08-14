package com.example.childdevelopment.database

import android.app.Application

class MilestoneApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}