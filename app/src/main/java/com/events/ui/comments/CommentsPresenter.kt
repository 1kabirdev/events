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
    private lateinit var callSendComment: Call<AddComment>

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

    override fun responseSendComment(
        user_id: Int,
        event_id: Int,
        username: String,
        comment_text: String
    ) {
        mvpView?.let {
            callSendComment = dataManager.sendComment(user_id, event_id, username, comment_text)
            callSendComment.enqueue(object : Callback<AddComment> {
                override fun onResponse(call: Call<AddComment>, response: Response<AddComment>) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            it.sendComment(data)
                        }
                    }
                }

                override fun onFailure(call: Call<AddComment>, t: Throwable) = Unit
            })
        }
    }
}