package com.hotsxu.flux.rxbus

enum class ThreadMode {

    /**
     * 当前线程
     */
    CURRENT,

    /**
     * io 线程
     */
    IO,

    /**
     * android collect thread
     */
    MAIN,


    /**
     * new thread
     */
    NEW
}
