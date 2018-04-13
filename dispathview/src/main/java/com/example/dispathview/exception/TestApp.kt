package com.example.dispathview.exception

import android.app.Application

/**
 * Created by zhanghehe on 2018/4/13.
 */
class TestApp:Application(){
    override fun onCreate() {
        super.onCreate()

        val crashHandler=CrashHandler.getInstance(this)

    }

    companion object {
        private lateinit var sInstance: TestApp

        private fun getInstance(): TestApp {
            if(sInstance==null){
                sInstance=TestApp()
            }
            return sInstance
        }
    }
}