package com.events.ui.organizer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.events.App
import com.events.adapter.AdapterEventList
import com.events.adapter.AdapterEventListOrganizer
import com.events.databinding.ActivityUserBinding
import com.events.model.list_events.ListEvents
import com.squareup.picasso.Picasso

class OrganizerActivity : AppCompatActivity(), OrganizerController.View {

    private lateinit var binding: ActivityUserBinding
    private lateinit var presenter: OrganizerPresenter
    private lateinit var adapterEventList: AdapterEventListOrganizer
    private lateinit var userId: String
    private var limit: String = "10"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val arguments = intent.extras
        userId = arguments?.get("USER_ID")?.toString().toString()
        presenter = OrganizerPresenter((applicationContext as App).dataManager)
        presenter.attachView(this)
        presenter.responseOrganizer(userId)
        presenter.responseEventOrganizer(userId, limit)
        onClickListener()
    }

    private fun onClickListener() {
        binding.btnBackUser.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun loadDataUserName(username: String) {
        binding.usernameOrganizer.text = "@$username"
    }

    override fun loadDataLastName(lastName: String) {
        binding.lastNameOrganizer.text = lastName
    }

    override fun loadDataAbout(about: String) {
        binding.aboutOrganizer.text = about
    }

    override fun loadDataAvatar(avatar: String) {
        Picasso.get().load(avatar).into(binding.avatarOrganizer)
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadEventsOrganizer(eventsList: ArrayList<ListEvents>) {
        adapterEventList = AdapterEventListOrganizer(eventsList)
        if (eventsList.size != 0) {
            binding.titleEventsOrganizer.text = "Мероприятия (${eventsList.size})"
            binding.recyclerViewEventsOrganizer.adapter = adapterEventList
        } else {
            binding.constraintNotEvents.visibility = View.VISIBLE
            binding.constraintRecyclerView.visibility = View.GONE
        }
    }

    override fun showProgressBarEvent() {
        binding.progressBarEventOrganizer.visibility = View.VISIBLE
    }

    override fun hideProgressBarEvent() {
        binding.progressBarEventOrganizer.visibility = View.GONE
    }

    override fun showProgressBar() {
        binding.nestedScrollViewOrganizer.visibility = View.GONE
        binding.progressBarUser.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.nestedScrollViewOrganizer.visibility = View.VISIBLE
        binding.progressBarUser.visibility = View.GONE
    }

    override fun noConnection() {
        binding.progressBarUser.visibility = View.GONE
        binding.noConnectionViewUser.visibility = View.VISIBLE
    }
}