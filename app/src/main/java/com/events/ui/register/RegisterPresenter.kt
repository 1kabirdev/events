package com.events.ui.register

import com.events.data.DataManager
import com.events.model.login.ResponseLogin
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterPresenter(private var dataManager: DataManager) :
    BasePresenter<RegisterController.View>(), RegisterController.Presenter {

    private lateinit var call: Call<ResponseLogin>

    override fun responseRegister(
        username: String,
        password: String,
        phone: String,
        last_name: String
    ) {
        mvpView?.let {
            it.showProgressView(true)
            call = dataManager.getRegisterAccountUser(username, password, phone, last_name)
            call.enqueue(object : Callback<ResponseLogin> {
                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    if (response.isSuccessful) {
                        it.showProgressView(false)
                        response.body()?.let { res ->
                            it.getRegisterUser(res.getStatus(), res.getMessage())
                            it.getDataSuccess(res.getToken(), res.getIdUser())
                        }
                    }

                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    it.showProgressView(false)
                    it.noConnection()
                }

            })
        }
    }
}