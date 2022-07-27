package com.events.ui.event

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.events.App
import com.events.databinding.ActivityMyEventsBinding
import com.events.model.events.Events
import com.events.model.events.User
import com.events.model.similar_event.SimilarList
import com.events.ui.comments.CommentsActivity
import com.events.ui.event.similar.AdapterSimilarEvent
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import com.squareup.picasso.Picasso

class MyEventsActivity : AppCompatActivity(), EventsController.View, DeleteEventController.View,
    AdapterSimilarEvent.OnClickListener {

    private lateinit var dialogProgress: ProgressDialog
    private lateinit var binding: ActivityMyEventsBinding
    private lateinit var deletePresenter: DeleteEventPresenter
    private lateinit var presenter: EventsPresenter
    private lateinit var eventId: String
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

        val arguments = intent.extras
        eventId = arguments?.get("EVENTS_ID")?.toString().toString()
        presenter = EventsPresenter((applicationContext as App).dataManager)
        presenter.attachView(this)
        deletePresenter = DeleteEventPresenter((applicationContext as App).dataManager)
        deletePresenter.attachView(this)
        onClickListener()
        presenter.responseLoadEvents(preferencesManager.getString(Constants.USER_ID), eventId)
    }

    private fun onClickListener() {
        with(binding) {
            btnBack.setOnClickListener {
                finish()
            }

            btnDeleteEvent.setOnClickListener {
                deletePresenter.responseDelete(
                    eventId,
                    preferencesManager.getString(Constants.USER_ID)
                )
            }

            btnReplyEvent.setOnClickListener {
                presenter.responseLoadEvents(
                    preferencesManager.getString(Constants.USER_ID),
                    eventId
                )
            }
        }
    }

    override fun getLoadData(events: Events, user: User) {
        with(binding) {
            Glide.with(this@MyEventsActivity).load(events.getImageE()).into(imageEventsView)
            nameEvents.text = events.getNameE()
            textDateAndTime.text = "${events.getDataE()} в ${events.getTimeE()}"
            textCityEvents.text = events.getCityE()
            textTheme.text = events.getThemeE()
            textDescEventView.text = events.getDescE()

            btnDiscussEvents.setOnClickListener {
                val intent = Intent(this@MyEventsActivity, CommentsActivity::class.java)
                intent.putExtra("EVENT_ID", eventId)
                intent.putExtra("EVENT_IMAGE", events.getImageE())
                intent.putExtra("EVENT_NAME", events.getNameE())
                intent.putExtra("EVENT_THEME", events.getThemeE())
                intent.putExtra("EVENT_DATE", events.getDataE() + " в " +  events.getTimeE())
                startActivity(intent)
            }
        }
        presenter.responseSimilarEvents(events.getThemeE(), events.getIdE().toInt())
    }

    override fun similarEventList(similarList: ArrayList<SimilarList>) {
        val adapterSimilarEvent = AdapterSimilarEvent(this, similarList)
        binding.recyclerViewSimilarEvent.adapter = adapterSimilarEvent
        if (similarList.size == 0) binding.titleTextView.visibility = View.GONE
        else binding.titleTextView.visibility = View.VISIBLE

    }

    override fun showProgressBar(show: Boolean) {
        if (show) {
            binding.constraintConnection.visibility = View.GONE
            binding.nested.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.nested.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
            binding.constraintConnection.visibility = View.GONE
        }
    }

    override fun noConnection() {
        binding.nested.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.constraintConnection.visibility = View.VISIBLE
    }

    override fun loadDeleteEvent(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showProgressDelete() {
        dialogProgress = ProgressDialog.show(this, "Удалени", "Подождите пожалуйста...", false)
    }

    override fun hideProgressDelete() {
        dialogProgress.dismiss()
    }

    override fun noConnectionDelete() {
        dialogProgress.dismiss()
        Toast.makeText(this, "Проверьте подключение интернета.", Toast.LENGTH_SHORT).show()
    }

    override fun onClickEvent(event_id: Int, user_id: Int) {
        if (preferencesManager.getString(Constants.USER_ID).toInt() == user_id) {
            val intent = Intent(this, MyEventsActivity::class.java)
            intent.putExtra("EVENTS_ID", event_id.toString())
            startActivity(intent)
        } else {
            val intent = Intent(this, EventsActivity::class.java)
            intent.putExtra("EVENTS_ID", event_id.toString())
            intent.putExtra("USER_ID", user_id.toString())
            startActivity(intent)
        }
    }
}