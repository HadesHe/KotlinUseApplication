package com.example.zhanghehe.kotlinuseapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ViewAnimator
import com.example.zhanghehe.kotlinuseapplication.base.BaseSampleActivity
import com.example.zhanghehe.kotlinuseapplication.logger.Log
import com.example.zhanghehe.kotlinuseapplication.logger.LogFragment
import com.example.zhanghehe.kotlinuseapplication.logger.LogWrapper
import com.example.zhanghehe.kotlinuseapplication.logger.MessageOnlyLogFilter
import com.example.zhanghehe.kotlinuseapplication.recyclerview.RecyclerViewFragment

class MainActivity : BaseSampleActivity() {

    private var logShown=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState==null){
            supportFragmentManager.beginTransaction().run {
                replace(R.id.sample_content_fragment, RecyclerViewFragment())
                commit()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.menu_toggle_log).run{
            isVisible=findViewById<ViewAnimator>(R.id.sample_output) is ViewAnimator
            setTitle(if(logShown) "hide_log" else "show_log")
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem)=when(item.itemId){
        R.id.menu_toggle_log ->{
            logShown=logShown.not()
            val output=findViewById<ViewAnimator>(R.id.sample_output) as ViewAnimator
            output.displayedChild=if(logShown)1 else 0

            invalidateOptionsMenu()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    override fun initializedLogging() {
        val logWrapper=LogWrapper()
        Log.logNode=logWrapper
        val msgFilter= MessageOnlyLogFilter()
        logWrapper.next=msgFilter
        val logFragment=supportFragmentManager.findFragmentById(R.id.log_fragment) as LogFragment
        msgFilter.next=logFragment.logView

        Log.i(TAG,"Ready")

    }

    companion object {
        val TAG="MainActivity"
    }
}
