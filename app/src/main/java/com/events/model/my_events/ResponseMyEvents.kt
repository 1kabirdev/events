package com.events.model.my_events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseMyEvents {
    @SerializedName("response")
    @Expose
    private var response: ArrayList<MyEventsList> = arrayListOf()

    fun getResponse(): ArrayList<MyEventsList> {
        return response
    }
}