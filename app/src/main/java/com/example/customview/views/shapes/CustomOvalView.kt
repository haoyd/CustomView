package com.example.customview.views.shapes

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import com.example.customview.views.CustomView

class CustomOvalView : CustomView {

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawOval(mLeft + 20, mTop, mRight - 20, mBottom, mPaint)
    }
}