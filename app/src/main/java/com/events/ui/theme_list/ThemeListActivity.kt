package com.events.ui.theme_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.events.App
import com.events.databinding.ActivityThemeListBinding
import com.events.model.theme_event.InfoEvents
import com.events.model.theme_event.ListEvents
import com.events.model.theme_event.Subscribe
import com.events.ui.comments.CommentsActivity
import com.events.ui.event.EventsActivity
import com.events.ui.event.MyEventsActivity
import com.events.ui.theme_list.adapter.AdapterThemeListEvent
import com.events.utill.ConstantAgrs
import com.events.utill.Constants
import com.events.utill.LinearEventEndlessScrollEventListener
import com.events.utill.PreferencesManager
import com.google.android.material.snackbar.Snackbar

class ThemeListActivity : AppCompatActivity(), ThemeListEventContract.View,
    AdapterThemeListEvent.OnClickListener {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var binding: ActivityThemeListBinding
    private lateinit var presenter: ThemeListEventPresenter
    private lateinit var endlessScrollEventListener: LinearEventEndlessScrollEventListener
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapterThemeListEvent: AdapterThemeListEvent

    private var name: String = ""
    private var icons: String = ""

    private var errorFailed = false
    private var isLoading = false
    private var isLastPage = false
    private val PAGE_START = 1
    private var currentPage: Int = PAGE_START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferencesManager = PreferencesManager(this)

        val args = intent.extras
        name = args?.get(ConstantAgrs.NAME)?.toString().toString()
        icons = args?.get(ConstantAgrs.ICONS)?.toString().toString()

        presenter = ThemeListEventPresenter((applicationContext as App).dataManager)
        presenter.attachView(this)
        presenter.responseThemeEvent(name, PAGE_START)

        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewThemeList.layoutManager = layoutManager
        setEndlessScrollEventListener()
        binding.recyclerViewThemeList.addOnScrollListener(endlessScrollEventListener)

        binding.textViewTheme.text = "#$name"

        binding.btnBackThematics.setOnClickListener { finish() }
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun setEndlessScrollEventListener() {
        endlessScrollEventListener =
            object : LinearEventEndlessScrollEventListener(
                layoutManager
            ) {
                override fun onLoadMore(recyclerView: RecyclerView?) {
                    recyclerView.apply {
                        isLoading = true
                        if (currentPage != 0) {
                            presenter.responseThemeEventPage(name, currentPage)
                        }
                    }
                }
            }
    }

    override fun loadEventTheme(info: InfoEvents, listEvents: ArrayList<ListEvents>) {
        currentPage = info.next_page
        adapterThemeListEvent =
            AdapterThemeListEvent(this, listEvents, name, icons, info.count_event.toString())
        binding.recyclerViewThemeList.adapter = adapterThemeListEvent
        if (info.next_page != 0)
            if (currentPage != info.count_page) adapterThemeListEvent.addLoadingFooter() else isLastPage =
                true
    }

    override fun loadEventThemePage(info: InfoEvents, listEvents: ArrayList<ListEvents>) {
        currentPage = info.next_page
        adapterThemeListEvent.addEventList(listEvents)
        isLoading = false
        adapterThemeListEvent.removeLoadingFooter()
        if (info.next_page != 0)
            if (currentPage <= info.count_page) adapterThemeListEvent.addLoadingFooter() else isLastPage =
                true
    }

    override fun progress(show: Boolean) {
        if (show) {
            binding.constraintConnection.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.constraintConnection.visibility = View.GONE
        }
    }

    override fun error() {
        binding.constraintConnection.visibility = View.VISIBLE
        binding.btnReplyEvent.setOnClickListener {
            presenter.responseThemeEvent(name, PAGE_START)
        }
    }

    override fun errorPage() {
        errorFailed = true
        adapterThemeListEvent.removeLoadingFooter()
        adapterThemeListEvent.showRetry(true)
    }

    override fun subscribe(subscribe: Subscribe) {
        if (subscribe.status == "add") Toast.makeText(
            this,
            "Вы подписались на $name",
            Toast.LENGTH_SHORT
        ).show()
        else Toast.makeText(this, "Вы отписались от $name", Toast.LENGTH_SHORT).show()

    }

    override fun onClickEvent(event_id: Int, user_id: Int) {
        val intent = Intent(this, EventsActivity::class.java)
        intent.putExtra("EVENTS_ID", event_id.toString())
        intent.putExtra("USER_ID", user_id.toString())
        startActivity(intent)
    }

    override fun onClickMyEvent(event_id: Int) {
        val intent = Intent(this, MyEventsActivity::class.java)
        intent.putExtra("EVENTS_ID", event_id.toString())
        startActivity(intent)
    }

    override fun OnClickReply() {
        errorFailed = false
        adapterThemeListEvent.showRetry(false)
        adapterThemeListEvent.addLoadingFooter()
        presenter.responseThemeEventPage(name, currentPage)
    }

    override fun onClickEventDiscuss(
        event_id: Int,
        event_image: String,
        event_name: String,
        event_theme: String,
        event_date: String,
        event_time: String
    ) {
        val intent = Intent(this, CommentsActivity::class.java)
        intent.putExtra("EVENT_ID", event_id.toString())
        intent.putExtra("EVENT_IMAGE", event_image)
        intent.putExtra("EVENT_NAME", event_name)
        intent.putExtra("EVENT_THEME", event_theme)
        intent.putExtra("EVENT_DATE", "$event_date в $event_time")
        startActivity(intent)
    }

    override fun onClickSubscribe(name: String) {
        presenter.responseSubscribe(preferencesManager.getString(Constants.USER_ID).toInt(), name)
    }
}