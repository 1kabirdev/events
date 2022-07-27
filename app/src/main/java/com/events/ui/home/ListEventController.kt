package com.events.ui.home

import com.events.model.home.InfoEvents
import com.events.model.home.ListEvents
import com.events.mvp.MvpView

interface ListEventController {
    interface View : MvpView {
        fun getLoadEvent(info: InfoEvents, eventsList: ArrayList<ListEvents>)
        fun getLoadEventPage(info: InfoEvents, eventsList: ArrayList<ListEvents>)
        fun showProgress(show: Boolean)
        fun noConnection()
        fun noConnectionPage()
    }

    interface Presenter : MvpView {
        fun responseEvents(page: Int, theme: String)
        fun responseEventsPage(page: Int, theme: String)
    }
}