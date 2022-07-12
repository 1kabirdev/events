package com.events

import android.app.Application
import androidx.room.Room
import com.events.data.DataManager
import com.events.room.dao.AppDatabase
import com.events.utill.SharedPreferences

class App : Application() {
    internal lateinit var dataManager: DataManager
    private lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        SharedPreferences.setEventType(SharedPreferences.getEventType(this), this)
        dataManager = DataManager()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "events_database"
        ).build()
    }

    fun getDatabase(): AppDatabase = database
}