package com.example.customview.views

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils

open class CustomView : View {

    private val sideWidth = ConvertUtils.dp2px(2f).toFloat()

    protected val mPaint = Paint()

    protected var parentWidth: Int = 0
    protected var parentHeight: Int = 0

    protected var cx: Float = 0f
    protected var cy: Float = 0f
    protected var radius: Float = 0f

    protected var mLeft: Float = 0f
    protected var mTop: Float = 0f
    protected var mRight: Float = 0f
    protected var mBottom: Float = 0f

    private var sx = 0
    private var sy = 0

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
        parentWidth = (parent as ViewGroup).measuredWidth
        parentHeight = (parent as ViewGroup).measuredHeight

        cx = measuredWidth / 2f
        cy = measuredHeight / 2f
        radius = cx - sideWidth

        mLeft = sideWidth
        mTop = sideWidth
        mRight = measuredWidth - sideWidth
        mBottom = measuredHeight - sideWidth
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                sx = event.rawX.toInt()
                sy = event.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.rawX
                val y = event.rawY

                // 手指移动距离
                val dx = x - sx
                val dy = y - sy

                var l = left + dx
                var t = top + dy
                var r = right + dx
                var b = bottom + dy

                if (l < 0) {
                    l = 0f
                    r = width.toFloat()
                }

                if (t < 0) {
                    t = 0f
                    b = height.toFloat()
                }

                if (r > parentWidth) {
                    r = parentWidth.toFloat()
                    l = parentWidth - width.toFloat()
                }

                if (b > parentHeight) {
                    b = parentHeight.toFloat()
                    t = parentHeight - height.toFloat()
                }

                layout(l.toInt(), t.toInt(), r.toInt(), b.toInt())

                sx = x.toInt()
                sy = y.toInt()
            }
            MotionEvent.ACTION_UP -> {
            }
            else -> {
            }
        }

        return true
    }


    private fun init() {
        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#000000")
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = sideWidth
    }
}