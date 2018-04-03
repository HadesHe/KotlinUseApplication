package com.example.zhanghehe.kotlinuseapplication.recyclerview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.zhanghehe.kotlinuseapplication.R
import com.example.zhanghehe.kotlinuseapplication.bean.LightUse
import java.util.*

class RecyclerViewFragment : Fragment() {

    enum class LayoutManagerType{
        GRID_LAYOUT_MANAGER,LINEAR_LAYOUT_MANAGER

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataSet()
    }


    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var currentLayoutManagerType: LayoutManagerType

    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView=inflater.inflate(R.layout.recycler_view_frag,container,false).apply {
            tag= TAG
        }

        recyclerView=rootView.findViewById(R.id.recyclerView)

        layoutManager=LinearLayoutManager(activity)
        currentLayoutManagerType=LayoutManagerType.LINEAR_LAYOUT_MANAGER

        if (savedInstanceState != null) {
            currentLayoutManagerType=savedInstanceState.getSerializable(KEY_LAYOUT_MANAGER)
            as LayoutManagerType
        }

        setRecyclerViewLayoutManager(currentLayoutManagerType)

        recyclerView.adapter=CustomAdapter(dataset)
        rootView.findViewById<RadioButton>(R.id.linear_layout_rb).setOnClickListener{
            setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER)
        }

        rootView.findViewById<RadioButton>(R.id.grid_layout_rb).setOnClickListener{
            setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER)
        }

        return rootView
    }

    private fun setRecyclerViewLayoutManager(currentLayoutManagerType: LayoutManagerType) {
        var scrollPosition=0

        if(recyclerView.layoutManager != null){
            scrollPosition=(recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        }

        when(currentLayoutManagerType){
            RecyclerViewFragment.LayoutManagerType.GRID_LAYOUT_MANAGER -> {
                layoutManager=GridLayoutManager(activity, SPAN_COUNT)
                this@RecyclerViewFragment.currentLayoutManagerType =LayoutManagerType.GRID_LAYOUT_MANAGER
            }
            RecyclerViewFragment.LayoutManagerType.LINEAR_LAYOUT_MANAGER -> {
                layoutManager= LinearLayoutManager(activity)
                this@RecyclerViewFragment.currentLayoutManagerType=LayoutManagerType.LINEAR_LAYOUT_MANAGER
            }
        }

        with(recyclerView){
            layoutManager=this@RecyclerViewFragment.layoutManager
            scrollToPosition(scrollPosition)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(KEY_LAYOUT_MANAGER,currentLayoutManagerType)
        super.onSaveInstanceState(outState)
    }

    private lateinit var dataset: List<LightUse>

    private fun initDataSet(){
        dataset= listOf<LightUse>(
                LightUse(1,"请打开近光灯","近光灯"),
                LightUse(2,"夜间同方向静距离跟车行使","近光灯"),
                LightUse(3,"夜间与机动车会车","近光灯"),
                LightUse(4,"夜间直行通过路口","近光灯"),
                LightUse(5,"夜间在窄路与非机动车会车","近光灯"),
                LightUse(6,"夜间在照明良好的道路上行使","近光灯"),
                LightUse(7,"夜间在窄桥与非机动车会车","近光灯"),
                LightUse(8,"夜间在有路灯的道路上行使","近光灯"),
                LightUse(9,"夜间路口左转弯","近光灯+左转向灯"),
                LightUse(10,"夜间路口右转弯","近光灯+右转向灯"),
                LightUse(11,"请打开远光灯","远光灯"),
                LightUse(12,"夜间在没有路灯，照明不良条件下行使","远光灯"),
                LightUse(13,"夜间通过急弯、坡路","交替使用远近光灯"),
                LightUse(14,"夜间通过坡路、拱桥","交替使用远近光灯"),
                LightUse(15,"夜间通过急弯、拱桥","交替使用远近光灯"),
                LightUse(16,"夜间通过拱桥、人行横道","交替使用远近光灯"),
                LightUse(17,"夜间通过没有信号灯控制的路口","交替使用远近光灯"),
                LightUse(18,"夜间超过前方车辆","交替使用远近光灯"),
                LightUse(19,"路边临时停车","危险报警闪光灯+示廓灯"),
                LightUse(20,"雾天行驶","雾灯+危险报警闪光灯")


        )
    }


    companion object {
        private val TAG="RecyclerViewFragment"
        private val KEY_LAYOUT_MANAGER="layoutManager"
        private val SPAN_COUNT=2
        private val DATASET_COUNT=20

    }
}
