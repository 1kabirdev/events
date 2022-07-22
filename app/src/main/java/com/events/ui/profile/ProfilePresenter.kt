package com.events.ui.profile

import com.events.data.DataManager
import com.events.model.profile.ResponseInfoProfile
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePresenter(private var dataManager: DataManager) :
    BasePresenter<ProfileController.View>(),
    ProfileController.Presenter {

    private lateinit var call: Call<ResponseInfoProfile>

    override fun responseLoadDataProfile(user_id: String, page: Int) {
        mvpView?.let {
            it.progressBar(true)
            call = dataManager.getLoadInfoProfile(user_id, page)
            call.enqueue(object : Callback<ResponseInfoProfile> {
                override fun onResponse(
                    call: Call<ResponseInfoProfile>,
                    response: Response<ResponseInfoProfile>
                ) {
                    it.progressBar(false)
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadData(
                                res.profile,
                                res.infoPage,
                                res.responseEvents,
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseInfoProfile>, t: Throwable) {
                    it.progressBar(true)
                    it.noConnection()
                }
            })
        }
    }

    override fun responseLoadDataPage(user_id: String, page: Int) {
        mvpView?.let {
            call = dataManager.getLoadInfoProfile(user_id, page)
            call.enqueue(object : Callback<ResponseInfoProfile> {
                override fun onResponse(
                    call: Call<ResponseInfoProfile>,
                    response: Response<ResponseInfoProfile>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadDataPage(
                                res.infoPage,
                                res.responseEvents,
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseInfoProfile>, t: Throwable) {
                    it.noConnectPage()
                }
            })
        }
    }
}