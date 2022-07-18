package com.events.ui.home

import com.events.data.DataManager
import com.events.model.list_events.ResponseListEvents
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListEventPresenter(private var dataManager: DataManager) :
    BasePresenter<ListEventController.View>(), ListEventController.Presenter {
    private lateinit var call: Call<ResponseListEvents>

    override fun responseEvents(page: Int) {
        mvpView?.let {
            it.showProgress(true)
            call = dataManager.loadHomeListEvents(page)
            call.enqueue(object : Callback<ResponseListEvents> {
                override fun onResponse(
                    call: Call<ResponseListEvents>,
                    response: Response<ResponseListEvents>
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

                override fun onFailure(call: Call<ResponseListEvents>, t: Throwable) {
                    it.showProgress(false)
                    it.noConnection()
                }
            })
        }
    }

    override fun responseEventsPage(page: Int) {
        mvpView?.let {
            call = dataManager.loadHomeListEvents(page)
            call.enqueue(object : Callback<ResponseListEvents> {
                override fun onResponse(
                    call: Call<ResponseListEvents>,
                    response: Response<ResponseListEvents>
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

                override fun onFailure(call: Call<ResponseListEvents>, t: Throwable) {}
            })
        }
    }
}