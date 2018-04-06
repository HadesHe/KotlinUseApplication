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
class HorizontalScrollViewEx : ViewGroup{

    private var mScroller: Scroller

    private var mVelocityTracher: VelocityTracker

    private var mLastX=0
    private var mLastY=0
    private var mChildIndex: Int=0
    private var mLastXIntercept:Int=0
    private var mLastYIntercept:Int=0


    constructor(context: Context):super(context){
    }

    constructor(context: Context,attrs:AttributeSet):super(context,attrs){

    }

    constructor(context: Context,attrs: AttributeSet,defStyleAttr:Int):super(context,attrs,defStyleAttr){

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var intercepted=false
        var x=ev.getX().toInt()
        var y=ev.getY().toInt()

        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                intercepted=false
                if(mScroller.isFinished.not()){
                    mScroller.abortAnimation()
                    intercepted=true
                }

            }
            MotionEvent.ACTION_MOVE ->{
                var deltaX=x-mLastXIntercept
                var deltaY=y-mLastYIntercept
                if(Math.abs(deltaX)>Math.abs(deltaY)){

                    intercepted=true
                }else{
                    intercepted=false
                }

            }
            MotionEvent.ACTION_UP ->{
                intercepted=false

            }
            else -> {

            }
        }
        Log.d(TAG,"intercepted="+intercepted)
        mLastX=x
        mLastY=y
        mLastXIntercept=x
        mLastYIntercept=y

        return intercepted
    }




    override fun onTouchEvent(event: MotionEvent): Boolean {
        mVelocityTracher.addMovement(event)
        var x=(event.getX().toInt() )
        var y=(event.getY().toInt())
        when(event.action){
            MotionEvent.ACTION_DOWN->{
                if (mScroller.isFinished.not()) {
                    mScroller.abortAnimation()

                }

            }
            MotionEvent.ACTION_MOVE ->{
                var deltaX=x-mLastX
                var deltaY=y-mLastY
                scrollBy(-deltaX,0)

            }
            MotionEvent.ACTION_UP   ->{
                var scrollX=scrollX
                var scrollToChildIndex=scrollX/mChildWidth
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

            }
            else ->{

            }
        }

        mLastX=x
        mLastY=y
        return true
    }

    private fun smoothScrollBy(dx:Int,dy:Int){
        mScroller.startScroll(scrollX,0,dx,0,500)
        invalidate()
    }


    init {
        mScroller= Scroller(context)
        mVelocityTracher=VelocityTracker.obtain()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var measuredWidth=0
        var measuredHeight=0
        val childCount=childCount
        measureChildren(widthMeasureSpec,heightMeasureSpec)

        var widthSpaceSize=MeasureSpec.getSize(widthMeasureSpec)
        var widthSpecMode=MeasureSpec.getMode(widthMeasureSpec)
        var heightSpaceSize=MeasureSpec.getSize(heightMeasureSpec)
        var heightSpecMode=MeasureSpec.getMode(heightMeasureSpec)

        if (childCount==0) {
            setMeasuredDimension(0,0)

        }else if(heightSpecMode==MeasureSpec.AT_MOST){
            val childView=getChildAt(0)
            measuredHeight=childView.measuredHeight
            setMeasuredDimension(widthSpaceSize,childView.measuredHeight)

        }else if(widthSpecMode==MeasureSpec.AT_MOST){
            val childView=getChildAt(0)
            measuredWidth=childView.measuredWidth*childCount
            setMeasuredDimension(measuredWidth,heightSpaceSize)

        }else{
            val childView=getChildAt(0)
            measuredWidth=childView.measuredWidth*childCount
            measuredHeight=childView.measuredHeight
            setMeasuredDimension(measuredWidth,measuredHeight)

        }




    }

    private var mChildrenSize: Int = 0


    private var mChildWidth: Int=0

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft=0
        val childCount=childCount
        mChildrenSize=childCount

        if(childCount>0) {
            for (i in 0..childCount) {
                val childView = getChildAt(i)
                if(childView!=null) {
                    if (childView.visibility != View.GONE) {
                        val childWidth = childView.measuredWidth
                        mChildWidth = childWidth
                        childView.layout(childLeft, 0, childLeft + childWidth, childView.measuredHeight)
                        childLeft += childWidth
                    }
                }

            }
        }


    }

    override fun computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.currX,mScroller.currY)
            postInvalidate()
        }
    }


    override fun onDetachedFromWindow() {
        mVelocityTracher.recycle()
        super.onDetachedFromWindow()
    }


    companion object {
        private val TAG="HorizontalScrollViewEx"
    }

}