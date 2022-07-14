package com.events.ui.comments

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.events.App
import com.events.databinding.ActivityCommentsBinding
import com.events.model.comments.AddComment
import com.events.model.comments.CommentsList
import com.events.model.comments.Info
import com.events.ui.comments.adapter.AdapterComments
import com.events.ui.login.LoginPresenter
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import java.util.Date

class CommentsActivity : AppCompatActivity(), CommentsContract.View {

    private lateinit var presenter: CommentsPresenter
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var binding: ActivityCommentsBinding
    private lateinit var event_id: String
    private var adapterComments = AdapterComments()
    private var countComments: Int = 0
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesManager = PreferencesManager(this)

        val args = intent.extras
        event_id = args?.get("EVENT_ID").toString()

        presenter = CommentsPresenter((applicationContext as App).dataManager)
        presenter.attachView(this)
        presenter.responseLoadComments(event_id.toInt(), page)

        binding.recyclerViewComments.adapter = adapterComments

        onClickListener()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onClickListener() {
        with(binding) {
            btnClickBack.setOnClickListener { finish() }

            btnSendMessage.setOnClickListener {
                if (editTextComment.text.toString().isNotEmpty()) {
                    countComments += 1
                    binding.countComment.text = countComments.toString()
                    presenter.responseSendComment(
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

    override fun loadComments(info: Info, commentsList: ArrayList<CommentsList>) {
        if (info.count_comments != 0) {
            countComments = info.count_comments
            binding.countComment.text = countComments.toString()
        }
        adapterComments.addComments(commentsList)
    }

    override fun progress(show: Boolean) {

    }

    override fun errorConnection() {
        Toast.makeText(this, "Проверьте подключение к Интеренту", Toast.LENGTH_SHORT).show()
    }

    override fun sendComment(addComment: AddComment) {

    }
}