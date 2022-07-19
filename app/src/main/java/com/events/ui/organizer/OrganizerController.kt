package com.events.ui.organizer

import com.events.model.list_events.InfoPage
import com.events.model.list_events.ListEvents
import com.events.model.list_events.Organize
import com.events.mvp.MvpView

interface OrganizerController {

    interface View : MvpView {
        fun getLoadEventsOrganizer(
            organize: Organize,
            infoPage: InfoPage,
            eventsList: ArrayList<ListEvents>
        )

        fun getLoadEventsOrganizerPage(
            infoPage: InfoPage,
            eventsList: ArrayList<ListEvents>
        )

        fun progressBar(show: Boolean)
        fun noConnection()
        fun noConnectionPage()
    }

    interface Presenter : MvpView {
        fun responseEventOrganizer(userId: String, page: Int)
        fun responseEventOrganizerPage(userId: String, page: Int)
    }
}