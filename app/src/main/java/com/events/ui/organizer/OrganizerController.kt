package com.events.ui.organizer

import com.events.model.list_events.ListEvents
import com.events.model.organizer.ResponseInfoOrganizer
import com.events.mvp.MvpView

interface OrganizerController {

    interface View : MvpView {
        fun loadDataOrganizer(username: String, lastName: String, about: String, avatar: String)
        fun getLoadEventsOrganizer(eventsList: ArrayList<ListEvents>)
        fun showProgressBarEvent(show:Boolean)
        fun showProgressBar(show: Boolean)
        fun noConnection()

    }

    interface Presenter : MvpView {
        fun responseOrganizer(userId: String)
        fun responseEventOrganizer(userId: String, limit: String)
    }
}