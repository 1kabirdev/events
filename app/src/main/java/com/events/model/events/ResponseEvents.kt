package com.events.model.events

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ResponseEvents {
    @SerializedName("response")
    @Expose
    private lateinit var response: Events

    fun getResponse(): Events {
        return response
    }

}