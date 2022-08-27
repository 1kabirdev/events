package com.events.room.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.events.room.Saved

@Database(entities = [Saved::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSavedEventDao(): DaoRoom
}