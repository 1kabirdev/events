package com.events.utill

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LinearEventEndlessScrollEventListener(
    private val mLinearLayoutManager: LinearLayoutManager,
) :
    RecyclerView.OnScrollListener() {

    private val visibleThreshold = 10
    private var previousTotalItemCount = 0
    private var loading = true
    private var totalItemCount = 0
    private var lastVisibleItemPosition = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        totalItemCount = mLinearLayoutManager.itemCount
        lastVisibleItemPosition = mLinearLayoutManager.findLastVisibleItemPosition()

        if (totalItemCount < previousTotalItemCount) {
            previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) loading = true
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            onLoadMore(recyclerView)
            loading = true
        }
    }

    abstract fun onLoadMore(recyclerView: RecyclerView?)
}