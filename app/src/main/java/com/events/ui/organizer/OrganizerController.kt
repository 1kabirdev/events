package com.events.ui.organizer

import com.events.model.list_events.ListEvents
import com.events.model.organizer.ResponseInfoOrganizer
import com.events.mvp.MvpView

interface OrganizerController {

    interface View : MvpView {
        fun loadDataUserName(username: String)
        fun loadDataLastName(lastName: String)
        fun loadDataAbout(about: String)
        fun loadDataAvatar(avatar: String)
        fun getLoadEventsOrganizer(eventsList: ArrayList<ListEvents>)
        fun showProgressBarEvent()
        fun hideProgressBarEvent()
        fun showProgressBar()
        fun hideProgressBar()
        fun noConnection()

    }

    interface Presenter : MvpView {
        fun responseOrganizer(userId: String)
        fun responseEventOrganizer(userId: String, limit: String)
    }
}