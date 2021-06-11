package com.example.customview.pages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.customview.BaseActivity
import com.example.customview.R
import com.example.customview.pages.anim.FrameAnimActivity
import com.example.customview.pages.anim.MovePointActivity
import com.example.customview.pages.anim.TweenAnimActivity
import com.example.customview.pages.anim.ValueAnimActivity
import kotlinx.android.synthetic.main.activity_anim_entrance.mListView

class AnimEntranceActivity : BaseActivity(), AdapterView.OnItemClickListener {

    private val pages = listOf(
        "补间动画基础使用",
        "逐帧动画基础使用",
        "属性动画基础使用",
        "移动点"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anim_entrance)
        mListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, pages)
        mListView.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val title = pages[position]
        when (position) {
            0 -> {
                startPage(TweenAnimActivity::class.java, title)
            }
            1 -> {
                startPage(FrameAnimActivity::class.java, title)
            }
            2 -> {
                startPage(ValueAnimActivity::class.java, title)
            }
            3 -> {
                startPage(MovePointActivity::class.java, title)
            }
            else -> {
            }
        }
    }
}