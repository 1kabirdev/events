package com.events.ui.event

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.events.App
import com.events.databinding.ActivityEventsBinding
import com.events.model.events.Events
import com.events.model.events.User
import com.events.ui.organizer.OrganizerActivity
import com.squareup.picasso.Picasso

class EventsActivity : AppCompatActivity(), EventsController.View {
    private lateinit var binding: ActivityEventsBinding
    private lateinit var presenter: EventsPresenter
    private lateinit var eventId: String
    private lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = EventsPresenter((applicationContext as App).dataManager)
        presenter.attachView(this)
        val arguments = intent.extras
        eventId = arguments?.get("EVENTS_ID")?.toString().toString()
        userId = arguments?.get("USER_ID")?.toString().toString()
        presenter.responseLoadEvents(userId, eventId)
        onClickListener()
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadData(events: Events, user: User) {
        Picasso.get().load(user.getAvatar()).into(binding.avatarProfileView)
        binding.textUserNameProfile.text = "@" + user.getUsername()
        binding.textLastNameProfile.text = user.getLastName()
        Picasso.get().load(events.getImageE()).into(binding.imageEventsView)
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

    private fun onClickListener() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.constraintUserAccount.setOnClickListener {
            val intent = Intent(this, OrganizerActivity::class.java)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
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
        binding.notConnectionView.visibility = View.VISIBLE
        binding.nested.visibility = View.GONE
        Toast.makeText(this, "Проверьте подключение интернета", Toast.LENGTH_SHORT).show()
    }
}