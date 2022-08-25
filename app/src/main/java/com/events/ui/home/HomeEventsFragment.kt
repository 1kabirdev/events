package com.events.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.events.App
import com.events.R
import com.events.ui.home.adapter.AdapterEventList
import com.events.databinding.FragmentHomeEventsBinding
import com.events.model.home.InfoEvents
import com.events.model.home.ListEvents
import com.events.model.home.ResponseHomeEvents
import com.events.model.home.ThemeEvent
import com.events.model.theme_event.ResponseThemeEventHome
import com.events.model.theme_event.ThemeEventHome
import com.events.ui.event.EventsActivity
import com.events.ui.event.MyEventsActivity
import com.events.ui.theme_list.ThemeListActivity
import com.events.utill.ConstantAgrs
import com.events.utill.Constants
import com.events.utill.LinearEventEndlessScrollEventListener
import com.events.utill.PreferencesManager

class HomeEventsFragment : Fragment(), ListEventController.View, AdapterEventList.OnClickListener {

    private lateinit var binding: FragmentHomeEventsBinding
    private lateinit var presenter: ListEventPresenter
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var adapterEventList: AdapterEventList
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var endlessScrollEventListener: LinearEventEndlessScrollEventListener
    private var errorFailed = false
    private var isLoading = false
    private var isLastPage = false
    private val PAGE_START = 1
    private var currentPage: Int = PAGE_START

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preferencesManager = PreferencesManager(requireContext())
        presenter = ListEventPresenter()
        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeEventsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.responseLoadDataAll(PAGE_START, "Все")
        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewList.layoutManager = layoutManager
        setEndlessScrollEventListener()
        binding.recyclerViewList.addOnScrollListener(endlessScrollEventListener)
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
                            presenter.responseEventsPage(currentPage, "Все")
                        }
                    }
                }
            }
    }

    override fun getLoadEventPage(info: InfoEvents, eventsList: ArrayList<ListEvents>) {
        currentPage = info.next_page
        adapterEventList.addEventList(eventsList)
        isLoading = false
        adapterEventList.removeLoadingFooter()
        if (info.next_page != 0)
            if (currentPage != info.count_page) adapterEventList.addLoadingFooter() else isLastPage =
                true
    }

    override fun getHomeView(
        responseHomeEvents: ResponseHomeEvents,
        theme: ResponseThemeEventHome
    ) {
        currentPage = responseHomeEvents.infoEvents.next_page
        adapterEventList = AdapterEventList(responseHomeEvents.response, this)
        adapterEventList.addThemeEvent(theme.theme_event)
        binding.recyclerViewList.adapter = adapterEventList
        if (responseHomeEvents.infoEvents.next_page != 0)
            if (currentPage <= responseHomeEvents.infoEvents.next_page) adapterEventList.addLoadingFooter() else isLastPage =
                true
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            binding.constraintConnection.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.constraintConnection.visibility = View.GONE
        }
    }

    override fun noConnection() {
        binding.constraintConnection.visibility = View.VISIBLE
        binding.btnReplyEvent.setOnClickListener {
            presenter.responseEventsPage(
                PAGE_START,
                "Все"
            )
        }
    }

    override fun noConnectionPage() {
        errorFailed = true
        adapterEventList.removeLoadingFooter()
        adapterEventList.showRetry(true)
    }

    override fun onClickEvent(event_id: Int, user_id: Int) {
        val intent = Intent(requireContext(), EventsActivity::class.java)
        intent.putExtra("EVENTS_ID", event_id.toString())
        intent.putExtra("USER_ID", user_id.toString())
        startActivity(intent)
    }

    override fun onClickMyEvent(event_id: Int) {
        val intent = Intent(requireContext(), MyEventsActivity::class.java)
        intent.putExtra("EVENTS_ID", event_id.toString())
        startActivity(intent)
    }

    override fun OnClickReply() {
        errorFailed = false
        adapterEventList.showRetry(false)
        adapterEventList.addLoadingFooter()
        presenter.responseEventsPage(
            currentPage,
            "Все"
        )
    }

    override fun onClickTheme(icons: String, name: String) {
        val intent = Intent(requireContext(), ThemeListActivity::class.java)
        intent.putExtra("NAME", name)
        intent.putExtra("ICONS", icons)
        startActivity(intent)
    }

}