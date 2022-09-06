package com.events.room.dao

import androidx.room.*
import com.events.room.SavedEvent

@Dao
interface DaoRoom {

    /**
     * event room db
     */

    @Query("SELECT * FROM savedEvent WHERE id_user = :id_user ORDER BY id DESC")
    fun getSavedEvent(id_user: Int): List<SavedEvent>

    @Insert
    fun insertEvent(saved: SavedEvent)

    @Delete
    fun deleteEvent(saved: SavedEvent)
}