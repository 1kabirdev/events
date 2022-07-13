package com.events.ui.comments

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.events.databinding.ActivityCommentsBinding
import com.events.model.comments.CommentsList
import com.events.utill.Constants
import com.events.utill.PreferencesManager

class CommentsActivity : AppCompatActivity() {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var binding: ActivityCommentsBinding
    private lateinit var event_id: String
    private var adapterComments = AdapterComments()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesManager = PreferencesManager(this)

        val args = intent.extras
        event_id = args?.get("EVENT_ID").toString()

        binding.recyclerViewComments.adapter = adapterComments
        onClickListener()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onClickListener() {
        with(binding) {
            btnClickBack.setOnClickListener { finish() }

            btnSendMessage.setOnClickListener {
                if (editTextComment.text.toString().isNotEmpty()) {
                    val comments = CommentsList(
                        1,
                        2,
                        3,
                        preferencesManager.getString(Constants.USERNAME),
                        editTextComment.text.toString()
                    )
                    adapterComments.addComment(comments)
                    editTextComment.text = null
                    recyclerViewComments.scrollToPosition(adapterComments.itemCount - 1)
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
}