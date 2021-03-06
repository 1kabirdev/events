package com.events.model.comments

import com.google.gson.annotations.SerializedName

data class CommentsList(
    @SerializedName("id") val id: Int,
    @SerializedName("event_id") val event_id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("username") val username: String,
    @SerializedName("comment_text") val text_comments: String,
    @SerializedName("data_create") val data_create: String
)

data class ResponseComments(
    @SerializedName("info") val info: Info,
    @SerializedName("response") val response: ArrayList<CommentsList>
)

data class Info(
    @SerializedName("page") val page: Int,
    @SerializedName("next_page") val next_page: Int,
    @SerializedName("count_page") val count_page: Int,
    @SerializedName("count_comments") val count_comments: Int
)

data class AddComment(
    @SerializedName("status") val status: Boolean,
    @SerializedName("username") val username: String,
    @SerializedName("comment_text") val comment_text: String,
    @SerializedName("message") val message: String
)