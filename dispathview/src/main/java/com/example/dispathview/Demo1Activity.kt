package com.example.dispathview

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.dispathview.util.getScreenMetrics
import com.example.dispathview.view.HorizontalScrollViewEx

class Demo1Activity : AppCompatActivity() {


    private lateinit var mListContainer: HorizontalScrollViewEx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo1)

        initView()
    }




    private fun initView() {
        var inflater=layoutInflater
        mListContainer=findViewById(R.id.hsv)
        val screenWidth= getScreenMetrics().widthPixels
        val screenHeight= getScreenMetrics().heightPixels
        for (i in 0..3){
            var layout=inflater.inflate(R.layout.content_layout,mListContainer,false) as ViewGroup
            layout.layoutParams.width=screenWidth
            var textView=layout.findViewById<TextView>(R.id.title)
            textView.setText("Page"+(i+1))
            layout.setBackgroundColor(Color.rgb(255/(i+1),255/(i+1),0))
            createList(layout)
            mListContainer.addView(layout)

        }

    }

    private fun createList(layout: ViewGroup) {
        var listView=layout.findViewById<ListView>(R.id.list)

        var datas=ArrayList<String>(0)
        for(i in 0..50){
            datas.add("name "+i)
        }
        var adapter=ArrayAdapter<String>(this,R.layout.content_list_item,R.id.name,datas)
        listView.adapter=adapter
        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this@Demo1Activity,"click item+$position",Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        private val TAG="Demo1Activity"
    }
}
