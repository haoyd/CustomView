package com.example.customview.views.shapes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import com.example.customview.views.CustomView

class CustomTriangleView : CustomView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val path = Path()
        path.moveTo(cx, mTop)
        path.lineTo(mRight, mBottom)
        path.lineTo(mLeft, mBottom)
        path.lineTo(cx, mTop)
        path.close()
        canvas?.drawPath(path, mPaint)
    }
}