package com.events.room.dao

import androidx.room.*
import com.events.room.Profile

@Dao
interface DaoRoom {

    /**
     * profile db
     */

    @Query("SELECT * FROM profile WHERE id = :id")
    suspend fun getProfile(id: Long): Profile

    @Insert
    suspend fun insertProfile(profile: Profile)
}