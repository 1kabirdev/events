package com.events.ui.event

import com.events.model.events.Events
import com.events.model.events.User
import com.events.mvp.MvpView

interface EventsController : MvpView {
    interface View : MvpView {
        fun getLoadData(events: Events, user: User)
        fun getLoadCost(cost: String)
        fun getLoadCostNot()
        fun showProgressBar(show:Boolean)
        fun noConnection()
    }

    interface Presenter : MvpView {
        fun responseLoadEvents(user_id_e: String, event_id: String)
    }
}
