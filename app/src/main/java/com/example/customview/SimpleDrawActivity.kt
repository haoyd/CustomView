package com.example.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ConvertUtils
import com.example.customview.views.shapes.*
import kotlinx.android.synthetic.main.activity_simple_draw.*

class SimpleDrawActivity : AppCompatActivity() {

    private val dp50 = ConvertUtils.dp2px(50f)
    private val lp = ViewGroup.LayoutParams(dp50, dp50)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_draw)
    }

    private fun setView(v: View) {
        cinema.removeAllViews()
        v.layoutParams = lp
        cinema.addView(v)
    }

    fun drawCircle(view: View) {
        setView(CustomCircleView(this))
    }

    fun drawOval(view: View) {
        setView(CustomOvalView(this))
    }

    fun drawRectangle(view: View) {
        setView(CustomRectangleView(this))
    }

    fun drawTriangle(view: View) {
        setView(CustomTriangleView(this))
    }

    fun drawStar(view: View) {
        setView(CustomStarView(this))
    }

}