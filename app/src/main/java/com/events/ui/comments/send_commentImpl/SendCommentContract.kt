package com.events.ui.comments.send_commentImpl

import com.events.model.comments.AddComment
import com.events.mvp.MvpView

interface SendCommentContract {
    interface View:MvpView{
        /**
         * send comment event
         */
        fun sendComment(addComment: AddComment)

    }

    interface Presenter:MvpView{
        /**
         * response send comment
         */
        fun responseSendComment(
            user_id: Int,
            event_id: Int,
            username: String,
            comment_text: String
        )
    }
}