package com.events.utill

import android.content.Context

class SharedPreferences {
    companion object {

        fun saveToken(token: String?, context: Context) {
            val preferences = context.getSharedPreferences("TOKEN_USER", Context.MODE_PRIVATE)
            preferences.edit().putString("token", token).apply()
        }

        fun loadToken(context: Context): String? {
            val preferences = context.getSharedPreferences("TOKEN_USER", Context.MODE_PRIVATE)
            return preferences.getString("token", "")
        }

        fun saveIdUser(user_id: String?, context: Context) {
            val preferences = context.getSharedPreferences("USER_ID", Context.MODE_PRIVATE)
            preferences.edit().putString("user_id", user_id).apply()
        }

        fun loadIdUser(context: Context): String? {
            val preferences = context.getSharedPreferences("USER_ID", Context.MODE_PRIVATE)
            return preferences.getString("user_id", "")
        }

        fun getEventType(context: Context): String? {
            val preferences = context.getSharedPreferences("TYPE", Context.MODE_PRIVATE)
            return preferences.getString("type", "")
        }

        fun setEventType(type: String?, context: Context) {
            val preferences = context.getSharedPreferences("TYPE", Context.MODE_PRIVATE)
            preferences.edit().putString("type", type).apply()
        }
    }
}