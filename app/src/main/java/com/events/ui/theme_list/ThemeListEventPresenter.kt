package com.events.ui.theme_list

import com.events.data.DataManager
import com.events.model.theme_event.ResponseThemeEventList
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThemeListEventPresenter(
    private var dataManager: DataManager
) : BasePresenter<ThemeListEventContract.View>(), ThemeListEventContract.Presenter {

    private lateinit var call: Call<ResponseThemeEventList>

    override fun responseThemeEvent(theme: String, page: Int) {
        mvpView?.let { view ->
            view.progress(true)
            call = dataManager.themeListEvent(theme, page)
            call.enqueue(object : Callback<ResponseThemeEventList> {
                override fun onResponse(
                    call: Call<ResponseThemeEventList>,
                    response: Response<ResponseThemeEventList>
                ) {
                    if (response.isSuccessful) {
                        view.progress(false)
                        response.body()?.let { data ->
                            view.loadEventTheme(data.infoEvents, data.response)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseThemeEventList>, t: Throwable) {
                    view.progress(false)
                    view.error()
                }
            })
        }
    }

    override fun responseThemeEventPage(theme: String, page: Int) {
        mvpView?.let { view ->
            call = dataManager.themeListEvent(theme, page)
            call.enqueue(object : Callback<ResponseThemeEventList> {
                override fun onResponse(
                    call: Call<ResponseThemeEventList>,
                    response: Response<ResponseThemeEventList>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { data ->
                            view.loadEventThemePage(data.infoEvents, data.response)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseThemeEventList>, t: Throwable) {
                    view.errorPage()
                }
            })
        }
    }

    override fun responseSubscribe(user_id: Int, name_theme: String) {

    }
}