package com.events.ui.comments.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.events.databinding.ItemListCommentsBinding
import com.events.databinding.ItemLoadingViewBinding
import com.events.model.comments.CommentsList
import com.events.utill.Constants
import com.events.utill.PreferencesManager

class AdapterComments(
    var listener: AdapterCommentOnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var commentsList: MutableList<CommentsList> = arrayListOf()

    private var isLoadingAdded = false

    fun addComments(comment: ArrayList<CommentsList>) {
        commentsList.addAll(comment)
        notifyItemInserted(commentsList.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addComment(comment: CommentsList) {
        commentsList.add(0, comment)
    }

    fun addLoadingFooter(show: Boolean) {
        isLoadingAdded = show
    }

    companion object {
        const val ITEM = 0
        const val LOADING = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == commentsList.size - 1 && isLoadingAdded) LOADING
        else ITEM
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            LOADING -> {
                val viewLoading = ItemLoadingViewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = LoadingViewHolder(viewLoading)
            }
            ITEM -> {
                val binding = ItemListCommentsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                viewHolder = ItemViewHolder(binding)
            }
        }
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            LOADING -> {
                val loadingViewHolder = holder as LoadingViewHolder
                loadingViewHolder.binding.progressViewComment.visibility = View.VISIBLE
            }
            ITEM -> {
                val comments = commentsList[position]
                val viewHolderWishlist = holder as ItemViewHolder
                viewHolderWishlist.bindView(comments)
            }
        }
    }

    inner class LoadingViewHolder(val binding: ItemLoadingViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class ItemViewHolder(val binding: ItemListCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var preferencesManager = PreferencesManager(itemView.context)

        @SuppressLint("SetTextI18n")
        fun bindView(comment: CommentsList) {
            with(binding) {
                if (comment.username == preferencesManager.getString(Constants.USERNAME))
                    usernameComments.text = "Вы"
                else usernameComments.text = "@${comment.username}"
                textViewComments.text = comment.text_comments

                usernameComments.setOnClickListener {
                    if (!preferencesManager.getBoolean(Constants.SIGN_UP)) {
                        listener.onClickUser(comment.user_id)
                    } else {
                        if (comment.user_id != preferencesManager.getString(Constants.USER_ID)
                                .toInt()
                        ) {
                            listener.onClickUser(comment.user_id)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = commentsList.size

    interface AdapterCommentOnClickListener {
        fun onClickUser(user_id: Int)
    }
}