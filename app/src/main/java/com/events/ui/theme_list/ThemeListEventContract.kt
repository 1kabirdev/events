package com.events.ui.theme_list

import com.events.model.theme_event.InfoEvents
import com.events.model.theme_event.ListEvents
import com.events.model.theme_event.Subscribe
import com.events.mvp.MvpView

interface ThemeListEventContract {
    interface View : MvpView {
        fun loadEventTheme(info:InfoEvents, listEvents: ArrayList<ListEvents>)
        fun loadEventThemePage(info:InfoEvents, listEvents: ArrayList<ListEvents>)
        fun progress(show: Boolean)
        fun error()
        fun errorPage()

        fun subscribe(subscribe: Subscribe)
    }

    interface Presenter : MvpView {
        fun responseThemeEvent(theme: String, page: Int)
        fun responseThemeEventPage(theme: String, page: Int)
        fun responseSubscribe(user_id:Int,name_theme:String)
    }
}