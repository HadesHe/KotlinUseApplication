package com.example.dispathview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnViewInterrupt1.setOnClickListener {
            val intent=Intent(this@MainActivity,Demo1Activity::class.java)
            startActivity(intent)

        }

        btnViewInterrupt2.setOnClickListener {
            val intent=Intent(this@MainActivity,Demo2Activity::class.java)
            startActivity(intent)
        }
    }
}
