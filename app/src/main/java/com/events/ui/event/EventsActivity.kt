package com.events.ui.event

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.events.App
import com.events.databinding.ActivityEventsBinding
import com.events.model.events.Events
import com.events.model.events.User
import com.events.model.similar_event.SimilarList
import com.events.ui.comments.CommentsActivity
import com.events.ui.organizer.OrganizerActivity
import com.events.utill.ConstantAgrs

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
        eventId = arguments?.get(ConstantAgrs.EVENTS_ID)?.toString().toString()
        userId = arguments?.get(ConstantAgrs.USER_ID)?.toString().toString()

        presenter.responseLoadEvents(userId, eventId)

        onClickListener()
    }

    override fun getLoadData(events: Events, user: User) {
        with(binding) {
            Glide.with(this@EventsActivity).load(user.getAvatar()).into(avatarProfileView)
            textUserNameProfile.text = "@${user.getUsername()}"
            textLastNameProfile.text = user.getLastName()
            Glide.with(this@EventsActivity).load(events.getImageE()).into(imageEventsView)
            nameEvents.text = events.getNameE()
            textDateAndTime.text = "${events.getDataE()} в ${events.getTimeE()}"
            textCityEvents.text = events.getCityE()
            textTheme.text = events.getThemeE()
            textDescEventView.text = events.getDescE()

            btnDiscussEvents.setOnClickListener {
                val intent = Intent(this@EventsActivity, CommentsActivity::class.java)
                intent.putExtra(ConstantAgrs.EVENT_ID, eventId)
                intent.putExtra(ConstantAgrs.EVENT_IMAGE, events.getImageE())
                intent.putExtra(ConstantAgrs.EVENT_NAME, events.getNameE())
                intent.putExtra(ConstantAgrs.EVENT_THEME, events.getThemeE())
                intent.putExtra(ConstantAgrs.EVENT_DATE, events.getDataE() + events.getTimeE())
                startActivity(intent)
            }
        }
        presenter.responseSimilarEvents(events.getThemeE(), events.getIdE().toInt())
    }

    override fun similarEventList(similarList: ArrayList<SimilarList>) {

    }

    private fun onClickListener() {
        with(binding) {
            btnBack.setOnClickListener {
                finish()
            }
            constraintUserAccount.setOnClickListener {
                val intent = Intent(it.context, OrganizerActivity::class.java)
                intent.putExtra("USER_ID", userId)
                startActivity(intent)
            }

            btnReplyEvent.setOnClickListener {
                presenter.responseLoadEvents(userId, eventId)
            }
        }
    }

    override fun showProgressBar(show: Boolean) {
        with(binding) {
            if (show) {
                constraintConnection.visibility = View.GONE
                nested.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            } else {
                constraintConnection.visibility = View.GONE
                nested.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun noConnection() {
        with(binding) {
            constraintConnection.visibility = View.VISIBLE
            nested.visibility = View.GONE
        }
    }
}