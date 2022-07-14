package com.events.ui.comments

import com.events.model.comments.AddComment
import com.events.model.comments.CommentsList
import com.events.model.comments.Info
import com.events.mvp.MvpView

interface CommentsContract {
    interface View : MvpView {
        /**
         * load comment event
         */
        fun loadComments(info: Info, commentsList: ArrayList<CommentsList>)
        fun progress(show: Boolean)
        fun errorConnection()

        /**
         * send comment event
         */
        fun sendComment(addComment: AddComment)

    }

    interface Presenter : MvpView {
        /**
         * response load comment
         */
        fun responseLoadComments(event_id: Int, page: Int)

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