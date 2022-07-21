package com.events.ui.search

import com.events.data.DataManager
import com.events.model.search.ResponseSearch
import com.events.mvp.BasePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenterImpl(private var dataManager: DataManager) :
    BasePresenter<SearchContract.View>(), SearchContract.Presenter {

    private lateinit var call: Call<ResponseSearch>

    override fun responseSearch(name: String) {
        mvpView?.let { view ->
            view.progress(true)
            call = dataManager.searchEventList(name)
            call.enqueue(object : Callback<ResponseSearch> {
                override fun onResponse(
                    call: Call<ResponseSearch>,
                    response: Response<ResponseSearch>
                ) {
                    if (response.isSuccessful) {
                        view.progress(false)
                        response.body()?.let { data ->
                            view.onSearchEvent(data.response)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseSearch>, t: Throwable) {
                    view.progress(false)
                    view.noConnection()
                }
            })
        }
    }
}