package com.example.customview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.customview.common.Env
import com.example.customview.pages.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), AdapterView.OnItemClickListener {

    private val pages = listOf(
            "圆形图片",
            "带删除功能的输入框",
            "绘制简单图形",
            "列表检索功能",
            "动画入口",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "主页"
        Env.app = application

        mListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pages)
        mListView.onItemClickListener = this
    }

    override
    fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val title = pages[position]
        when (position) {
            0 -> {
                startPage(CircleImageActivity::class.java, title)
            }
            1 -> {
                startPage(ClearEditActivity::class.java, title)
            }
            2 -> {
                startPage(SimpleDrawActivity::class.java, title)
            }
            3 -> {
                startPage(LetterIndexActivity::class.java, title)
            }
            4 -> {
                startPage(AnimEntranceActivity::class.java, title)
            }
            else -> {
                startPage(LetterIndexActivity::class.java, title)
            }
        }
    }
}