package com.example.dispathview.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ListView

/**
 * Created by zhanghehe on 2018/4/6.
 */
class ListViewEx:ListView{

    constructor(context:Context):super(context){

    }

    constructor(context:Context,attrs:AttributeSet):super(context,attrs){

    }

    constructor(context:Context,attrs:AttributeSet,defStyleAttr:Int):super(context,attrs,defStyleAttr){

    }

    private var mLastX: Int = 0

    private var mLastY: Int = 0

    lateinit var mHorizontalScrollViewEx2:HorizontalScrollViewEx2

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var x=ev.getX().toInt()
        var y=ev.getY().toInt()

        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(true)

            }
            MotionEvent.ACTION_MOVE -> {
                var deltaX=x-mLastX
                var deltaY=y-mLastY
                Log.d(TAG,"dx: $deltaX dy: $deltaY")
                if(Math.abs(deltaX)>Math.abs(deltaY)){
                    mHorizontalScrollViewEx2.requestDisallowInterceptTouchEvent(false)
                }


            }
            MotionEvent.ACTION_UP -> {

            }
            else -> {

            }
        }
        mLastX=x
        mLastY=y

        return super.dispatchTouchEvent(ev)
    }

    companion object {
        private val TAG="ListViewEx"
    }


}