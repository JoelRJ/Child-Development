package com.example.childdevelopment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activity WHERE milestoneId = :id")
    fun getActivities(id: String): Flow<List<Activity>>

    @Query("INSERT INTO activity (milestoneId, activity) VALUES ( :milestoneId, :activity)")
    suspend fun addActivity(milestoneId: String, activity: String)

    @Query("UPDATE activity SET isChecked = :isChecked WHERE id = :id")
    suspend fun updateCheckbox(id: Int, isChecked: Int)

    @Query("DELETE FROM activity")
    suspend fun deleteAll()
}