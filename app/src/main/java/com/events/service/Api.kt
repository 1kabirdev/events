package com.events.service

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
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("login.php")
    fun loginAccountUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ResponseLogin>

    @FormUrlEncoded
    @POST("register.php")
    fun registerAccountUser(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("phone") phone: String,
        @Field("last_name") last_name: String
    ): Call<ResponseLogin>

    /**
     * Load profile and event user
     */
    @GET("profile.php")
    fun loadDataProfile(
        @Query("user_id") user_id: String,
        @Query("page") page: Int
    ): Call<ResponseInfoProfile>

    @GET("event_organizer.php")
    fun loadListEventsOrganizer(
        @Query("user_id_e") user_id: String,
        @Query("page") page: Int
    ): Call<ResponseListEvents>

    @Multipart
    @POST("create_events.php")
    fun createEvents(
        @Part("user_id_e") user_id_e: RequestBody,
        @Part("name_e") name_e: RequestBody,
        @Part("desc_e") desc_e: RequestBody,
        @Part("data_e") data_e: RequestBody,
        @Part("time_e") time_e: RequestBody,
        @Part("theme_e") theme_e: RequestBody,
        @Part("city_e") city_e: RequestBody,
        @Part("cost_e") cost_e: RequestBody,
        @Part image_e: MultipartBody.Part,
    ): Call<ResponseCreateEvents>

    @GET("home_list_events.php")
    fun loadHomeListEvents(
        @Query("page") page: Int
    ): Call<ResponseHomeEvents>

    @GET("events.php")
    fun loadEvents(
        @Query("user_id_e") user_id: String,
        @Query("event_id") event_id: String
    ): Call<ResponseEvents>

    @FormUrlEncoded
    @POST("delete_event.php")
    fun deleteEvent(
        @Field("event_id") event_id: String,
        @Field("user_id") user_id: String,
    ): Call<ResponseDeleteEvent>

    @GET("comments.php")
    fun getCommentsList(
        @Query("event_id") event_id: Int,
        @Query("page") page: Int
    ): Call<ResponseComments>

    @FormUrlEncoded
    @POST("send_comment.php")
    fun sendComment(
        @Field("user_id") user_id: Int,
        @Field("event_id") event_id: Int,
        @Field("username") username: String,
        @Field("comment_text") comment_text: String
    ): Call<AddComment>

    @GET("search_event.php")
    fun searchEvent(
        @Query("name") name: String
    )
}