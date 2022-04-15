package com.events.model.list_events

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseListEvents {
    @SerializedName("response")
    @Expose
    private var response: ArrayList<ListEvents> = arrayListOf()

    fun getResponse(): ArrayList<ListEvents> {
        return response
    }
}