package com.events.ui.profile

import com.events.model.profile.ResponseInfoProfile
import com.events.mvp.MvpView

interface ProfileController {
    interface View : MvpView {
        fun getLoadData(
            profileData: ResponseInfoProfile.ProfileData,
            infoPage: ResponseInfoProfile.InfoPage,
            eventsList: ArrayList<ResponseInfoProfile.ResponseEvents>
        )

        fun getLoadDataPage(
            infoPage: ResponseInfoProfile.InfoPage,
            eventsList: ArrayList<ResponseInfoProfile.ResponseEvents>
        )

        fun progressBar(show: Boolean)
        fun noConnection()
        fun noConnectPage()
    }

    interface Presenter : MvpView {
        fun responseLoadDataProfile(user_id: String, page: Int)
        fun responseLoadDataPage(user_id: String, page: Int)
    }
}