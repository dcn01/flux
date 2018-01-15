package com.hotsxu.flux.flux

/**
 * Created by hotsxu on 2017/12/25.
 *
 * 创建事件
 */
object ActionCreator {

    fun sendAction(type: String, data: Any = Any()) {
        Dispatcher.dispatch(Action(type, data))
    }

}