package com.events.ui.event

import com.events.model.events.Events
import com.events.model.events.User
import com.events.model.similar_event.SimilarList
import com.events.mvp.MvpView

interface EventsController : MvpView {
    interface View : MvpView {
        fun getLoadData(events: Events, user: User)
        fun similarEventList(similarList: ArrayList<SimilarList>)
        fun showProgressBar(show: Boolean)
        fun noConnection()
    }

    interface Presenter : MvpView {
        fun responseLoadEvents(user_id_e: String, event_id: String)
        fun responseSimilarEvents(theme: String, event_id: Int)
    }
}
