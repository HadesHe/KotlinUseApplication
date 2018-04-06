package com.example.dispathview

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.dispathview.util.getScreenMetrics
import com.example.dispathview.util.toast
import com.example.dispathview.view.HorizontalScrollViewEx2
import com.example.dispathview.view.ListViewEx

/**
 * 内部拦截法
 */
class Demo2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo2)

        initView()
    }

    private lateinit var mListContainer: HorizontalScrollViewEx2

    private fun initView(){
        var  inflater=layoutInflater
        mListContainer=findViewById<HorizontalScrollViewEx2>(R.id.hsv2)
        val screenWidth=getScreenMetrics().widthPixels
        val screenHeight=getScreenMetrics().heightPixels

        for(i in 0..2 ){
            var layout=inflater.inflate(R.layout.content_layout2,mListContainer,false) as ViewGroup
            layout.layoutParams.width=screenWidth
            var textView=layout.findViewById<TextView>(R.id.title)
            textView.setText("page ${i+1}")
            layout.setBackgroundColor(Color.rgb(255/(i+1),255/(i+1),0))
            createList(layout)
            mListContainer.addView(layout)

        }
    }

    private fun createList(layout: ViewGroup) {
        var listView=layout.findViewById<ListViewEx>(R.id.list)
        var datas=ArrayList<String>(0)
        for(i in 0..49){
            datas.add("name $i")
        }

        var adapter=ArrayAdapter<String>(this,R.layout.content_list_item,R.id.name,datas)
        listView.mHorizontalScrollViewEx2=mListContainer
        listView.adapter=adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            toast("click item $position",Toast.LENGTH_SHORT)
        }


    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        Log.d(TAG,"dispatchTouchEvent action: ${ev.action}")
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d(TAG,"onTouchEvent action ${event.action}")
        return super.onTouchEvent(event)
    }

    companion object {
        private val TAG="Demo2Activity"
    }

}
