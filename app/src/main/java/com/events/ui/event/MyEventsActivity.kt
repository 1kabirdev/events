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
import com.events.ui.comments.CommentsActivity
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import com.squareup.picasso.Picasso

class MyEventsActivity : AppCompatActivity(), EventsController.View, DeleteEventController.View {

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
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDeleteEvent.setOnClickListener {
            deletePresenter.responseDelete(eventId, preferencesManager.getString(Constants.USER_ID))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadData(events: Events, user: User) {
        with(binding) {
            Glide.with(this@MyEventsActivity).load(events.getImageE()).into(imageEventsView)
            nameEvents.text = events.getNameE()
            textDateAndTimeEventView.text = "${events.getDataE()} ?? ${events.getTimeE()}"
            textAddressEventView.text = events.getCityE()
            textThemeEventView.text = events.getThemeE()
            textDescEventView.text = events.getDescE()

            btnDiscussEvents.setOnClickListener {
                val intent = Intent(this@MyEventsActivity, CommentsActivity::class.java)
                intent.putExtra("EVENT_ID", eventId)
                intent.putExtra("EVENT_IMAGE", events.getImageE())
                intent.putExtra("EVENT_NAME", events.getNameE())
                intent.putExtra("EVENT_THEME", events.getThemeE())
                intent.putExtra("EVENT_DATE", events.getDataE() + events.getTimeE())
                startActivity(intent)
            }
        }
    }

    override fun showProgressBar(show: Boolean) {
        if (show) {
            binding.nested.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.nested.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }

    }

    override fun noConnection() {
        binding.nested.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    override fun loadDeleteEvent(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun showProgressDelete() {
        dialogProgress = ProgressDialog.show(this, "??????????????", "?????????????????? ????????????????????...", false)
    }

    override fun hideProgressDelete() {
        dialogProgress.dismiss()
    }

    override fun noConnectionDelete() {
        dialogProgress.dismiss()
        Toast.makeText(this, "?????????????????? ?????????????????????? ??????????????????.", Toast.LENGTH_SHORT).show()
    }
}