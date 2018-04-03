package com.example.zhanghehe.kotlinuseapplication.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.example.zhanghehe.kotlinuseapplication.logger.Log
import com.example.zhanghehe.kotlinuseapplication.logger.LogWrapper

/**
 * Created by zhanghehe on 2018/3/28.
 */
open class BaseSampleActivity:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStart() {
        super.onStart()
        initializedLogging()
    }

    open  fun initializedLogging() {
        val logWrapper= LogWrapper()
        Log.logNode=logWrapper
        Log.i(TAG,"Ready")
    }

    companion object {
        val TAG="BaseSampleActivity"
    }
}