package com.events.room.dao

import androidx.room.*
import com.events.room.Profile
import com.events.room.Subscribe

@Dao
interface DaoRoom {

    /**
     * profile db
     */

    @Query("SELECT * FROM profile WHERE id = :id")
    fun getProfile(id: Long): Profile

    @Insert
    fun insertProfile(profile: Profile)

    /**
     * subs
     */
//    @Query("SELECT * FROM subscribe WHERE user_id = :user_id")
//    fun getSubscribe(user_id: Int): List<Subscribe>
//
//    @Insert
//    fun insertSubscribe(subscribe: Subscribe)
}