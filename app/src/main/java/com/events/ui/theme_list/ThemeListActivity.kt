package com.events.ui.theme_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.events.App
import com.events.databinding.ActivityThemeListBinding
import com.events.model.theme_event.InfoEvents
import com.events.model.theme_event.ListEvents
import com.events.ui.theme_list.adapter.AdapterThemeListEvent
import com.events.utill.ConstantAgrs
import com.events.utill.LinearEventEndlessScrollEventListener
import com.events.utill.PreferencesManager

class ThemeListActivity : AppCompatActivity(), ThemeListEventContract.View ,AdapterThemeListEvent.OnClickListener{

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var binding: ActivityThemeListBinding
    private lateinit var presenter: ThemeListEventPresenter
    private lateinit var endlessScrollEventListener: LinearEventEndlessScrollEventListener

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

    }

    override fun loadEventTheme(info: InfoEvents, listEvents: ArrayList<ListEvents>) {

    }

    override fun loadEventThemePage(info: InfoEvents, listEvents: ArrayList<ListEvents>) {

    }

    override fun progress(show: Boolean) {

    }

    override fun error() {

    }

    override fun errorPage() {

    }

    override fun onClickEvent(event_id: Int, user_id: Int) {

    }

    override fun onClickMyEvent(event_id: Int) {

    }

    override fun OnClickReply() {

    }

    override fun onClickEventDiscuss(
        event_id: Int,
        event_image: String,
        event_name: String,
        event_theme: String,
        event_date: String
    ) {

    }

    override fun onClickSubscribe() {

    }
}