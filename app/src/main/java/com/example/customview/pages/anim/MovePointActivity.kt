package com.example.customview.pages.anim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.ScreenUtils
import com.example.customview.BaseActivity
import com.example.customview.R
import kotlinx.android.synthetic.main.activity_move_point.*
import kotlin.random.Random

class MovePointActivity : BaseActivity() {

    private val appWidth = ScreenUtils.getAppScreenWidth() - 100
    private val appHeight = ScreenUtils.getAppScreenHeight() - ConvertUtils.dp2px(50f) - 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_move_point)
    }

    fun changePosition(view: View) {
        val x = (0..appWidth).random()
        val y = (0..appHeight).random()

        pointView.setPosition(x.toFloat(), y.toFloat())
    }
}