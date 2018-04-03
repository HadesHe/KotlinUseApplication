package com.example.zhanghehe.kotlinuseapplication.logger

object Log {

    val NONE=-1
    val VERBOSE=android.util.Log.VERBOSE
    val DEBUG=android.util.Log.DEBUG
    val INFO=android.util.Log.INFO
    val WARN=android.util.Log.WARN
    val ERROR=android.util.Log.ERROR
    val ASSERT=android.util.Log.ASSERT

    var logNode:LogNode?=null

    fun println(priority:Int,tag:String,msg:String?,tr:Throwable?=null){
        logNode?.println(priority,tag,msg,tr)
    }

    fun v(tag: String,msg: String?=null,tr: Throwable?=null){
        println(VERBOSE,tag,msg,tr)
    }

    fun d(tag: String,msg: String?=null,tr: Throwable?=null){
        println(DEBUG,tag,msg,tr)
    }

    fun i(tag: String,msg: String,tr: Throwable?=null){
        println(INFO,tag,msg,tr)
    }

    fun w(tag: String,msg: String?=null,tr: Throwable?=null){
        println(WARN,tag,msg,tr)
    }

    fun e(tag: String,msg: String,tr: Throwable?=null){
        println(ERROR,tag,msg,tr)
    }

    fun wtf(tag: String,msg: String?=null,tr: Throwable?=null){
        println(ASSERT,tag,msg,tr)
    }

}
