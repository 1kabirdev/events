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
import com.events.model.profile.UpdateAvatar
import com.events.model.profile.UpdatePassword
import com.events.model.profile.UpdateProfile
import com.events.model.search.ResponseSearch
import com.events.model.similar_event.ResponseSimilarEvent
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
        @Part("location_e") location_e: RequestBody,
        @Part("data_e") data_e: RequestBody,
        @Part("time_e") time_e: RequestBody,
        @Part("theme_e") theme_e: RequestBody,
        @Part image: MultipartBody.Part,
    ): Call<ResponseCreateEvents>

    @Multipart
    @POST("create_avatar.php")
    fun updateAvatar(
        @Part("user_id") user_id: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<UpdateAvatar>

    @GET("home_list_events.php")
    fun loadHomeListEvents(
        @Query("page") page: Int,
        @Query("theme") theme: String
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
    ): Call<ResponseSearch>

    @FormUrlEncoded
    @POST("update_profile.php")
    fun updateProfile(
        @Field("user_id") user_id: Int,
        @Field("last_name") last_name: String,
        @Field("about") about: String
    ): Call<UpdateProfile>

    @FormUrlEncoded
    @POST("update_password.php")
    fun updatePassword(
        @Field("user_id") user_id: Int,
        @Field("old_password") old_password: String,
        @Field("new_password") new_password: String,
        @Field("conf_password") conf_password: String
    ): Call<UpdatePassword>

    @GET("similar_event.php")
    fun similarEvent(
        @Query("theme") theme: String,
        @Query("event_id") event_id: Int
    ): Call<ResponseSimilarEvent>
}