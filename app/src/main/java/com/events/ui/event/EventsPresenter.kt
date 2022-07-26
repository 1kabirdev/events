package com.events.ui.event

import com.events.data.DataManager
import com.events.model.events.ResponseEvents
import com.events.model.similar_event.ResponseSimilarEvent
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventsPresenter(private val dataManager: DataManager) :
    BasePresenter<EventsController.View>(),
    EventsController.Presenter {

    private lateinit var call: Call<ResponseEvents>
    private lateinit var callSimilar: Call<ResponseSimilarEvent>

    override fun responseLoadEvents(user_id_e: String, event_id: String) {
        mvpView?.let {
            it.showProgressBar(true)
            call = dataManager.loadEvents(user_id_e, event_id)
            call.enqueue(object : Callback<ResponseEvents> {
                override fun onResponse(
                    call: Call<ResponseEvents>,
                    response: Response<ResponseEvents>
                ) {
                    it.showProgressBar(false)
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadData(res.getResponse(), res.getResponse().getUser())
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseEvents>, t: Throwable) {
                    it.showProgressBar(false)
                    it.noConnection()
                }
            })
        }
    }

    override fun responseSimilarEvents(theme: String, event_id: Int) {
        mvpView?.let { view ->
            callSimilar = dataManager.similarEvent(theme, event_id)
            callSimilar.enqueue(object : Callback<ResponseSimilarEvent> {
                override fun onResponse(
                    call: Call<ResponseSimilarEvent>,
                    response: Response<ResponseSimilarEvent>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { similar ->
                            view.similarEventList(similar.similar)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseSimilarEvent>, t: Throwable) {}
            })
        }
    }
}








