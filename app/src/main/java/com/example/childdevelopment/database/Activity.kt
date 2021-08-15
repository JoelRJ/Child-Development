package com.example.childdevelopment.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Activity (
    @PrimaryKey val id: String,
    @NonNull @ColumnInfo val milestone: String,
    @NonNull @ColumnInfo val activity: String
)