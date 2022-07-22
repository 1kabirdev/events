package com.events.ui.edit_profile

import com.events.data.DataManager
import com.events.mvp.BasePresenter

class EditProfilePresenter(private var dataManager: DataManager) :
    BasePresenter<EditProfileController.View>(), EditProfileController.Presenter {

    override fun responseUpdateAvatar(user_id: Int, image: ByteArray) {

    }
}