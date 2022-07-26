package com.events.ui.event

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.events.App
import com.events.databinding.ActivityEventsBinding
import com.events.model.events.Events
import com.events.model.events.User
import com.events.ui.comments.CommentsActivity
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
        with(binding) {
            Picasso.get().load(user.getAvatar()).into(avatarProfileView)
            textUserNameProfile.text = "@" + user.getUsername()
            textLastNameProfile.text = user.getLastName()
            Glide.with(this@EventsActivity).load(events.getImageE()).into(imageEventsView)
            nameEvents.text = events.getNameE()
            textDateAndTime.text = "${events.getDataE()} в ${events.getTimeE()}"
            textCityEvents.text = events.getCityE()
            textTheme.text = events.getThemeE()
            textDescEventView.text = events.getDescE()

            btnDiscussEvents.setOnClickListener {
                val intent = Intent(this@EventsActivity, CommentsActivity::class.java)
                intent.putExtra("EVENT_ID", eventId)
                intent.putExtra("EVENT_IMAGE", events.getImageE())
                intent.putExtra("EVENT_NAME", events.getNameE())
                intent.putExtra("EVENT_THEME", events.getThemeE())
                intent.putExtra("EVENT_DATE", events.getDataE() + events.getTimeE())
                startActivity(intent)
            }
        }
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
        binding.notConnectionView.visibility = View.VISIBLE
        binding.nested.visibility = View.GONE
        Toast.makeText(this, "Проверьте подключение интернета", Toast.LENGTH_SHORT).show()
    }
}