package com.events.model.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseLogin {
    @SerializedName("id")
    private var idUser: String = ""

    @SerializedName("token")
    private var token: String = ""

    @SerializedName("status")
    private var status: Boolean = false

    @SerializedName("message")
    private var message: String = ""

    fun getStatus(): Boolean {
        return status
    }

    fun getIdUser(): String {
        return idUser
    }

    fun getToken(): String {
        return token
    }

    fun getMessage(): String {
        return message
    }
}