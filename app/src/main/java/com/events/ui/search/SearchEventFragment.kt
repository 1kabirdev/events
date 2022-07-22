package com.events.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.events.App
import com.events.databinding.FragmentSearchEventBinding
import com.events.model.search.Event
import com.events.ui.event.EventsActivity
import com.events.ui.event.MyEventsActivity
import com.events.ui.search.adapter.AdapterSearchEvent
import com.events.utill.Constants
import com.events.utill.PreferencesManager
import java.util.*


class SearchEventFragment : Fragment(), SearchContract.View, AdapterSearchEvent.OnClickListener {

    private lateinit var binding: FragmentSearchEventBinding
    private lateinit var presenterImpl: SearchPresenterImpl
    private lateinit var preferencesManager: PreferencesManager

    private lateinit var adapterSearchEvent: AdapterSearchEvent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesManager = PreferencesManager(requireContext())

        presenterImpl = SearchPresenterImpl((view.context.applicationContext as App).dataManager)
        presenterImpl.attachView(this)

        with(binding) {
            toolbarSearch.editTextSearchEventName.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) = Unit

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) =
                    Unit

                override fun afterTextChanged(s: Editable) {
                    if (s.trim().isNotEmpty())
                        presenterImpl.responseSearch(s.toString())
                    else presenterImpl.responseSearch("")
                }
            })
        }
    }

    override fun onSearchEvent(event: ArrayList<Event>) {
        adapterSearchEvent = AdapterSearchEvent(event, this)
        binding.recyclerViewEventSearch.adapter = adapterSearchEvent
    }

    override fun progress(show: Boolean) {

    }

    override fun noConnection() {

    }

    override fun onClickEvent(id: Int, user_id: Int) {
        if (preferencesManager.getBoolean(Constants.SIGN_UP)) {
            if (user_id == preferencesManager.getString(Constants.USER_ID).toInt()) {
                startActivity(
                    Intent(
                        requireContext(),
                        MyEventsActivity::class.java
                    ).putExtra("EVENTS_ID", id.toString())
                )
            } else {
                startActivity(
                    Intent(
                        requireContext(), EventsActivity::class.java
                    ).putExtra(
                        "EVENTS_ID", id.toString()
                    ).putExtra("USER_ID", user_id.toString())
                )
            }
        } else {
            startActivity(
                Intent(
                    requireContext(), EventsActivity::class.java
                ).putExtra(
                    "EVENTS_ID", id.toString()
                ).putExtra("USER_ID", user_id.toString())
            )
        }
    }
}