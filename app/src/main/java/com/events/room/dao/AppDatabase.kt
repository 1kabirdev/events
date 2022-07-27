package com.events.room.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.events.room.Profile
import com.events.room.Subscribe

@Database(entities = [Profile::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): DaoRoom
    //abstract fun subscribeDao(): DaoRoom
}