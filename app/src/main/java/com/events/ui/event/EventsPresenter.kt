package com.events.ui.event

import com.events.data.DataManager
import com.events.model.events.ResponseEvents
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsPresenter(private val dataManager: DataManager) :
    BasePresenter<EventsController.View>(),
    EventsController.Presenter {

    private lateinit var call: Call<ResponseEvents>

    override fun responseLoadEvents(user_id_e: String, event_id: String) {
        mvpView?.let {
            it.showProgressBar()
            call = dataManager.loadEvents(user_id_e, event_id)
            call.enqueue(object : Callback<ResponseEvents> {
                override fun onResponse(
                    call: Call<ResponseEvents>,
                    response: Response<ResponseEvents>
                ) {
                    it.hideProgressBar()
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadData(res.getResponse(), res.getResponse().getUser())
                            if (res.getResponse().getCostE() != "" && res.getResponse().getCostE() != "0"){
                                it.getLoadCost(res.getResponse().getCostE())
                            }else{
                                it.getLoadCostNot()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseEvents>, t: Throwable) {
                    it.hideProgressBar()
                    it.noConnection()
                }
            })
        }
    }
}








