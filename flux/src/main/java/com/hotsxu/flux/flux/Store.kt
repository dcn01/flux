package com.hotsxu.flux.flux

import com.hotsxu.flux.rxbus.RxBus


/**
 * Created by hotsxu on 2017/12/25.
 *
 * View Status Store
 */
abstract class Store {

    fun register(view: Any?) {
        RxBus.register(view)
    }

    fun unregister(view: Any?) {
        RxBus.unregister(view)
    }

    fun emitStoreChange(event: String) {
        RxBus.post(event)
    }

    abstract fun onAction(action: Action)
}