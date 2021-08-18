package com.example.childdevelopment.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MilestoneDao {
    @Query("SELECT * FROM milestone")
    fun getAll(): Flow<List<Milestone>>

    @Query("SELECT * FROM milestone WHERE ageRange = :ageIn")
    fun getMilestones(ageIn: String): Flow<List<Milestone>>

    @Query("INSERT INTO milestone VALUES (:id, :milestone, :category, :age)")
    suspend fun addMilestone(id: String, milestone: String, category: String, age: String)

    @Insert
    suspend fun insertMilestone(milestone: Milestone)

    @Query("DELETE FROM milestone")
    suspend fun deleteAll()
}
