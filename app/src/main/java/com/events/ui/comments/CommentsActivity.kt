package com.events.ui.comments

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.events.App
import com.events.databinding.ActivityCommentsBinding
import com.events.model.comments.AddComment
import com.events.model.comments.CommentsList
import com.events.model.comments.Info
import com.events.ui.comments.adapter.AdapterComments
import com.events.ui.comments.send_commentImpl.SendCommentContract
import com.events.ui.comments.send_commentImpl.SendCommentPresenter
import com.events.ui.login.LoginPresenter
import com.events.utill.Constants
import com.events.utill.LinearEndlessScrollEventListener
import com.events.utill.PreferencesManager
import java.util.Date

class CommentsActivity : AppCompatActivity(), CommentsContract.View, SendCommentContract.View {

    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var endlessScrollEventListener: LinearEndlessScrollEventListener
    private lateinit var presenter: CommentsPresenter
    private lateinit var sendPresenter: SendCommentPresenter
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var binding: ActivityCommentsBinding
    private lateinit var event_id: String
    private var adapterComments = AdapterComments()
    private var countComments: Int = 0
    private var isLoading = false
    private var isLastPage = false
    private var countPage = 0
    private val PAGE_START = 1
    private var currentPage: Int = PAGE_START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesManager = PreferencesManager(this)

        val args = intent.extras
        event_id = args?.get("EVENT_ID").toString()

        sendPresenter = SendCommentPresenter((applicationContext as App).dataManager)
        sendPresenter.attachView(this)

        presenter = CommentsPresenter((applicationContext as App).dataManager)
        presenter.attachView(this)
        presenter.responseLoadComments(event_id.toInt(), PAGE_START)


        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewComments.layoutManager = layoutManager
        setEndlessScrollEventListener()
        binding.recyclerViewComments.addOnScrollListener(endlessScrollEventListener)

        onClickListener()
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun setEndlessScrollEventListener() {
        endlessScrollEventListener =
            object : LinearEndlessScrollEventListener(
                layoutManager
            ) {
                override fun onLoadMore(recyclerView: RecyclerView?) {
                    if (countPage != null) {
                        isLoading = true
                        currentPage += 1
                        presenter.responseLoadCommentsPage(event_id.toInt(), currentPage)
                    }
                }
            }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun onClickListener() {
        with(binding) {

            // click close activity
            btnClickBack.setOnClickListener { finish() }

            // click send comment
            btnSendMessage.setOnClickListener {
                if (editTextComment.text.toString().isNotEmpty()) {
                    countComments += 1
                    binding.countComment.text = countComments.toString()
                    sendPresenter.responseSendComment(
                        preferencesManager.getString(Constants.USER_ID).toInt(),
                        event_id.toInt(),
                        preferencesManager.getString(Constants.USERNAME),
                        editTextComment.text.toString()
                    )
                    val comments = CommentsList(
                        0,
                        event_id.toInt(),
                        preferencesManager.getString(Constants.USER_ID).toInt(),
                        preferencesManager.getString(Constants.USERNAME),
                        editTextComment.text.toString(),
                        Date().toString()
                    )
                    adapterComments.addComment(comments)
                    adapterComments.notifyDataSetChanged()
                    editTextComment.text = null
                } else {
                    Toast.makeText(
                        this@CommentsActivity,
                        "Сообщение не должно быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    override fun loadComments(info: Info, commentsList: ArrayList<CommentsList>) {
        if (info.count_comments != 0) {
            countComments = info.count_comments
            binding.countComment.text = countComments.toString()
        }
        countPage = info.count_page
        adapterComments.addComments(commentsList)
        binding.recyclerViewComments.adapter = adapterComments
        if (currentPage == info.count_page) adapterComments.addLoadingFooter(true) else isLastPage =
            true

    }

    override fun loadCommentPage(info: Info, commentsList: ArrayList<CommentsList>) {
        if (info.count_comments != 0) {
            countComments = info.count_comments
            binding.countComment.text = countComments.toString()
        }
        adapterComments.addComments(commentsList)
        isLoading = false
        adapterComments.addLoadingFooter(false)
        if (currentPage == info.count_page) adapterComments.addLoadingFooter(true) else isLastPage =
            true
    }

    override fun progress(show: Boolean) {
        if (show) binding.progressBarComments.visibility = View.VISIBLE
        else binding.progressBarComments.visibility = View.GONE
    }

    override fun errorConnection() {
        Toast.makeText(this, "Проверьте подключение к Интеренту", Toast.LENGTH_SHORT).show()
    }


    override fun sendComment(addComment: AddComment) = Unit


    override fun onStart() {
        if (preferencesManager.getBoolean(Constants.SIGN_UP)) {
            binding.linearNotAuthorized.visibility = View.GONE
            binding.linearCommentView.visibility = View.VISIBLE
        } else {
            binding.linearCommentView.visibility = View.GONE
            binding.linearNotAuthorized.visibility = View.VISIBLE
        }
        super.onStart()
    }

    override fun onDestroy() {
        sendPresenter.detachView()
        presenter.detachView()
        super.onDestroy()
    }
}