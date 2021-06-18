package com.example.customview.pages.anim

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ScreenUtils
import com.example.customview.BaseActivity
import com.example.customview.R
import kotlinx.android.synthetic.main.activity_move_child.*

class MoveChildActivity : BaseActivity() {

    private val appWidth = ScreenUtils.getAppScreenWidth() - 100
    private val appHeight = ScreenUtils.getAppScreenHeight() - ConvertUtils.dp2px(50f) - 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_child)

        val tv = TextView(this)
        tv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        tv.text = "hello"
        mView.provideView("hello", tv)
    }

    fun changePosition(view: View) {
        val x = (0..appWidth).random()
        val y = (0..appHeight).random()

//        mView.setChildPosition("hello", x.toFloat(), y.toFloat())
        mView.moveMyPosition(x.toFloat(), y.toFloat())
    }
}