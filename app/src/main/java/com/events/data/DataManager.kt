package com.events.data

import com.events.model.comments.AddComment
import com.events.model.comments.ResponseComments
import com.events.model.create_event.ResponseCreateEvents
import com.events.model.delete_event.ResponseDeleteEvent
import com.events.model.profile.ResponseInfoProfile
import com.events.model.login.ResponseLogin
import com.events.model.events.ResponseEvents
import com.events.model.home.ResponseHomeEvents
import com.events.model.list_events.ResponseListEvents
import com.events.model.my_events.ResponseMyEvents
import com.events.model.organizer.ResponseInfoOrganizer
import com.events.model.profile.UpdateAvatar
import com.events.model.profile.UpdatePassword
import com.events.model.profile.UpdateProfile
import com.events.model.search.ResponseSearch
import com.events.model.similar_event.ResponseSimilarEvent
import com.events.model.theme_event.ResponseThemeEventHome
import com.events.model.theme_event.ResponseThemeEventList
import com.events.model.theme_event.Subscribe
import com.events.service.Api
import com.events.service.ServicesGenerator
import io.reactivex.Observable
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

    fun loadEventOrganizer(user_id: String, page: Int): Call<ResponseListEvents> {
        return api.loadListEventsOrganizer(user_id, page)
    }

    fun updateAvatar(
        user_id: RequestBody,
        image: MultipartBody.Part
    ): Call<UpdateAvatar> {
        return api.updateAvatar(user_id, image)
    }

    fun loadEvents(user_id: String, event_id: String): Call<ResponseEvents> {
        return api.loadEvents(user_id, event_id)
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


    fun searchEventList(name: String): Call<ResponseSearch> {
        return api.searchEvent(name)
    }

    fun updateProfile(user_id: Int, last_name: String, about: String): Call<UpdateProfile> {
        return api.updateProfile(user_id, last_name, about)
    }

    fun updatePassword(
        user_id: Int,
        old_password: String,
        new_password: String,
        conf_password: String
    ): Call<UpdatePassword> {
        return api.updatePassword(user_id, old_password, new_password, conf_password)
    }


    fun themeListEvent(
        theme: String,
        page: Int,
        user_id: Int
    ): Call<ResponseThemeEventList> {
        return api.themeListEvent(theme, page, user_id)
    }

    fun addSubscribe(
        user_id: Int,
        name_theme: String
    ): Call<Subscribe> {
        return api.addSunscribe(user_id, name_theme)

    }

}