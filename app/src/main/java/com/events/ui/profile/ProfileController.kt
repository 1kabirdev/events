package com.events.ui.profile

import com.events.model.profile.InfoPage
import com.events.model.profile.ProfileData
import com.events.model.profile.ResponseEvents
import com.events.mvp.MvpView

interface ProfileController {
    interface View : MvpView {
        fun getLoadData(
            profileData: ProfileData,
            infoPage: InfoPage,
            eventsList: ArrayList<ResponseEvents>
        )

        fun progressBar(show: Boolean)
        fun noConnection()
    }

    interface Presenter : MvpView {
        fun responseLoadDataProfile(user_id: String, page: Int)
    }
}