package com.hotsxu.flux

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.hotsxu.flux.flux.ActionCreator
import com.hotsxu.flux.flux.BaseActivity
import com.hotsxu.flux.flux.Store
import com.hotsxu.flux.rxbus.Subscribe
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override var mStore: Store = MainStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            ActionCreator.sendAction(MainStore.MAIN_SHOW)
        }
    }

    @Subscribe(MainStore.MAIN_SHOW)
    fun show() {
        Snackbar.make(fab, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
