package com.events.ui.event

import android.annotation.SuppressLint
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import com.events.App
import com.events.R
import com.events.databinding.ActivityMyEventsBinding
import com.events.model.delete_event.ResponseDeleteEvent
import com.events.model.events.Events
import com.events.model.events.User
import com.events.utill.SharedPreferences
import com.squareup.picasso.Picasso

class MyEventsActivity : AppCompatActivity(), EventsController.View, DeleteEventController.View {

    private lateinit var dialogProgress: ProgressDialog
    private lateinit var binding: ActivityMyEventsBinding
    private lateinit var deletePresenter: DeleteEventPresenter
    private lateinit var presenter: EventsPresenter
    private lateinit var eventId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arguments = intent.extras
        eventId = arguments?.get("EVENTS_ID")?.toString().toString()
        presenter = EventsPresenter((applicationContext as App).dataManager)
        presenter.attachView(this)
        deletePresenter = DeleteEventPresenter((applicationContext as App).dataManager)
        deletePresenter.attachView(this)
        onClickListener()
        presenter.responseLoadEvents(SharedPreferences.loadIdUser(this).toString(), eventId)
    }

    private fun onClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDeleteEvent.setOnClickListener {
            deletePresenter.responseDelete(eventId, SharedPreferences.loadIdUser(this).toString())
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadData(events: Events, user: User) {
        Picasso.get().load(events.getImageE()).into(binding.expandedImage)
        binding.nameEvents.text = events.getNameE()
        binding.textDateAndTimeEventView.text = "${events.getDataE()} в ${events.getTimeE()}"
        binding.textAddressEventView.text = events.getCityE()
        binding.textThemeEventView.text = events.getThemeE()
        binding.textDescEventView.text = events.getDescE()
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadCost(cost: String) {
        binding.textCostEventView.text = "$cost р"
    }

    override fun getLoadCostNot() {
        binding.textCostEventView.text = "Бесплатно"
    }

    override fun showProgressBar() {
        binding.nested.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.nested.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
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