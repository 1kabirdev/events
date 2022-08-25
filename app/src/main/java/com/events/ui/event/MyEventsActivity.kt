package com.events.ui.event

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

class MyEventsActivity : AppCompatActivity(), EventsController.View, DeleteEventController.View,
    AdapterSimilarEvent.OnClickListener {

    private lateinit var dialogProgress: ProgressDialog
    private lateinit var binding: ActivityMyEventsBinding
    private var deletePresenter: DeleteEventPresenter
    private var presenter: EventsPresenter
    private var eventId: String = ""
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferencesManager = PreferencesManager(this)
        onClickListener()
        val arguments = intent.extras
        eventId = arguments?.get("EVENTS_ID")?.toString().toString()
        presenter.responseLoadEvents(preferencesManager.getString(Constants.USER_ID), eventId)
    }

    init {
        presenter = EventsPresenter()
        presenter.attachView(this)
        deletePresenter = DeleteEventPresenter()
        deletePresenter.attachView(this)
    }

    private fun onClickListener() = with(binding) {
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

    override fun getLoadData(events: Events, user: User) = with(binding) {
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
            intent.putExtra("EVENT_DATE", events.getDataE() + " в " + events.getTimeE())
            startActivity(intent)
        }
        presenter.responseSimilarEvents(events.getThemeE(), events.getIdE().toInt())
    }

    override fun similarEventList(similarList: ArrayList<SimilarList>) = with(binding) {
        val adapterSimilarEvent = AdapterSimilarEvent(this@MyEventsActivity, similarList)
        recyclerViewSimilarEvent.adapter = adapterSimilarEvent
        if (similarList.size == 0) titleTextView.visibility = View.GONE
        else titleTextView.visibility = View.VISIBLE
    }


    override fun showProgressBar(show: Boolean) = with(binding) {
        if (show) {
            constraintConnection.visibility = View.GONE
            nested.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        } else {
            nested.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            constraintConnection.visibility = View.GONE
        }
    }


    override fun noConnection() = with(binding) {
        nested.visibility = View.GONE
        progressBar.visibility = View.GONE
        constraintConnection.visibility = View.VISIBLE
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