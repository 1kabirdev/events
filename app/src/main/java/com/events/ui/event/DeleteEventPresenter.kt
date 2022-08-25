package com.events.ui.event

import android.opengl.Visibility
import com.events.data.DataManager
import com.events.model.delete_event.ResponseDeleteEvent
import com.events.mvp.BasePresenter
import com.events.service.Api
import com.events.service.ServicesGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteEventPresenter :
    BasePresenter<DeleteEventController.View>(), DeleteEventController.Presenter {

    private var api = ServicesGenerator.createService(Api::class.java)

    override fun responseDelete(event_id: String, user_id: String) {
        mvpView?.let {
            it.showProgressDelete()
            api.deleteEvent(event_id, user_id)
                .enqueue(object : Callback<ResponseDeleteEvent> {
                    override fun onResponse(
                        call: Call<ResponseDeleteEvent>,
                        response: Response<ResponseDeleteEvent>
                    ) {
                        it.hideProgressDelete()
                        if (response.isSuccessful) {
                            response.body()?.let { res ->
                                if (res.getStatus() == true) {
                                    it.loadDeleteEvent(res.getMessage())
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ResponseDeleteEvent>, t: Throwable) {
                        it.hideProgressDelete()
                        it.noConnectionDelete()
                    }
                })
        }
    }
}