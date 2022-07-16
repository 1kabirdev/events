package com.events.data

import com.events.model.comments.AddComment
import com.events.model.comments.ResponseComments
import com.events.model.create_event.ResponseCreateEvents
import com.events.model.delete_event.ResponseDeleteEvent
import com.events.model.profile.ResponseInfoProfile
import com.events.model.login.ResponseLogin
import com.events.model.events.ResponseEvents
import com.events.model.list_events.ResponseListEvents
import com.events.model.my_events.ResponseMyEvents
import com.events.model.organizer.ResponseInfoOrganizer
import com.events.service.Api
import com.events.service.ServicesGenerator
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class DataManager {
    private val api = ServicesGenerator.createService(Api::class.java)

    fun getLoginAccountUser(username: String, password: String): Call<ResponseLogin> {
        return api.loginAccountUser(username, password)
    }

    fun getRegisterAccountUser(
        username: String,
        password: String,
        phone: String,
        last_name: String
    ): Call<ResponseLogin> {
        return api.registerAccountUser(username, password, phone, last_name)
    }

    fun getLoadDataOrganizer(user_id: String): Call<ResponseInfoOrganizer> {
        return api.loadDataOrganizer(user_id)
    }


    fun loadEventOrganizer(user_id: String, limit: String): Call<ResponseListEvents> {
        return api.loadListEventsOrganizer(user_id, limit)
    }

    fun createEvents(
        user_id_e: RequestBody,
        name_e: RequestBody,
        desc_e: RequestBody,
        data_e: RequestBody,
        time_e: RequestBody,
        theme_e: RequestBody,
        city_e: RequestBody,
        cost_e: RequestBody,
        image_e: MultipartBody.Part,
    ): Call<ResponseCreateEvents> {
        return api.createEvents(
            user_id_e, name_e, desc_e, data_e, time_e, theme_e, city_e, cost_e, image_e
        )
    }

    fun getLoadInfoProfile(token: String): Call<ResponseInfoProfile> {
        return api.loadDataProfile(token)
    }

    fun loadMyEvents(user_id: String, limit: String): Call<ResponseMyEvents> {
        return api.loadMyEvents(user_id, limit)
    }

    fun loadListEvents(page: Int): Call<ResponseListEvents> {
        return api.loadListEvents(page)
    }

    fun loadEvents(user_id: String, event_id: String): Call<ResponseEvents> {
        return api.loadEvents(user_id, event_id)
    }

    fun deleteEvent(event_id: String, user_id: String): Call<ResponseDeleteEvent> {
        return api.deleteEvent(event_id, user_id)
    }

    fun getComments(event_id: Int, page: Int): Call<ResponseComments> {
        return api.getCommentsList(event_id, page)
    }

    fun sendComment(
        user_id: Int,
        event_id: Int,
        username: String,
        comment_text: String
    ): Call<AddComment> {
        return api.sendComment(user_id, event_id, username, comment_text)
    }

}