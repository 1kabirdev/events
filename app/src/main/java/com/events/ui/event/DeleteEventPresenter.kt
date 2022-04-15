package com.events.ui.event

import android.opengl.Visibility
import com.events.data.DataManager
import com.events.model.delete_event.ResponseDeleteEvent
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteEventPresenter(private var dataManager: DataManager) :
    BasePresenter<DeleteEventController.View>(), DeleteEventController.Presenter {

    private lateinit var call: Call<ResponseDeleteEvent>
    override fun responseDelete(event_id: String, user_id: String) {
        mvpView?.let {
            it.showProgressDelete()
            call = dataManager.deleteEvent(event_id, user_id)
            call.enqueue(object : Callback<ResponseDeleteEvent> {
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