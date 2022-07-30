package com.events.mvp

class BaseContract {
    interface Presenter<in T> {
        fun subscribe()
        fun unsubscribe()
        fun attachView(view: T)
    }

    interface View {}
}