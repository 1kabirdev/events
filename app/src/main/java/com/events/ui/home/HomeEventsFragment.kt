package com.events.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.events.App
import com.events.MainActivity
import com.events.ui.home.adapter.AdapterEventList
import com.events.databinding.FragmentHomeEventsBinding
import com.events.model.list_events.InfoEvents
import com.events.model.list_events.ListEvents
import com.events.ui.event.EventsActivity
import com.events.ui.event.MyEventsActivity
import com.events.utill.LinearEndlessScrollEventListener
import com.events.utill.LinearEventEndlessScrollEventListener
import com.events.utill.PreferencesManager

class HomeEventsFragment : Fragment(), ListEventController.View, AdapterEventList.OnClickListener {

    private lateinit var binding: FragmentHomeEventsBinding
    private lateinit var presenter: ListEventPresenter
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var adapterEventList: AdapterEventList
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var endlessScrollEventListener: LinearEventEndlessScrollEventListener
    private var isLoading = false
    private var isLastPage = false
    private val PAGE_START = 1
    private var currentPage: Int = PAGE_START

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeEventsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())

        presenter = ListEventPresenter((requireContext().applicationContext as App).dataManager)
        presenter.attachView(this)
        presenter.responseEvents(PAGE_START)

        adapterEventList = AdapterEventList(this)

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
                    isLoading = true
                    if (currentPage != 0) {
                        presenter.responseEventsPage(currentPage)
                    }
                }
            }
    }

    override fun getLoadEvent(info: InfoEvents, eventsList: ArrayList<ListEvents>) {
        currentPage = info.next_page
        adapterEventList.addComments(eventsList)
        binding.recyclerViewList.adapter = adapterEventList
        if (info.next_page != 0) adapterEventList.addLoadingFooter(true) else isLastPage =
            true
    }

    override fun getLoadEventPage(info: InfoEvents, eventsList: ArrayList<ListEvents>) {
        currentPage = info.next_page
        adapterEventList.addComments(eventsList)
        isLoading = false
        adapterEventList.addLoadingFooter(false)
        if (info.next_page != 0) adapterEventList.addLoadingFooter(true) else isLastPage =
            true
    }

    override fun showProgress(show: Boolean) {
        if (show) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    override fun noConnection() {
        binding.notConnectionView.visibility = View.VISIBLE
        binding.notConnectionView.text = "Проверьте подключение интернета."
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

}