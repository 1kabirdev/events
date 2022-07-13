package com.events.ui.home

import com.events.model.list_events.ListEvents
import com.events.mvp.MvpView

interface ListEventController {
    interface View : MvpView {
        fun getLoadEvent(eventsList: ArrayList<ListEvents>)
        fun showProgress(show:Boolean)
        fun noConnection()
    }

    interface Presenter : MvpView {
        fun responseEvents(city: String)
        fun responseEventsFilter(city: String)
    }
}