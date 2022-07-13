package com.events.ui.organizer

import com.events.data.DataManager
import com.events.model.list_events.ResponseListEvents
import com.events.model.organizer.ResponseInfoOrganizer
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrganizerPresenter(private var dataManager: DataManager) :
    BasePresenter<OrganizerController.View>(), OrganizerController.Presenter {

    private lateinit var call: Call<ResponseInfoOrganizer>
    private lateinit var callEvent: Call<ResponseListEvents>

    override fun responseOrganizer(userId: String) {
        mvpView?.let {
            it.showProgressBar(true)
            call = dataManager.getLoadDataOrganizer(userId)
            call.enqueue(object : Callback<ResponseInfoOrganizer> {
                override fun onResponse(
                    call: Call<ResponseInfoOrganizer>,
                    response: Response<ResponseInfoOrganizer>
                ) {
                    it.showProgressBar(false)
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.loadDataOrganizer(
                                res.getUsername(),
                                res.getLastName(),
                                res.getAbout(),
                                res.getAvatar()
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseInfoOrganizer>, t: Throwable) {
                    it.showProgressBar(false)
                    it.noConnection()
                }
            })
        }
    }

    override fun responseEventOrganizer(userId: String, limit: String) {
        mvpView?.let {
            it.showProgressBarEvent(true)
            callEvent = dataManager.loadEventOrganizer(userId, limit)
            callEvent.enqueue(object : Callback<ResponseListEvents> {
                override fun onResponse(
                    call: Call<ResponseListEvents>,
                    response: Response<ResponseListEvents>
                ) {
                    it.showProgressBarEvent(false)
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadEventsOrganizer(res.getResponse())
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseListEvents>, t: Throwable) {
                    it.showProgressBarEvent(false)
                }
            })
        }
    }
}