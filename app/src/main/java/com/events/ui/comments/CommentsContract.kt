package com.events.ui.comments

import com.events.model.comments.CommentsList
import com.events.model.comments.Info
import com.events.mvp.MvpView

interface CommentsContract {
    interface View : MvpView {
        fun loadComments(info: Info, commentsList: ArrayList<CommentsList>)
        fun progress(show: Boolean)
        fun errorConnection()
    }

    interface Presenter : MvpView {
        fun responseLoadComments(event_id: Int, page: Int)
    }
}