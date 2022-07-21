package com.events.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.events.App
import com.events.databinding.FragmentSearchEventBinding
import com.events.model.search.Event
import com.events.ui.search.adapter.AdapterSearchEvent
import com.events.utill.PreferencesManager
import java.util.*


class SearchEventFragment : Fragment(), SearchContract.View, AdapterSearchEvent.OnClickListener {

    private lateinit var binding: FragmentSearchEventBinding
    private lateinit var presenterImpl: SearchPresenterImpl
    private lateinit var preferencesManager: PreferencesManager

    private var adapterSearchEvent = AdapterSearchEvent(this)

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
                    presenterImpl.responseSearch(binding.toolbarSearch.editTextSearchEventName.text.toString())
                }
            })
        }
    }

    override fun onSearchEvent(event: ArrayList<Event>) {
        adapterSearchEvent.addEventList(event)
        binding.recyclerViewEventSearch.adapter = adapterSearchEvent
    }

    override fun progress(show: Boolean) {

    }

    override fun noConnection() {

    }

    override fun onClickEvent(id: Int, user_id: Int) {

    }

}