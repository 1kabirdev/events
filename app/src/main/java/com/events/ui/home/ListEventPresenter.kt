package com.events.ui.home

import com.events.model.home.ResponseHomeEvents
import com.events.model.theme_event.DetailsViewModel
import com.events.model.theme_event.ResponseThemeEventHome
import com.events.mvp.BasePresenter
import com.events.service.Api
import com.events.service.ServicesGenerator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListEventPresenter :
    BasePresenter<ListEventController.View>(), ListEventController.Presenter {

    private var subscription = CompositeDisposable()
    private var api = ServicesGenerator.createService(Api::class.java)

    override fun responseEventsPage(page: Int, theme: String) {
        mvpView?.let {
            val subscribe = api.loadHomeListEvents(page, theme).subscribeOn(Schedulers.io())
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

    override fun responseLoadDataAll(page: Int, theme: String) {
        mvpView?.let { view ->
            view.showProgress(true)
            val subscribe = Observable.zip(api.loadHomeListEvents(page, theme),
                api.loadThemeEventHome(),
                object : Function2<ResponseHomeEvents, ResponseThemeEventHome, DetailsViewModel> {
                    override fun invoke(
                        responseHomeEvents: ResponseHomeEvents,
                        themeList: ResponseThemeEventHome,
                    ): DetailsViewModel {
                        return createDetailsViewModel(responseHomeEvents, themeList)
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ model: DetailsViewModel? ->
                    view.showProgress(false)
                    view.getHomeView(model!!.responseEvents, model.theme_event)
                }, { error ->
                    view.showProgress(false)
                    view.noConnection()
                    print("ERROR: ${error.localizedMessage}")
                })

            subscription.add(subscribe)
        }
    }

    private fun createDetailsViewModel(
        responseHomeEvents: ResponseHomeEvents,
        theme: ResponseThemeEventHome
    ): DetailsViewModel {
        return DetailsViewModel(responseHomeEvents, theme)
    }
}