package com.events.ui.create_events

import com.events.data.DataManager
import com.events.model.create_event.ResponseCreateEvents
import com.events.mvp.BasePresenter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateEventsPresenter(private var dataManager: DataManager) :
    BasePresenter<CreateEventsController.View>(), CreateEventsController.Presenter {

    private lateinit var call: Call<ResponseCreateEvents>

    override fun responseCreateEvents(
        user_id_e: String,
        name_e: String,
        desc_e: String,
        location_e: String,
        data_e: String,
        time_e: String,
        theme_e: String,
        image_e: ByteArray
    ) {
        mvpView?.let {
            it.showProgress(true)
            val tsLong = System.currentTimeMillis() / 1000
            val nameImage = "$tsLong.jpg"
            val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), image_e)
            val imageE = MultipartBody.Part.createFormData("image_e", nameImage, requestFile)
            val userId = user_id_e.toRequestBody("text/plain".toMediaTypeOrNull())
            val nameE = name_e.toRequestBody("text/plain".toMediaTypeOrNull())
            val descE = desc_e.toRequestBody("text/plain".toMediaTypeOrNull())
            val locationE = location_e.toRequestBody("text/plain".toMediaTypeOrNull())
            val dataE = data_e.toRequestBody("text/plain".toMediaTypeOrNull())
            val timeE = time_e.toRequestBody("text/plain".toMediaTypeOrNull())
            val themeE = theme_e.toRequestBody("text/plain".toMediaTypeOrNull())

            call = dataManager.createEvents(
                userId, nameE, descE, locationE, dataE, timeE, themeE, imageE
            )
            call.enqueue(object : Callback<ResponseCreateEvents> {
                override fun onResponse(
                    call: Call<ResponseCreateEvents>,
                    response: Response<ResponseCreateEvents>
                ) {
                    it.showProgress(false)
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.createEvents(res)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseCreateEvents>, t: Throwable) {
                    it.showProgress(false)
                    it.noConnection()
                }
            })
        }
    }
}