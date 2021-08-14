package com.example.childdevelopment.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MilestoneDao {
    @Query("SELECT * FROM milestone WHERE ageRange = :age")
    fun getMilestones(age: String): Flow<List<Milestone>>

    @Query("INSERT INTO milestone VALUES (:id, :milestone, :category, :age)")
    fun addMilestone(id: String, milestone: String, category: String, age: String)

    @Insert
    suspend fun insertMilestone(milestone: Milestone)

    @Query("DELETE FROM milestone")
    fun deleteAll()
}
