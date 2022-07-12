package com.events.room.dao

import androidx.room.*
import com.events.room.Profile

@Dao
interface DaoRoom {

    /**
     * profile db
     */

    @Query("SELECT * FROM profile WHERE id = :id")
    fun getProfile(id: Long): Profile

    @Insert
    fun insertProfile(profile: Profile)
}