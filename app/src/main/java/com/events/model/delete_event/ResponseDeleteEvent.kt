package com.events.model.delete_event

import com.google.gson.annotations.SerializedName

class ResponseDeleteEvent {
    @SerializedName("status")
    private var status: Boolean = false

    @SerializedName("message")
    private var message: String = ""

    fun getStatus(): Boolean {
        return status
    }

    fun getMessage(): String {
        return message
    }
}