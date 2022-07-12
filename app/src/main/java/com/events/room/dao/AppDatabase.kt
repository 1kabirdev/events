package com.events.room.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.events.room.Profile

@Database(entities = [Profile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): DaoRoom
}