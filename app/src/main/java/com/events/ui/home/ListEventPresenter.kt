package com.events.ui.home

import com.events.data.DataManager
import com.events.model.home.ResponseHomeEvents
import com.events.model.list_events.ResponseListEvents
import com.events.model.theme_event.ResponseThemeEventHome
import com.events.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListEventPresenter(private var dataManager: DataManager) :
    BasePresenter<ListEventController.View>(), ListEventController.Presenter {

    private var subscription = CompositeDisposable()
    private lateinit var callThemeEventHome: Call<ResponseThemeEventHome>

    override fun responseEvents(page: Int, theme: String) {
        mvpView?.let {
            it.showProgress(true)
            val subscribe = dataManager.loadHomeListEvents(page, theme).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data: ResponseHomeEvents ->
                    it.showProgress(false)
                    it.getLoadEvent(
                        data.infoEvents, data.response
                    )
                }, { error ->
                    it.showProgress(false)
                    it.noConnection()
                })

            subscription.add(subscribe)
        }
    }

    override fun responseEventsPage(page: Int, theme: String) {
        mvpView?.let {
            val subscribe = dataManager.loadHomeListEvents(page, theme).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data: ResponseHomeEvents ->
                    it.getLoadEventPage(
                        data.infoEvents, data.response
                    )
                }, { error ->
                    it.noConnectionPage()
                })

            subscription.add(subscribe)
        }
    }

    override fun responseThemeEventHome() {
        mvpView?.let {
            callThemeEventHome = dataManager.getLoadThemeEventHome()
            callThemeEventHome.enqueue(object : Callback<ResponseThemeEventHome> {
                override fun onResponse(
                    call: Call<ResponseThemeEventHome>,
                    response: Response<ResponseThemeEventHome>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            it.getLoadThemeEventHome(data.theme_event)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseThemeEventHome>, t: Throwable) {}
            })
        }
    }
}