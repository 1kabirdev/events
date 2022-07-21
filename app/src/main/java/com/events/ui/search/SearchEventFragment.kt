package com.events.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.events.App
import com.events.R
import com.events.databinding.FragmentSearchEventBinding
import com.events.model.search.Event
import com.events.utill.PreferencesManager

class SearchEventFragment : Fragment(), SearchContract.View {

    private lateinit var binding: FragmentSearchEventBinding
    private lateinit var presenterImpl: SearchPresenterImpl
    private lateinit var preferencesManager: PreferencesManager

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

    }

    override fun onSearchEvent(event: ArrayList<Event>) {

    }

    override fun progress(show: Boolean) {

    }

    override fun noConnection() {

    }

}