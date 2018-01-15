package com.hotsxu.flux

import com.hotsxu.flux.flux.Action
import com.hotsxu.flux.flux.Store

/**
 * Created by hotsxu on 2018/1/16.
 */
object MainStore : Store() {

    const val MAIN_SHOW = "main-show"


    override fun onAction(action: Action) {
        when (action.type) {
            MAIN_SHOW -> {
                    //dosomething
            }
        }
        emitStoreChange(action.type)
    }

}