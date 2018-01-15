package com.hotsxu.flux.flux

import java.util.*


/**
 * Created by hotsxu on 2017/12/25.
 *
 * 事件发布
 */
object Dispatcher {

    private val stores = HashSet<Store>()

    fun register(store: Store) {
        stores.add(store)
    }

    fun unregister(store: Store) {
        stores.remove(store)
    }

    fun dispatch(action: Action) {
        stores.forEach {
            it.onAction(action)
        }
    }
}