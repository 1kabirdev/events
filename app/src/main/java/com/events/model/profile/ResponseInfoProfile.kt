package com.events.model.profile

import com.google.gson.annotations.SerializedName

data class ResponseInfoProfile(
    @SerializedName("profile") val profile: ProfileData,
    @SerializedName("info") val infoPage: InfoPage,
    @SerializedName("response") val responseEvents: ArrayList<ResponseEvents>
)

data class ProfileData(
    @SerializedName("id") var id: String,
    @SerializedName("username") var username: String,
    @SerializedName("avatar") var avatar: String,
    @SerializedName("phone") var phone: String,
    @SerializedName("last_name") var last_name: String,
    @SerializedName("about") var about: String,
    @SerializedName("create_data") var create_data: String,
)

data class InfoPage(
    @SerializedName("page") val page: Int,
    @SerializedName("next_page") val next_page: Int,
    @SerializedName("count_page") val count_page: Int,
    @SerializedName("count_event") val count_event: Int
)

data class ResponseEvents(
    @SerializedName("id_e") var idE: String,
    @SerializedName("name_e") var nameE: String,
    @SerializedName("desc_e") var descE: String,
    @SerializedName("image_e") var imageE: String,
    @SerializedName("data_e") var dataE: String,
    @SerializedName("time_e") var timeE: String,
    @SerializedName("theme_e") var themeE: String,
    @SerializedName("city_e") var cityE: String,
    @SerializedName("cost_e") var costE: String,
    @SerializedName("create_date_e") var createDateE: String
)

data class UpdateAvatar(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String
)
