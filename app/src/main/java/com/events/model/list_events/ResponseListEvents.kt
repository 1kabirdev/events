package com.events.model.list_events

import com.google.gson.annotations.SerializedName

data class ResponseListEvents(
    @SerializedName("organize") val organize: Organize,
    @SerializedName("info") val infoPage: InfoPage,
    @SerializedName("response") var response: ArrayList<ListEvents>
)

data class InfoPage(
    @SerializedName("page") val page: Int,
    @SerializedName("next_page") val next_page: Int,
    @SerializedName("count_page") val count_page: Int,
    @SerializedName("count_event") val count_event: Int
)

data class ListEvents(
    @SerializedName("id_e") var idE: String,
    @SerializedName("name_e") var nameE: String,
    @SerializedName("desc_e") var descE: String,
    @SerializedName("image_e") var imageE: String,
    @SerializedName("data_e") var dataE: String,
    @SerializedName("time_e") var timeE: String,
    @SerializedName("theme_e") var themeE: String,
    @SerializedName("city_e") var cityE: String,
    @SerializedName("cost_e") var costE: String,
    @SerializedName("create_date_e") var createDateE: String,
    @SerializedName("user") var user: UsersEvent? = null
)

data class UsersEvent(
    @SerializedName("user_id") var userId: String,
    @SerializedName("avatar") var avatar: String
)

data class Organize(
    @SerializedName("user_id") var userId: String,
    @SerializedName("username") var username: String,
    @SerializedName("last_name") var lastName: String,
    @SerializedName("avatar") var avatar: String,
    @SerializedName("about") var about: String,

)