package com.events.ui.comments.send_commentImpl

import com.events.data.DataManager
import com.events.model.comments.AddComment
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendCommentPresenter(
    private var dataManager: DataManager
) : BasePresenter<SendCommentContract.View>(), SendCommentContract.Presenter {

    private lateinit var callSendComment: Call<AddComment>

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