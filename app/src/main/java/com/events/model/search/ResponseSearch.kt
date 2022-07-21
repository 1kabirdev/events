package com.events.model.search

import com.google.gson.annotations.SerializedName

data class ResponseSearch(
    @SerializedName("response") val response: ArrayList<Event>
)

data class Event(
    @SerializedName("id") var id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: String,
    @SerializedName("theme") val theme: String
)