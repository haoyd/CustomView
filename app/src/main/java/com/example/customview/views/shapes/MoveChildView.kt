package com.example.customview.views.shapes

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class MoveChildView : FrameLayout {

    private val viewsPool: MutableMap<String, View> = mutableMapOf()

    private var mWidth = 0
    private var mHeight = 0

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
    }

    /**
     * 添加子 View
     * @param name String
     * @param view View
     */
    fun provideView(name: String, view: View) {
        viewsPool[name] = view
        addView(view)
    }

    /**
     * 设置子 View 的位置
     * @param name String
     * @param x Float
     * @param y Float
     */
    fun setChildPosition(name: String, x: Float, y: Float) {
        var realX = x
        var realY = y

        if (realX < 0) {
            realX = 0f
        }

        if (realY < 0) {
            realY = 0f
        }

        val childWidth = viewsPool[name]?.width ?: 0
        if (realX > mWidth - childWidth) {
            realX = mWidth.toFloat() - childWidth
        }

        val childHeight = viewsPool[name]?.height ?: 0
        if (realY > mHeight - childHeight) {
            realY = mHeight.toFloat() - childHeight
        }

        viewsPool[name]?.translationX = realX
        viewsPool[name]?.translationY = realY
    }

    /**
     * 改变当前 View 的位置
     * @param x Float
     * @param y Float
     */
    fun moveMyPosition(x: Float, y: Float) {
        translationX = x
        translationY = y
    }
}