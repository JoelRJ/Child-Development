package com.example.childdevelopment.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Milestone(
    @PrimaryKey val id: String,
    @NonNull @ColumnInfo val milestone: String,
    @NonNull @ColumnInfo val category: String,
    @NonNull @ColumnInfo val ageRange: String
)