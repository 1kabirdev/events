package com.events.ui.setting_password.updateImpl

import com.events.data.DataManager
import com.events.model.profile.UpdatePassword
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePasswordPresenterImpl(
    private var dataManager: DataManager
) : BasePresenter<UpdatePasswordContract.View>(), UpdatePasswordContract.Presenter {

    private lateinit var call: Call<UpdatePassword>

    override fun responseUpdatePassword(
        user_id: Int,
        old_password: String,
        new_password: String,
        conf_password: String
    ) {
        mvpView?.let { view ->
            view.progress(true)
            call = dataManager.updatePassword(user_id, old_password, new_password, conf_password)
            call.enqueue(object : Callback<UpdatePassword> {
                override fun onResponse(
                    call: Call<UpdatePassword>,
                    response: Response<UpdatePassword>
                ) {
                    view.progress(false)
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            when (data.message) {
                                "failed old password" -> {
                                    view.isEmptyPassword("Введите ваш старый пароль")
                                }
                                "failed new password" -> {
                                    view.isEmptyPassword("Введите новый пароль")
                                }
                                "failed passwords do not match" -> {
                                    view.isEmptyPassword("Пароли не совпадают")
                                }
                                "failed your old password is wrong" -> {
                                    view.isEmptyPassword("Старый пароль неправильный")
                                }
                                else -> {
                                    if (data.status) {
                                        view.updatePassword(updatePassword = data)
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<UpdatePassword>, t: Throwable) {
                    view.progress(false)
                    view.error()
                }
            })
        }
    }
}