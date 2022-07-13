package com.events.model.comments

import com.google.gson.annotations.SerializedName

data class Comments(
    @SerializedName("response") val response: ArrayList<CommentsList> = arrayListOf()
)

data class CommentsList(
    @SerializedName("id") val id: Int,
    @SerializedName("event_id") val event_id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("text_comments") val text_comments: String
)