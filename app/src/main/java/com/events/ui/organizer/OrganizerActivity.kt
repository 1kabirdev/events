package com.events.ui.organizer

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.events.App
import com.events.databinding.ActivityUserBinding
import com.events.model.list_events.InfoPage
import com.events.model.list_events.ListEvents
import com.events.model.list_events.Organize
import com.events.ui.event.EventsActivity
import com.events.ui.organizer.adapter.AdapterEventListOrganizer
import com.events.utill.LinearEventEndlessScrollEventListener

class OrganizerActivity : AppCompatActivity(), OrganizerController.View,
    AdapterEventListOrganizer.OnClickListener {

    private lateinit var binding: ActivityUserBinding
    private lateinit var presenter: OrganizerPresenter
    private lateinit var adapterEventList: AdapterEventListOrganizer
    private lateinit var userId: String
    private lateinit var endlessScrollEventListener: LinearEventEndlessScrollEventListener
    private lateinit var layoutManager: LinearLayoutManager
    private var errorFailed = false
    private var isLoading = false
    private var isLastPage = false
    private val PAGE_START = 1
    private var currentPage: Int = PAGE_START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val arguments = intent.extras
        userId = arguments?.get("USER_ID")?.toString().toString()

        onClickListener()

        presenter = OrganizerPresenter((applicationContext as App).dataManager)
        presenter.attachView(this)
        presenter.responseEventOrganizer(userId, PAGE_START)

        layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewEventsOrganizer.layoutManager = layoutManager
        setEndlessScrollEventListener()
        binding.recyclerViewEventsOrganizer.addOnScrollListener(endlessScrollEventListener)
    }

    private fun onClickListener() {
        binding.toolbarOrganizer.btnBackUser.setOnClickListener { finish() }

        binding.btnReplyProfile.setOnClickListener {
            presenter.responseEventOrganizer(
                userId,
                PAGE_START
            )
        }
    }

    private fun setEndlessScrollEventListener() {
        endlessScrollEventListener =
            object : LinearEventEndlessScrollEventListener(
                layoutManager
            ) {
                override fun onLoadMore(recyclerView: RecyclerView?) {
                    recyclerView.apply {
                        isLoading = true
                        if (currentPage != 0) {
                            presenter.responseEventOrganizerPage(
                                userId,
                                currentPage
                            )
                        }
                    }
                }
            }
    }

    @SuppressLint("SetTextI18n")
    override fun getLoadEventsOrganizer(
        organize: Organize,
        infoPage: InfoPage,
        eventsList: ArrayList<ListEvents>
    ) {
        currentPage = infoPage.next_page
        adapterEventList = AdapterEventListOrganizer(eventsList, this@OrganizerActivity)
        adapterEventList.organizer(organize)
        adapterEventList.infoPage(infoPage.count_event)

        with(binding) {
            recyclerViewEventsOrganizer.adapter = adapterEventList
        }


        if (infoPage.next_page != 0)
            if (currentPage <= infoPage.count_page) adapterEventList.addLoadingFooter() else isLastPage =
                true
    }

    override fun getLoadEventsOrganizerPage(infoPage: InfoPage, eventsList: ArrayList<ListEvents>) {
        currentPage = infoPage.next_page
        adapterEventList.addAll(eventsList)
        isLoading = false
        adapterEventList.removeLoadingFooter()
        if (infoPage.next_page != 0)
            if (currentPage != infoPage.count_page) adapterEventList.addLoadingFooter() else isLastPage =
                true
    }

    override fun progressBar(show: Boolean) {
        if (show) {
            binding.constraintConnection.visibility = View.GONE
            binding.progressBarOrganizer.visibility = View.VISIBLE
        } else {
            binding.progressBarOrganizer.visibility = View.GONE
            binding.constraintConnection.visibility = View.GONE
        }
    }

    override fun noConnection() {
        binding.progressBarOrganizer.visibility = View.GONE
        binding.constraintConnection.visibility = View.VISIBLE
    }

    override fun noConnectionPage() {
        errorFailed = true
        adapterEventList.removeLoadingFooter()
        adapterEventList.showRetry(true)
    }

    override fun onClickEvent(id: Int) {
        val intent = Intent(this, EventsActivity::class.java)
        intent.putExtra("EVENTS_ID", id.toString())
        intent.putExtra("USER_ID", userId)
        startActivity(intent)
    }

    override fun OnClickReply() {
        errorFailed = false
        adapterEventList.showRetry(false)
        adapterEventList.addLoadingFooter()
        presenter.responseEventOrganizerPage(
            userId,
            currentPage
        )
    }
}