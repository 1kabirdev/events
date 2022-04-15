package com.events.model.create_event

import com.google.gson.annotations.SerializedName

class ResponseCreateEvents {
    @SerializedName("status")
    private var status: String = ""

    @SerializedName("message")
    private var message: String = ""

    @SerializedName("error")
    private var error: String = ""

    fun getStatus(): String {
        return status
    }

    fun getMessage(): String {
        return message
    }

    fun getError(): String {
        return error
    }
}