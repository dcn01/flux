package com.hotsxu.flux.rxbus

import android.util.Log
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.lang.reflect.Method
import java.util.*

/**
 * Created by hotsxu on 2017/12/25.
 *
 * RxKotlin实现 事件总站
 */
object RxBus {

    private val subscriberToDisposable = HashMap<Any, HashSet<Disposable>>()
    private val subscriberToMethod = HashMap<Any, HashSet<SubscriberMethod>>()

    private val bus: Subject<String> by lazy {
        PublishSubject.create<String>().toSerialized()
    }

    fun post(tag: String = "default") {
        bus.onNext(tag)
    }

    /**
     * 订阅
     * @param subscriber 订阅者
     */
    fun register(subscriber: Any?) {
        subscriber?.
                javaClass?.
                declaredMethods?.
                forEach {
                    //参数
                    if (it.isAnnotationPresent(Subscribe::class.java)) {
                        addSubscriberToMethod(subscriber, it)
                    }
                }
    }

    /**
     * 方法加入Map中
     * 并订阅
     */
    private fun addSubscriberToMethod(subscriber: Any, method: Method) {
        var methods = subscriberToMethod[subscriber]
        if (methods == null) {
            methods = HashSet()
            subscriberToMethod.put(subscriber, methods)
        }
        val subscribe = method.getAnnotation(Subscribe::class.java)
        val subscribeMethod = SubscriberMethod(
                method,
                subscribe.tag,
                subscribe.threadMode)
        methods.add(subscribeMethod)
        addSubscriber(subscriber, subscribeMethod)
    }

    /**
     * RxJava添加订阅者
     */
    private fun addSubscriber(subscriber: Any, subscriberMethod: SubscriberMethod) {
        var disposables = subscriberToDisposable[subscriber]
        if (disposables == null) {
            disposables = HashSet()
            subscriberToDisposable.put(subscriber, disposables)
        }
        disposables.add(toObservable(subscriberMethod)
                .observeOn(when (subscriberMethod.threadMode) {
                    ThreadMode.CURRENT -> Schedulers.trampoline()
                    ThreadMode.NEW -> Schedulers.newThread()
                    ThreadMode.IO -> Schedulers.io()
                    ThreadMode.MAIN -> AndroidSchedulers.mainThread()
                }).subscribeBy(
                //调用订阅方法
                onNext = {
                    subscriberMethod.invoke(subscriber)
                },
                onError = {
                    //异常处理
                    Log.e("RxBus:", "addSubscriber error")
                }
        ))
    }

    private fun toObservable(subscriberMethod: SubscriberMethod):
            Observable<String> {
        return bus.filter {
            return@filter it ==
                    subscriberMethod.tag
        }
    }

    /**
     * 取消订阅
     */
    fun unregister(subscriber: Any?) {
        subscriber?.let {
            subscriberToMethod.remove(subscriber)
            subscriberToDisposable[it]?.forEach {
                if (!it.isDisposed)
                    it.dispose()
            }
        }
    }
}