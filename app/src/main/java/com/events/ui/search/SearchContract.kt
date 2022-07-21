package com.events.ui.search

import com.events.model.search.Event
import com.events.mvp.MvpView


interface SearchContract {
    interface View : MvpView {
        fun onSearchEvent(event: ArrayList<Event>)
        fun progress(show: Boolean)
        fun noConnection()
    }

    interface Presenter : MvpView {
        fun responseSearch(name: String)
    }
}