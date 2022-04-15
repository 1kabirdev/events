package com.events.ui.create_events

import com.events.data.DataManager
import com.events.model.create_event.ResponseCreateEvents
import com.events.mvp.BasePresenter
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        data_e: String,
        time_e: String,
        theme_e: String,
        city_e: String,
        cost_e: String,
        image_e: ByteArray
    ) {
        mvpView?.let {
            it.showProgress()
            val tsLong = System.currentTimeMillis() / 1000
            val nameImage = "$tsLong.jpg"
            val requestFile = RequestBody.create(MediaType.parse("image/jpeg"), image_e)
            val imageE = MultipartBody.Part.createFormData("image_e", nameImage, requestFile)
            val userId = RequestBody.create(MediaType.parse("text/plain"), user_id_e)
            val nameE = RequestBody.create(MediaType.parse("text/plain"), name_e)
            val descE = RequestBody.create(MediaType.parse("text/plain"), desc_e)
            val dataE = RequestBody.create(MediaType.parse("text/plain"), data_e)
            val timeE = RequestBody.create(MediaType.parse("text/plain"), time_e)
            val themeE = RequestBody.create(MediaType.parse("text/plain"), theme_e)
            val cityE = RequestBody.create(MediaType.parse("text/plain"), city_e)
            val costE = RequestBody.create(MediaType.parse("text/plain"), cost_e)

            call = dataManager.createEvents(
                userId, nameE, descE, dataE, timeE, themeE, cityE, costE, imageE
            )
            call.enqueue(object : Callback<ResponseCreateEvents> {
                override fun onResponse(
                    call: Call<ResponseCreateEvents>,
                    response: Response<ResponseCreateEvents>
                ) {
                    it.hideProgress()
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.createEvents(res)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseCreateEvents>, t: Throwable) {
                    it.hideProgress()
                    it.noConnection()
                }
            })
        }
    }
}