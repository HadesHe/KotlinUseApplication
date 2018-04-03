package com.example.zhanghehe.kotlinuseapplication.logger

interface LogNode {

    fun println(priority:Int,tag:String?,msg:String?,tr:Throwable?)

}
