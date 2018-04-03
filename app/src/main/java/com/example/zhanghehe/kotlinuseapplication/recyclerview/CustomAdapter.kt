package com.example.zhanghehe.kotlinuseapplication.recyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.zhanghehe.kotlinuseapplication.R
import com.example.zhanghehe.kotlinuseapplication.bean.LightUse
import com.example.zhanghehe.kotlinuseapplication.logger.Log
import android.graphics.Typeface
import android.R.attr.textColor
import android.graphics.Color
import com.amulyakhare.textdrawable.TextDrawable


class CustomAdapter(val dataset: List<LightUse>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v=LayoutInflater.from(parent.context).inflate(R.layout.text_row_item,parent,false)

        return ViewHolder(v)
    }


    override fun getItemCount() = dataset.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG,"Element $position set.")


        val myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(dataset[position].id.toString(), Color.DKGRAY)


        holder.ivNum.setImageDrawable(myDrawable)
        holder.tvEnvDesc.text=dataset[position].envDesc
        holder.tvLightUse.text=dataset[position].lightUse
    }

    class ViewHolder(v:View):RecyclerView.ViewHolder(v){

        val tvEnvDesc:TextView
        val tvLightUse:TextView
        val ivNum:ImageView

        init {
            v.setOnClickListener {
                Log.d(TAG,"Element $adapterPosition clicked")
            }

            tvEnvDesc=v.findViewById(R.id.tvEnvDesc)
            tvLightUse=v.findViewById(R.id.tvLightUse)
            ivNum=v.findViewById(R.id.ivNum)
        }

    }
    companion object {
        private val TAG="CustomAdapter"
    }
}
