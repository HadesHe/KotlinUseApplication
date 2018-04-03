package com.example.zhanghehe.kotlinuseapplication.logger

class MessageOnlyLogFilter : LogNode {

    var next:LogNode?=null

    override fun println(priority: Int, tag: String?, msg: String?, tr: Throwable?) {
        next?.println(Log.NONE,null,msg,null)
    }

}
