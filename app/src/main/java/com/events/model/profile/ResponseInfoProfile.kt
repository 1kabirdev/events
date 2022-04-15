package com.events.model.profile

import com.google.gson.annotations.SerializedName

class ResponseInfoProfile {
    @SerializedName("id")
    private var id: String = ""

    @SerializedName("username")
    private var username: String = ""

    @SerializedName("avatar")
    private var avatar: String = ""

    @SerializedName("phone")
    private var phone: String = ""

    @SerializedName("last_name")
    private var last_name: String = ""

    @SerializedName("about")
    private var about: String = ""

    @SerializedName("create_data")
    private var create_data: String = ""

    fun getId(): String {
        return id
    }

    fun getUsername(): String {
        return username
    }

    fun getAvatar(): String {
        return avatar
    }

    fun getPhone(): String {
        return phone
    }

    fun getLastName(): String {
        return last_name
    }

    fun getAbout(): String {
        return about
    }

    fun getCreateData(): String {
        return create_data
    }
}