package com.events.ui.login

import com.events.mvp.MvpView

interface LoginController {
    interface View : MvpView {
        fun getLoginSuccessFully(status: Boolean, message: String)
        fun getLoadUserId(user_id: String)
        fun getLoadToken(token: String)
        fun showProgressView()
        fun hideProgressView()
        fun connectionView()
    }

    interface Presenter : MvpView {
        fun responseLoginSuccessFully(username: String, password: String)
    }
}