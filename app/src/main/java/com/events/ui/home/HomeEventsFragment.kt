package com.events.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.events.App
import com.events.MainActivity
import com.events.ui.home.adapter.AdapterEventList
import com.events.databinding.FragmentHomeEventsBinding
import com.events.model.list_events.ListEvents
import com.events.utill.SharedPreferences

class HomeEventsFragment : Fragment(), ListEventController.View {

    private lateinit var binding: FragmentHomeEventsBinding
    private lateinit var adapter: AdapterEventList
    private lateinit var presenter: ListEventPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeEventsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ListEventPresenter((requireContext().applicationContext as App).dataManager)
        presenter.attachView(this)
        presenter.responseEvents(SharedPreferences.getEventType(requireContext()).toString())

        if (SharedPreferences.getEventType(requireContext())
                .toString() == "all"
        ) binding.textEvent.text = "Все мероприятия"
        else if (SharedPreferences.getEventType(requireContext()).toString() == "Махачкала")
            binding.textEvent.text = "Мероприятия в Махачкале"
    }

    private fun showDialogFilter() {
        (requireActivity() as MainActivity).createDialogFragment(
            DialogFilterFragment(this)
        )
    }

    @SuppressLint("SetTextI18n")
    fun filter(name: String) {
        if (name == "all") {
            binding.textEvent.text = "Все мероприятия"
        } else if (name == "Махачкала") {
            binding.textEvent.text = "Мероприятия в Махачкале"
        }
        presenter.responseEventsFilter(name)
    }

    override fun getLoadEvent(eventsList: ArrayList<ListEvents>) {
        adapter = AdapterEventList(eventsList)
        binding.recyclerViewList.adapter = adapter
        binding.filterEvents.setOnClickListener {
            showDialogFilter()
        }
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
    }

    override fun noConnection() {
        binding.notConnectionView.visibility = View.VISIBLE
        binding.notConnectionView.text = "Проверьте подключение интернета."
    }

}