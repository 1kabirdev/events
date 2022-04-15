package com.events.ui.event

import com.events.mvp.MvpView

interface DeleteEventController {

    interface View : MvpView {
        fun loadDeleteEvent(message: String)
        fun showProgressDelete()
        fun hideProgressDelete()
        fun noConnectionDelete()
    }

    interface Presenter : MvpView {
        fun responseDelete(event_id: String, user_id: String)
    }
}