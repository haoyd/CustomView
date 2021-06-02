package com.example.customview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.customview.common.Env
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private val pages = listOf(
            "圆形图片",
            "带删除功能的输入框",
            "绘制简单图形",
            "列表检索功能",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Env.app = application

        mListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pages)
        mListView.onItemClickListener = this
    }

    override
    fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> {
                startActivity(Intent(this, CircleImageActivity::class.java))
            }
            1 -> {
                startActivity(Intent(this, ClearEditActivity::class.java))
            }
            2 -> {
                startActivity(Intent(this, SimpleDrawActivity::class.java))
            }
            else -> {
                startActivity(Intent(this, LetterIndexActivity::class.java))
            }
        }
    }
}