package com.example.dispathview.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller

/**
 * Created by zhanghehe on 2018/4/4.
 */
class HorizontalScrollViewEx2 : ViewGroup{

    constructor(context: Context):super(context){
    }

    constructor(context: Context,attrs:AttributeSet):super(context,attrs){

    }

    constructor(context: Context,attrs: AttributeSet,defStyleAttr:Int):super(context,attrs,defStyleAttr){

    }

    private var mScroller: Scroller

    private var mVelocityTracher: VelocityTracker

    init {
        mScroller=Scroller(context)
        mVelocityTracher=VelocityTracker.obtain()

    }

    private var mChildrenSize: Int = 0


    private var mChildWidth: Int=0

    private var mLastX: Int = 0

    private var mLastY: Int=0

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var x=ev.getX().toInt()
        var y=ev.getY().toInt()
        when(ev.action){
            MotionEvent.ACTION_DOWN ->{
                mLastX=x
                mLastY=y
                if (mScroller.isFinished.not()) {
                    mScroller.abortAnimation()
                    return true
                }
                return false

            }
            else -> {
                return true
            }
        }
    }

    private var mChildIndex: Int = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(TAG,"onTouchEvent action: ${event.action}")
        mVelocityTracher.addMovement(event)

        var x=event.getX().toInt()
        var y=event.getY().toInt()
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                if (mScroller.isFinished.not()) {
                    mScroller.abortAnimation()
                }

            }
            MotionEvent.ACTION_MOVE -> {
                var deltaX=x-mLastX
                var deltaY=y-mLastY
                Log.d(TAG,"move,deltaX:$deltaX deltaY: $deltaY")
                scrollBy(-deltaX,0)

            }
            MotionEvent.ACTION_UP -> {
                var scrollX=scrollX
                var scrollToChildIndex=scrollX/mChildWidth
                Log.d(TAG,"current index:$scrollToChildIndex")
                mVelocityTracher.computeCurrentVelocity(1000)
                var xVelocity=mVelocityTracher.getXVelocity()
                if(Math.abs(xVelocity)>=50){
                    if(xVelocity>0){
                        mChildIndex=mChildIndex-1
                    }else{
                        mChildIndex=mChildIndex+1
                    }
                }else{
                    mChildIndex=(scrollX+mChildWidth/2)/mChildWidth

                }
                mChildIndex=Math.max(0,Math.min(mChildIndex,mChildrenSize-1))
                var dx=mChildIndex*mChildWidth-scrollX
                smoothScrollBy(dx,0)
                mVelocityTracher.clear()
                Log.d(TAG,"index: $scrollToChildIndex dx: $dx")

            }
            else -> {

            }

        }
        mLastX=x
        mLastY=y

        return true
    }

    private fun smoothScrollBy(dx: Int, i: Int) {
        mScroller.startScroll(scrollX,0,dx,0,500)
        invalidate()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        Log.d(TAG,"width:"+width)
        var childLeft=0
        val childCount=childCount
        mChildrenSize=childCount

        for (i in 0..(childCount-1)){
            val childView=getChildAt(i)
            if (childView.visibility != View.GONE) {
                val childWidth=childView.measuredWidth
                mChildWidth=childWidth
                childView.layout(childLeft,0,childLeft+childWidth,childView.measuredHeight)
                childLeft+=childWidth
            }
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var measuredWidth=0
        var measuredHeight=0
        val childCount=childCount
        measureChildren(widthMeasureSpec,heightMeasureSpec)

        val widthSpaceSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightSpaceSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
        if (childCount === 0) {
            setMeasuredDimension(0, 0)
        } else if (heightSpecMode == View.MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredHeight = childView.measuredHeight
            setMeasuredDimension(widthSpaceSize, childView.measuredHeight)
        } else if (widthSpecMode == View.MeasureSpec.AT_MOST) {
            val childView = getChildAt(0)
            measuredWidth = childView.measuredWidth * childCount
            setMeasuredDimension(measuredWidth, heightSpaceSize)
        } else {
            val childView = getChildAt(0)
            measuredWidth = childView.measuredWidth * childCount
            measuredHeight = childView.measuredHeight
            setMeasuredDimension(measuredWidth, measuredHeight)
        }
    }

    override fun computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.currX,mScroller.currY)
            postInvalidate()
        }
    }

    override fun onDetachedFromWindow() {
        mVelocityTracher.recycle()
        super.onDetachedFromWindow()
    }

    companion object {
        private val TAG="HorizontalScrollViewEx2"
    }

}