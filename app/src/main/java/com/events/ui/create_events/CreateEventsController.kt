package com.events.ui.create_events

import com.events.model.create_event.ResponseCreateEvents
import com.events.mvp.MvpView

interface CreateEventsController {
    interface View : MvpView {
        fun createEvents(responseCreateEvents: ResponseCreateEvents)
        fun showProgress(show:Boolean)
        fun noConnection()
    }

    interface Presenter : MvpView {
        fun responseCreateEvents(
            user_id_e: String,
            name_e: String,
            desc_e: String,
            data_e: String,
            time_e: String,
            theme_e: String,
            image_e: ByteArray,
        )
    }
}