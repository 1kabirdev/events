package com.events.model.my_events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UsersEvents {
    @SerializedName("user_id")
    @Expose
    private var userId: String = ""

    @SerializedName("avatar")
    @Expose
    private var avatar: String = ""

    fun getUserId(): String {
        return userId
    }

    fun getAvatar(): String {
        return avatar
    }

}