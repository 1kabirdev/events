package com.events.ui.event

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
            Picasso.get().load(events.getImageE()).into(expandedImage)
            nameEvents.text = events.getNameE()
            textDateAndTimeEventView.text = "${events.getDataE()} в ${events.getTimeE()}"
            textAddressEventView.text = events.getCityE()
            textThemeEventView.text = events.getThemeE()
            textDescEventView.text = events.getDescE()

            btnDiscussEvents.setOnClickListener {
                val intent = Intent(this@MyEventsActivity, CommentsActivity::class.java)
                intent.putExtra("EVENT_ID", eventId)
                startActivity(intent)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadCost(cost: String) {
        binding.textCostEventView.text = "$cost р"
    }

    override fun getLoadCostNot() {
        binding.textCostEventView.text = "Бесплатно"
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
        dialogProgress = ProgressDialog.show(this, "Удалени", "Подождите пожалуйста...", false)
    }

    override fun hideProgressDelete() {
        dialogProgress.dismiss()
    }

    override fun noConnectionDelete() {
        dialogProgress.dismiss()
        Toast.makeText(this, "Проверьте подключение интернета.", Toast.LENGTH_SHORT).show()
    }
}