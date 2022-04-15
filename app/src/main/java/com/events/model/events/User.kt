package com.events.model.events

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class User {
    @SerializedName("user_id")
    @Expose
    private var userId: String = ""

    @SerializedName("avatar")
    @Expose
    private var avatar: String = ""

    @SerializedName("username")
    @Expose
    private var username: String = ""

    @SerializedName("last_name")
    @Expose
    private var lastName: String = ""

    fun getUserId(): String {
        return userId
    }

    fun getAvatar(): String {
        return avatar
    }

    fun getUsername(): String {
        return username
    }

    fun getLastName(): String {
        return lastName
    }

}