package com.events.ui.profile

import com.events.model.my_events.MyEventsList
import com.events.mvp.MvpView

interface ProfileController {
    interface View : MvpView {
        fun getLoadData(
            username: String,
            avatar: String,
            phone: String,
            last_name: String,
            create_data: String,
            about: String
        )

        fun getLoadMyEvents(eventsList: ArrayList<MyEventsList>)
        fun showProgressViewEvents()
        fun hideProgressViewEvents()

        fun showProgressView()
        fun hideProgressView()
        fun noConnection()
    }

    interface Presenter : MvpView {
        fun responseLoadDataProfile(token: String)
        fun responseLoadMyEvents(user_id: String, limit: String)
    }
}