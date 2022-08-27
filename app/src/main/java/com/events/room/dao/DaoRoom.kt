package com.events.room.dao

import androidx.room.*
import com.events.room.Saved

@Dao
interface DaoRoom {

    /**
     * save event db
     */

    @Query("SELECT * FROM saved WHERE id = :id ORDER BY id DESC")
    fun getProfile(id: Long): Saved

    @Insert
    fun insertProfile(saved: Saved)
}