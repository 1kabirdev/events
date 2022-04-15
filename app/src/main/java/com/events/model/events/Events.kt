package com.events.model.events

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Events {
    @SerializedName("id_e")
    @Expose
    private var idE: String = ""

    @SerializedName("name_e")
    @Expose
    private var nameE: String = ""

    @SerializedName("desc_e")
    @Expose
    private var descE: String = ""

    @SerializedName("image_e")
    @Expose
    private var imageE: String = ""

    @SerializedName("data_e")
    @Expose
    private var dataE: String = ""

    @SerializedName("time_e")
    @Expose
    private var timeE: String = ""

    @SerializedName("theme_e")
    @Expose
    private var themeE: String = ""

    @SerializedName("city_e")
    @Expose
    private var cityE: String = ""

    @SerializedName("cost_e")
    @Expose
    private var costE: String = ""

    @SerializedName("create_date_e")
    @Expose
    private var createDateE: String = ""

    @SerializedName("user")
    @Expose
    private lateinit var user: User

    fun getIdE(): String {
        return idE
    }

    fun getNameE(): String {
        return nameE
    }

    fun getDescE(): String {
        return descE
    }

    fun getImageE(): String {
        return imageE
    }


    fun getDataE(): String {
        return dataE
    }

    fun getTimeE(): String {
        return timeE
    }

    fun getThemeE(): String {
        return themeE
    }

    fun getCityE(): String {
        return cityE
    }

    fun getCostE(): String {
        return costE
    }

    fun getCreateDateE(): String {
        return createDateE
    }

    fun getUser(): User {
        return user
    }

}