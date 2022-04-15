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
    override fun responseEvents(city: String) {
        mvpView?.let {
            it.showProgress()
            call = dataManager.loadListEvents(city)
            call.enqueue(object : Callback<ResponseListEvents> {
                override fun onResponse(
                    call: Call<ResponseListEvents>,
                    response: Response<ResponseListEvents>
                ) {
                    it.hideProgress()
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadEvent(res.getResponse())
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseListEvents>, t: Throwable) {
                    it.hideProgress()
                    it.noConnection()
                }
            })
        }
    }

    override fun responseEventsFilter(city: String) {
        mvpView?.let {
            call = dataManager.loadListEvents(city)
            call.enqueue(object : Callback<ResponseListEvents> {
                override fun onResponse(
                    call: Call<ResponseListEvents>,
                    response: Response<ResponseListEvents>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadEvent(res.getResponse())
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseListEvents>, t: Throwable) {
                    it.noConnection()
                }
            })
        }
    }
}