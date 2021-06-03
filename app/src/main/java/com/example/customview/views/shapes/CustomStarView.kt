package com.example.customview.views.shapes

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import com.blankj.utilcode.util.LogUtils
import com.example.customview.views.CustomView
import kotlin.math.cos
import kotlin.math.sin

class CustomStarView : CustomView {

    var mAngleNum = 5

    private var outRadius = 0f
    private var internalRadius = 0f

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val outArray = mutableListOf(
            Pair(cos(90f) * outRadius, -sin(90f) * outRadius),
            Pair(cos(54f) * internalRadius, -sin(54f) * internalRadius),
            Pair(cos(18f) * outRadius, -sin(18f) * outRadius),
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawStar(canvas!!)
    }

    private fun drawStar(canvas: Canvas) {
        outRadius = radius
        internalRadius = radius / 2

        val averageAngle = 360f / mAngleNum
        val outCircleAngle = 90 - averageAngle
        val halfAverageAngle = averageAngle / 2
        val internalAngle = halfAverageAngle + outCircleAngle

        val outPoint = Point()
        val internalPoint = Point()
        val mPath = Path()

        for (i in 0 until mAngleNum) {
            outPoint.x = (cos(angleToRadian(outCircleAngle + i * averageAngle)) * outRadius).toInt() + cx.toInt()
            outPoint.y = -(sin(angleToRadian(outCircleAngle + i * averageAngle)) * outRadius).toInt() + cy.toInt()

            internalPoint.x = (cos(angleToRadian(internalAngle + i * averageAngle)) * internalRadius).toInt() + cx.toInt()
            internalPoint.y = -(sin(angleToRadian(internalAngle + i * averageAngle)) * internalRadius).toInt() + cy.toInt()

            if (i == 0) {
                mPath.moveTo(outPoint.x.toFloat(), outPoint.y.toFloat())
            }

            mPath.lineTo(outPoint.x.toFloat(), outPoint.y.toFloat())
            mPath.lineTo(internalPoint.x.toFloat(), internalPoint.y.toFloat())
        }
        mPath.close()
        canvas.drawPath(mPath, mPaint)
    }

    /**
     * 角度转弧度，由于Math的三角函数需要传入弧度制，而不是角度值，所以要角度换算为弧度，角度 / 180 * π
     *
     * @param angle 角度
     * @return 弧度
     */
    private fun angleToRadian(angle: Float): Double {
        return angle / 180f * Math.PI
    }
}