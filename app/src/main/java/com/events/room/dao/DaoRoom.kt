package com.events.room.dao

import androidx.room.*
import com.events.room.SavedEvent

@Dao
interface DaoRoom {

    /**
     * event room db
     */

    @Query("SELECT * FROM savedEvent WHERE id = :id ORDER BY id DESC")
    fun getSavedEvent(id: Long): List<SavedEvent>

    @Insert
    fun insertEvent(saved: SavedEvent)
}