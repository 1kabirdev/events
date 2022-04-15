package com.events

import android.app.Application
import com.events.data.DataManager
import com.events.utill.SharedPreferences

class App : Application() {
    lateinit var dataManager: DataManager
    override fun onCreate() {
        super.onCreate()
        SharedPreferences.setEventType(SharedPreferences.getEventType(this), this)
        dataManager = DataManager()
    }
}