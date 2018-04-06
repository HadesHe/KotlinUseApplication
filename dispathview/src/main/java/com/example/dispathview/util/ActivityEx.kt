package com.example.dispathview.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Created by zhanghehe on 2018/4/6.
 */
fun Context.getScreenMetrics(): DisplayMetrics {
    var wm=this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    var dm=DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm

}