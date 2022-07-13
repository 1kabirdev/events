package com.events.ui.register

import com.events.mvp.MvpView

interface RegisterController {
    interface View : MvpView {
        fun getRegisterUser(status: Boolean, message: String)
        fun getDataSuccess(token: String,user_id: String)
        fun showProgressView(show:Boolean)
        fun noConnection()
    }

    interface Presenter : MvpView {
        fun responseRegister(username: String, password: String, phone: String, last_name: String)
    }
}