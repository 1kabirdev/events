package com.events.ui.register

import com.events.mvp.MvpView

interface RegisterController {
    interface View : MvpView {
        fun getRegisterUser(status: Boolean, message: String)
        fun getTokenUser(token: String)
        fun getUserId(user_id: String)
        fun showProgressView()
        fun hideProgressView()
        fun noConnection()
    }

    interface Presenter : MvpView {
        fun responseRegister(username: String, password: String, phone: String, last_name: String)
    }
}