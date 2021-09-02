package com.example.childdevelopment.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// https://medium.com/@tonia.tkachuk/android-app-example-using-room-database-63f7091e69af
@Database(
    version = 3,
    entities = [Milestone::class, Activity::class],
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun milestoneDao(): MilestoneDao
    abstract fun activityDao(): ActivityDao



    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE Activity ADD COLUMN isChecked INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database")
                        // TODO: https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
                    // https://developer.android.com/training/data-storage/room/migrating-db-versions
                    //.fallbackToDestructiveMigration()
                    // https://stackoverflow.com/questions/49629656/please-provide-a-migration-in-the-builder-or-call-fallbacktodestructivemigration
                    .addMigrations((MIGRATION_2_3))
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}