package com.events.ui.edit_profile

import com.events.model.profile.UpdateAvatar
import com.events.mvp.MvpView

interface EditProfileController {
    interface View : MvpView {
        fun updateAvatar(updateAvatar: UpdateAvatar)
        fun progressAvatar(show:Boolean)
        fun errorAvatar()
    }

    interface Presenter : MvpView {
        fun responseUpdateAvatar(user_id:Int,image:ByteArray)
    }
}