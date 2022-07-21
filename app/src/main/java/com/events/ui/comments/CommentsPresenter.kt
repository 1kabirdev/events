package com.events.ui.comments

import com.events.data.DataManager
import com.events.model.comments.AddComment
import com.events.model.comments.ResponseComments
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentsPresenter(
    private var dataManager: DataManager
) : BasePresenter<CommentsContract.View>(), CommentsContract.Presenter {

    private lateinit var callComments: Call<ResponseComments>

    override fun responseLoadComments(event_id: Int, page: Int) {
        mvpView?.let {
            it.progress(true)
            callComments = dataManager.getComments(event_id, page)
            callComments.enqueue(object : Callback<ResponseComments> {
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

    override fun responseLoadCommentsPage(event_id: Int, page: Int) {
        mvpView?.let {
            callComments = dataManager.getComments(event_id, page)
            callComments.enqueue(object : Callback<ResponseComments> {
                override fun onResponse(
                    call: Call<ResponseComments>,
                    response: Response<ResponseComments>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            it.loadCommentPage(data.info, data.response)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseComments>, t: Throwable) {}
            })
        }
    }

}