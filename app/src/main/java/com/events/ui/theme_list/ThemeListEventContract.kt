package com.events.ui.theme_list

import com.events.model.theme_event.InfoEvents
import com.events.model.theme_event.ListEvents
import com.events.mvp.MvpView

interface ThemeListEventContract {
    interface View : MvpView {
        fun loadEventTheme(info:InfoEvents, listEvents: ArrayList<ListEvents>)
        fun loadEventThemePage(info:InfoEvents, listEvents: ArrayList<ListEvents>)
        fun progress(show: Boolean)
        fun error()
        fun errorPage()
    }

    interface Presenter : MvpView {
        fun responseThemeEvent(theme: String, page: Int)
        fun responseThemeEventPage(theme: String, page: Int)
    }
}