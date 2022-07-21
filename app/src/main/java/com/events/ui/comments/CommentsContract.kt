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
         * load comment event paging
         */

        fun loadCommentPage(info: Info, commentsList: ArrayList<CommentsList>)


    }

    interface Presenter : MvpView {
        /**
         * response load comment
         */
        fun responseLoadComments(event_id: Int, page: Int)

        /**
         * response load comment paging
         */
        fun responseLoadCommentsPage(event_id: Int, page: Int)

    }
}