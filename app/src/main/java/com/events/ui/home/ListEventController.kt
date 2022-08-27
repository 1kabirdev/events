package com.events.ui.home

import com.events.model.home.InfoEvents
import com.events.model.home.ListEvents
import com.events.model.home.ResponseHomeEvents
import com.events.model.theme_event.ResponseThemeEventHome
import com.events.model.theme_event.ResponseThemeEventList
import com.events.model.theme_event.ThemeEventHome
import com.events.mvp.MvpView

interface ListEventController {
    interface View : MvpView {
        fun getLoadEventPage(info: InfoEvents, eventsList: ArrayList<ListEvents>)
        fun getHomeView(
            responseHomeEvents: ResponseHomeEvents,
            theme: ResponseThemeEventHome
        )

        fun showProgress(show: Boolean)
        fun noConnection()
        fun noConnectionPage()
    }

    interface Presenter : MvpView {
        fun responseEventsPage(page: Int)
        fun responseLoadDataAll(page: Int)
    }
}