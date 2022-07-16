package com.events.ui.home

import com.events.model.list_events.InfoEvents
import com.events.model.list_events.ListEvents
import com.events.mvp.MvpView

interface ListEventController {
    interface View : MvpView {
        fun getLoadEvent(info: InfoEvents, eventsList: ArrayList<ListEvents>)
        fun getLoadEventPage(info: InfoEvents, eventsList: ArrayList<ListEvents>)
        fun showProgress(show: Boolean)
        fun noConnection()
    }

    interface Presenter : MvpView {
        fun responseEvents(page: Int)
        fun responseEventsPage(page: Int)
    }
}