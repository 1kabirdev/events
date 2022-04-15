package com.events.model.list_events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UsersEvent {
    @SerializedName("user_id")
    @Expose
    private var userId: String = ""

    @SerializedName("username")
    private var username: String = ""

    @SerializedName("last_name")
    private var lastName: String = ""

    @SerializedName("avatar")
    @Expose
    private var avatar: String = ""

    fun getUserId(): String {
        return userId
    }

    fun getAvatar(): String {
        return avatar
    }

    fun getUserName(): String {
        return username
    }

    fun getLastName(): String {
        return lastName
    }

}