package com.example.customview.views.shapes

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.example.customview.R

@SuppressLint("CustomViewStyleable")
class CirCleProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val circlePaint: Paint = Paint()
    private val scalePaint: Paint = Paint()
    private val textPaint: Paint = Paint()
    private val bottomTextPaint: Paint = Paint()

    private val circleColor: Int //圆弧颜色
    private var circleStartColor = 0 //圆弧渐变起始颜色
    private var circleEndColor = 0 //圆弧结束颜色
    private val circleBgColor: Int //圆弧背景颜色
    private val circleWidth: Float
    private val circleBgWidth: Float
    private val textColor: Int
    private val textSize: Float
    private val totalAngle: Int
    private val startAngle: Int
    private var currentProgress: Float
    private var maxProgress: Float
    private var section = 0f
    private var currentAngle = 0f
    private var lastAngle = 0f
    private var progressAnimator: ValueAnimator? = null
    private val duration = 1000
    private var isDefaultText = false
    private var mTextValue: String? = null
    private var showAnim = true
    private val scaleSize = ConvertUtils.dp2px(4f)
    private val scaleWidth = ConvertUtils.dp2px(1f)
    private val scaleColor = Color.parseColor("#D67FDE")
    private val bottomText = "综合得分"
    private val bottomTextSize = ConvertUtils.dp2px(12f).toFloat()
    private val bottomTextColor = Color.parseColor("#AEAFB7")
    private val dp8 = ConvertUtils.dp2px(8f)

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ymyy_CirCleProgressBar)
        circleColor = typedArray.getColor(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_color, Color.RED)
        circleStartColor = typedArray.getColor(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_start_color, 0)
        circleEndColor = typedArray.getColor(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_end_color, 0)
        circleBgColor = typedArray.getColor(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_bg_color, Color.YELLOW)
        circleWidth = typedArray.getDimension(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_width, 2f)
        circleBgWidth = typedArray.getDimension(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_bg_width, 2f)
        textColor = typedArray.getColor(R.styleable.ymyy_CirCleProgressBar_ymyy_text_color, Color.BLUE)
        textSize = typedArray.getDimension(R.styleable.ymyy_CirCleProgressBar_ymyy_text_size, 10f)
        totalAngle = typedArray.getInteger(R.styleable.ymyy_CirCleProgressBar_ymyy_total_angle, 360)
        startAngle = typedArray.getInteger(R.styleable.ymyy_CirCleProgressBar_ymyy_start_angle, 0)
        currentProgress = typedArray.getFloat(R.styleable.ymyy_CirCleProgressBar_ymyy_current_progress, 0f)
        maxProgress = typedArray.getFloat(R.styleable.ymyy_CirCleProgressBar_ymyy_max_progress, 100f)
        showAnim = typedArray.getBoolean(R.styleable.ymyy_CirCleProgressBar_ymyy_show_anim, true)
        setCurrentProgress(currentProgress)
        setMaxProgress(maxProgress)
        typedArray.recycle()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val centre = width / 2 // 获取圆心的x坐标
        val radius = (centre - circleWidth / 2).toInt() - 2 // 圆环的半径
        drawCircle(canvas, centre, radius)
        drawProgressText(canvas, centre)
        drawBottomText(canvas, centre)
        drawScale(canvas, centre)
        invalidate()
    }

    fun setMaxProgress(maxProgress: Float) {
        require(maxProgress >= 0) { "max not less than 0" }
        this.maxProgress = maxProgress
        section = totalAngle / maxProgress
    }

    fun setCurrentProgress(progress: Float) {
        var progress = progress
        if (progress < 0) {
            return
        }
        currentProgress = progress
        if (progress > maxProgress) {
            progress = maxProgress
        }
        lastAngle = currentAngle
        if (showAnim) {
            setAnimation(lastAngle, progress * section, duration)
        } else {
            currentAngle = progress * section
        }
    }

    private fun setAnimation(last: Float, current: Float, duration: Int) {
        progressAnimator = ValueAnimator.ofFloat(last, current)
        progressAnimator?.setDuration(duration.toLong())
        progressAnimator?.setTarget(currentAngle)
        progressAnimator?.addUpdateListener(AnimatorUpdateListener { valueAnimator ->
            currentAngle = valueAnimator.animatedValue as Float
            currentProgress = currentAngle / section
        })
        progressAnimator?.start()
    }

    /**
     * @param isText 为true,自定义设置字体显示
     * @param text
     */
    fun setText(isText: Boolean, text: String?) {
        isDefaultText = isText
        mTextValue = text
    }

    /**
     * 绘制圆
     * @param canvas
     * @param centre
     * @param radius
     */
    private fun drawCircle(canvas: Canvas, centre: Int, radius: Int) {
        circlePaint.style = Paint.Style.STROKE
        circlePaint.isAntiAlias = true
        circlePaint.strokeCap = Paint.Cap.ROUND // 圆头
        circlePaint.strokeWidth = circleBgWidth
        /**
         * 背景圆环
         */
        circlePaint.color = circleBgColor
        circlePaint.shader = null
        val oval = RectF(
            (centre - radius - 1).toFloat(),
            (centre - radius - 1).toFloat(),
            (centre + radius + 1).toFloat(),
            (centre + radius + 1).toFloat()
        ) // 用于定义的圆弧的形状和大小的界限
        canvas.drawArc(oval, startAngle.toFloat(), totalAngle.toFloat(), false, circlePaint)
        /**
         * 进度圆环
         */
        circlePaint.strokeWidth = circleWidth
        if (circleStartColor != 0 && circleEndColor != 0) {
            val colors = intArrayOf(circleStartColor, circleEndColor)
            val linearGradient = LinearGradient(0f, 0f, width.toFloat(), 0f, colors, null, Shader.TileMode.CLAMP)
            circlePaint.shader = linearGradient
        } else {
            circlePaint.color = circleColor
        }
        canvas.drawArc(oval, startAngle.toFloat(), currentAngle, false, circlePaint)
    }

    /**
     * 绘制刻度
     * @param canvas
     * @param centre
     */
    private fun drawScale(canvas: Canvas, centre: Int) {
        scalePaint.color = scaleColor
        scalePaint.style = Paint.Style.STROKE
        scalePaint.isAntiAlias = true
        scalePaint.strokeWidth = scaleWidth.toFloat()
        val totalScale = totalAngle / 360f * 28
        val scaleNum = currentProgress / 100 * totalScale
        val initRotate = totalAngle / -2f + 2
        val eachDegrees = 360f / 28
        canvas.rotate(initRotate, centre.toFloat(), centre.toFloat())
        var i = 0
        while (i < scaleNum) {
            canvas.drawLine(
                centre.toFloat(),
                (circleWidth - scaleSize) / 2,
                centre.toFloat(),
                (circleWidth - scaleSize) / 2 + scaleSize,
                scalePaint
            )
            canvas.rotate(eachDegrees, centre.toFloat(), centre.toFloat())
            i++
        }
        canvas.save()
        canvas.restore()
    }

    /**
     * 绘制进度文案
     * @param canvas
     * @param centre
     */
    private fun drawProgressText(canvas: Canvas, centre: Int) {
        textPaint.isAntiAlias = true
        textPaint.color = textColor
        textPaint.textSize = textSize
        val textWidth: Float = textPaint.measureText(currentProgress.toInt().toString())
        if (!isDefaultText) {
            canvas.drawText(
                currentProgress.toInt().toString(),
                centre - textWidth / 2,
                centre + textSize / 4,
                textPaint
            )
        } else {
            canvas.drawText(mTextValue!!, centre - textWidth / 2, centre + textSize / 2, textPaint)
        }
    }

    /**
     * 绘制底部文案
     * @param canvas
     * @param centre
     */
    private fun drawBottomText(canvas: Canvas, centre: Int) {
        if (!TextUtils.isEmpty(bottomText)) {
            bottomTextPaint.isAntiAlias = true
            bottomTextPaint.color = bottomTextColor
            bottomTextPaint.textSize = bottomTextSize
            val bottomTextWidth = bottomTextPaint.measureText(bottomText)
            canvas.drawText(
                bottomText,
                centre - bottomTextWidth / 2,
                centre + textSize / 2 + dp8,
                bottomTextPaint
            )
        }
    }
}