package com.events.model.create_event

import com.google.gson.annotations.SerializedName

data class ResponseCreateEvents(
    @SerializedName("status") var status: String,
    @SerializedName("message") var message: String,
    @SerializedName("error") var error: String
)