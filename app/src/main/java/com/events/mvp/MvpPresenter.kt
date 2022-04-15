package com.events.mvp


interface MvpPresenter<V : MvpView?> {
    fun attachView(view: V)
    fun detachView()
}




