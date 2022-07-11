package com.events.model.profile

import com.google.gson.annotations.SerializedName

data class ResponseInfoProfile(
    @SerializedName("id") var id: String = "",
    @SerializedName("username") var username: String = "",
    @SerializedName("avatar") var avatar: String = "",
    @SerializedName("phone") var phone: String = "",
    @SerializedName("last_name") var last_name: String = "",
    @SerializedName("about") var about: String = "",
    @SerializedName("create_data") var create_data: String = "",
)