package com.events.ui.profile

import com.events.data.DataManager
import com.events.model.profile.ResponseInfoProfile
import com.events.model.my_events.ResponseMyEvents
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilePresenter(private var dataManager: DataManager) :
    BasePresenter<ProfileController.View>(),
    ProfileController.Presenter {
    private lateinit var call: Call<ResponseInfoProfile>
    private lateinit var callEvents: Call<ResponseMyEvents>
    override fun responseLoadDataProfile(token: String) {
        mvpView?.let {
            it.showProgressView()
            call = dataManager.getLoadInfoProfile(token)
            call.enqueue(object : Callback<ResponseInfoProfile> {
                override fun onResponse(
                    call: Call<ResponseInfoProfile>,
                    response: Response<ResponseInfoProfile>
                ) {
                    it.hideProgressView()
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadData(
                                res.getUsername(),
                                res.getAvatar(),
                                res.getPhone(),
                                res.getLastName(),
                                res.getCreateData(),
                                res.getAbout()
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseInfoProfile>, t: Throwable) {
                    it.hideProgressView()
                    it.noConnection()
                }

            })
        }
    }

    override fun responseLoadMyEvents(user_id: String, limit: String) {
        mvpView?.let {
            it.showProgressViewEvents()
            callEvents = dataManager.loadMyEvents(user_id, limit)
            callEvents.enqueue(object : Callback<ResponseMyEvents> {
                override fun onResponse(
                    call: Call<ResponseMyEvents>,
                    response: Response<ResponseMyEvents>
                ) {
                    it.hideProgressViewEvents()
                    if (response.isSuccessful) {
                        response.body()?.let { res ->
                            it.getLoadMyEvents(res.getResponse())
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseMyEvents>, t: Throwable) {
                    it.hideProgressViewEvents()
                    it.noConnection()
                }
            })
        }
    }
}