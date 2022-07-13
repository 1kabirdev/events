package com.events.ui.login

import com.events.data.DataManager
import com.events.model.login.ResponseLogin
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private var dataManager: DataManager) : BasePresenter<LoginController.View>(),
    LoginController.Presenter {

    private lateinit var call: Call<ResponseLogin>

    override fun responseLoginSuccessFully(username: String, password: String) {
        mvpView?.let {
            it.showProgressView(true)
            call = dataManager.getLoginAccountUser(username, password)
            call.enqueue(object : Callback<ResponseLogin> {
                override fun onResponse(
                    call: Call<ResponseLogin>,
                    response: Response<ResponseLogin>
                ) {
                    if (response.isSuccessful) {
                        it.showProgressView(false)
                        response.body()?.let { res ->
                            it.getLoginSuccessFully(res.getStatus(), res.getMessage())
                            it.getDataSuccess( res.getIdUser(), res.getToken())
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    it.showProgressView(false)
                    it.connectionView()
                }
            })
        }
    }
}