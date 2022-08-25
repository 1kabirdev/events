package com.events.model.theme_event

import com.events.model.home.ResponseHomeEvents
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class ResponseThemeEventList(
    @SerializedName("info") val infoEvents: InfoEvents,
    @SerializedName("response") var response: ArrayList<ListEvents>
)

data class InfoEvents(
    @SerializedName("page") val page: Int,
    @SerializedName("next_page") val next_page: Int,
    @SerializedName("count_page") val count_page: Int,
    @SerializedName("count_event") val count_event: Int,
    @SerializedName("subscribe") var subscribe: Boolean
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

data class Subscribe(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: String
)

data class ResponseThemeEventHome(
    @SerializedName("theme_event") val theme_event: List<ThemeEventHome>
)

data class ThemeEventHome(
    @SerializedName("id_t") val id_t: Int,
    @SerializedName("name_t") val name: String,
    @SerializedName("image") val image: String
)


data class DetailsViewModel(
    val responseEvents: ResponseHomeEvents,
    val theme_event: ResponseThemeEventHome
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}