package com.hotsxu.flux.flux

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by hotsxu on 2017/12/25.
 */
abstract class BaseActivity : AppCompatActivity() {

    protected abstract var mStore: Store

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Dispatcher.register(mStore)
    }

    override fun onResume() {
        super.onResume()
        mStore.register(this)
    }

    override fun onPause() {
        super.onPause()
        mStore.unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        Dispatcher.unregister(mStore)
    }


}