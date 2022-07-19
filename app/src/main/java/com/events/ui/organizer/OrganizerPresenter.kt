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

    private lateinit var callEvent: Call<ResponseListEvents>

    override fun responseEventOrganizer(userId: String, page: Int) {
        mvpView?.let {
            it.progressBar(true)
            callEvent = dataManager.loadEventOrganizer(userId, page)
            callEvent.enqueue(object : Callback<ResponseListEvents> {
                override fun onResponse(
                    call: Call<ResponseListEvents>,
                    response: Response<ResponseListEvents>
                ) {
                    it.progressBar(false)
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            it.getLoadEventsOrganizer(
                                data.organize,
                                data.infoPage,
                                data.response
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseListEvents>, t: Throwable) {
                    it.progressBar(false)
                    it.noConnection()
                }
            })
        }
    }

    override fun responseEventOrganizerPage(userId: String, page: Int) {
        mvpView?.let {
            callEvent = dataManager.loadEventOrganizer(userId, page)
            callEvent.enqueue(object : Callback<ResponseListEvents> {
                override fun onResponse(
                    call: Call<ResponseListEvents>,
                    response: Response<ResponseListEvents>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            it.getLoadEventsOrganizerPage(
                                data.infoPage,
                                data.response
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseListEvents>, t: Throwable) {
                    it.noConnectionPage()
                }
            })
        }
    }
}