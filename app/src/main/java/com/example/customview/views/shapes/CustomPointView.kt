package com.example.customview.views.shapes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.View

class CustomPointView : View {

    private var mColor = Color.RED
    private val mPaint = Paint()

    private var cx: Float = 0f
    private var cy: Float = 0f

    private var xPosition: Float = 0f
    private var yPosition: Float = 0f

    private var hasSetPoint = false

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mPaint.isAntiAlias = true
        mPaint.color = mColor
        mPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        cx = measuredWidth / 2f
        cy = measuredHeight / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        if (!hasSetPoint) {
            canvas?.drawCircle(cx, cy, 30f, mPaint)
            return
        }

        canvas?.drawCircle(xPosition, yPosition, 30f, mPaint)
    }

    fun setPosition(x: Float, y: Float) {
        xPosition = x
        yPosition = y
        hasSetPoint = true
        invalidate()
    }
}