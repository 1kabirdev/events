package com.events.ui.login

import com.events.mvp.MvpView

interface LoginController {
    interface View : MvpView {
        fun getLoginSuccessFully(status: Boolean, message: String)
        fun getDataSuccess(user_id: String,token: String)
        fun showProgressView(show:Boolean)
        fun connectionView()
    }

    interface Presenter : MvpView {
        fun responseLoginSuccessFully(username: String, password: String)
    }
}