package com.hotsxu.flux.rxbus

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

data class SubscriberMethod(private var method: Method,
                            var tag: String,
                            var threadMode: ThreadMode) {
    /**
     * 调用方法
     */
    operator fun invoke(subscriber: Any) {
        try {
            method.invoke(subscriber)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }
}