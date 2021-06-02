package com.example.customview.views

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ConvertUtils

open class CustomView : View {

    private val sideWidth = ConvertUtils.dp2px(2f).toFloat()

    protected val mPaint = Paint()

    protected var cx: Float = 0f
    protected var cy: Float = 0f
    protected var radius: Float = 0f

    protected var mLeft: Float = 0f
    protected var mTop: Float = 0f
    protected var mRight: Float = 0f
    protected var mBottom: Float = 0f


    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        cx = measuredWidth / 2f
        cy = measuredHeight / 2f
        radius = cx - sideWidth

        mLeft = sideWidth
        mTop = sideWidth
        mRight = measuredWidth - sideWidth
        mBottom = measuredHeight - sideWidth
    }

    private fun init() {
        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#000000")
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = sideWidth
    }
}