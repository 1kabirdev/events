package com.events.ui.comments

import com.events.data.DataManager
import com.events.model.comments.ResponseComments
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsPresenter(
    private var dataManager: DataManager
) : BasePresenter<CommentsContract.View>(), CommentsContract.Presenter {

    private lateinit var call: Call<ResponseComments>

    override fun responseLoadComments(event_id: Int, page: Int) {
        mvpView?.let {
            it.progress(true)
            call = dataManager.getComments(event_id, page)
            call.enqueue(object : Callback<ResponseComments> {
                override fun onResponse(
                    call: Call<ResponseComments>,
                    response: Response<ResponseComments>
                ) {
                    if (response.isSuccessful) {
                        it.progress(false)
                        response.body()?.let { data ->
                            it.loadComments(data.info, data.response)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseComments>, t: Throwable) {
                    it.progress(false)
                    it.errorConnection()
                }
            })
        }
    }
}