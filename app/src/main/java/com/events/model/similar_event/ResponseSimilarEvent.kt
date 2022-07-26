package com.events.model.similar_event

import com.google.gson.annotations.SerializedName

data class ResponseSimilarEvent(
    @SerializedName("response") val similar: ArrayList<SimilarList>
)

data class SimilarList(
    @SerializedName("id_e") val id_e: Int,
    @SerializedName("name_e") val name_e: String,
    @SerializedName("image_e") val image_e: String,
    @SerializedName("data_e") val data_e: String,
    @SerializedName("theme_e") val theme_e: String,
    @SerializedName("city_e") val city: String,
    @SerializedName("user") val user: User
)

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("avatar") val avatar: String
)