package com.events.ui.setting_password.updateImpl

import com.events.model.profile.UpdatePassword
import com.events.mvp.MvpView

interface UpdatePasswordContract {
    interface View : MvpView {
        fun updatePassword(updatePassword: UpdatePassword)
        fun progress(show: Boolean)
        fun isEmptyPassword(message: String)
        fun error()
    }

    interface Presenter : MvpView {
        fun responseUpdatePassword(
            user_id: Int,
            old_password: String,
            new_password: String,
            conf_password: String
        )
    }
}