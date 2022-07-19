package com.events.ui.home

import com.events.data.DataManager
import com.events.model.home.ResponseHomeEvents
import com.events.model.list_events.ResponseListEvents
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListEventPresenter(private var dataManager: DataManager) :
    BasePresenter<ListEventController.View>(), ListEventController.Presenter {
    private lateinit var call: Call<ResponseHomeEvents>

    override fun responseEvents(page: Int) {
        mvpView?.let {
            it.showProgress(true)
            call = dataManager.loadHomeListEvents(page)
            call.enqueue(object : Callback<ResponseHomeEvents> {
                override fun onResponse(
                    call: Call<ResponseHomeEvents>,
                    response: Response<ResponseHomeEvents>
                ) {
                    it.showProgress(false)
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadEvent(
                                res.infoEvents,
                                res.response
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseHomeEvents>, t: Throwable) {
                    it.showProgress(false)
                    it.noConnection()
                }
            })
        }
    }

    override fun responseEventsPage(page: Int) {
        mvpView?.let {
            call = dataManager.loadHomeListEvents(page)
            call.enqueue(object : Callback<ResponseHomeEvents> {
                override fun onResponse(
                    call: Call<ResponseHomeEvents>,
                    response: Response<ResponseHomeEvents>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadEventPage(
                                res.infoEvents,
                                res.response
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseHomeEvents>, t: Throwable) {}
            })
        }
    }
}