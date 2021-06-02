package com.example.customview.views

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.example.customview.R
import com.example.customview.utils.TextDrawUtils
import kotlin.math.cos
import kotlin.math.sin

class LetterIndexView : View {

    var mListener: OnLetterChangeListener? = null

    private lateinit var mLetters: Array<String>

    private lateinit var mSlideBarRect: RectF
    private lateinit var mTextPaint: TextPaint
    private lateinit var mPaint: Paint
    private lateinit var mSelectBgPaint: Paint
    private lateinit var mWavePaint: Paint
    private lateinit var mWavePath: Path

    private val mBarWidth = ConvertUtils.dp2px(20f)
    private val mBarPadding = ConvertUtils.dp2px(10f)
    private val mContentPadding = ConvertUtils.dp2px(5f)

    private val mBackgroundColor = Color.parseColor("#F9F9F9")
    private val mStrokeColor = Color.parseColor("#000000")
    private val mTextColor = Color.parseColor("#000000")
    private val mTextSize = ConvertUtils.dp2px(13f)

    private var mTouchY = 0
    private val mWaveRadius = ConvertUtils.dp2px(20f)
    private var mRatioAnimator: ValueAnimator? = null
    private var mAnimationRatio = 0f

    private var mSelect = 0
    private var mPreSelect = 0
    private var mNewSelect = 0

    private var mHintCircleRadius = ConvertUtils.dp2px(24f)
    private var mHintCircleColor = Color.parseColor("#bef9b81b")

    private var mHintTextSize = ConvertUtils.dp2px(16f)
    private val mHintTextColor = Color.parseColor("#FFFFFF")

    private var mSelectTextSize = ConvertUtils.dp2px(13f)
    private val mSelectTextColor = Color.parseColor("#FFFFFF")

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
        val contentLeft: Float = (measuredWidth - mBarWidth - mBarPadding).toFloat()
        val contentRight: Float = (measuredWidth - mBarPadding).toFloat()
        val contentTop: Float = mBarPadding.toFloat()
        val contentBottom = measuredHeight - mBarPadding
        mSlideBarRect.set(contentLeft, contentTop, contentRight, contentBottom.toFloat())
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawLetters(canvas!!)   // 字母
        drawHint(canvas)        // 圆 + 文字
        drawSelect(canvas)      // bar 上选中的圆
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val y = event.y
        val x = event.x
        mPreSelect = mSelect
        mNewSelect = (y / (mSlideBarRect.bottom - mSlideBarRect.top) * mLetters.size).toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                //保证down的时候在bar区域才相应事件
                if (x < mSlideBarRect.left || y < mSlideBarRect.top || y > mSlideBarRect.bottom) {
                    return false
                }
                mTouchY = y.toInt()
                startAnimator(1f)
            }
            MotionEvent.ACTION_MOVE -> {
                mTouchY = y.toInt()
                if (mPreSelect != mNewSelect && mNewSelect >= 0 && mNewSelect < mLetters.size) {
                    mSelect = mNewSelect
                    mListener?.onLetterChange(mLetters[mNewSelect])
                }
                invalidate()
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> startAnimator(0f)
            else -> {
            }
        }
        return true
    }

    private fun init() {
        mLetters = resources.getStringArray(R.array.english_letters)
        mSlideBarRect = RectF()
        mTextPaint = TextPaint()
        mPaint = Paint()
        mSelectBgPaint = Paint()
        mWavePaint = Paint()
        mWavePath = Path()

        mPaint.isAntiAlias = true
        mSelectBgPaint.isAntiAlias = true
        mWavePaint.isAntiAlias = true
    }

    /**
     * 绘制slide bar 上字母列表
     */
    private fun drawLetters(canvas: Canvas) {
        //绘制圆角矩形
        mPaint.style = Paint.Style.FILL
        mPaint.color = mBackgroundColor
        canvas.drawRoundRect(mSlideBarRect, mBarWidth / 2.0f, mBarWidth / 2.0f, mPaint)
        //绘制描边
        mPaint.style = Paint.Style.STROKE
        mPaint.color = mStrokeColor
        canvas.drawRoundRect(mSlideBarRect, mBarWidth / 2.0f, mBarWidth / 2.0f, mPaint)

        //顺序绘制文字
        val itemHeight: Float = (mSlideBarRect.bottom - mSlideBarRect.top - mContentPadding * 2) / mLetters.size
        val pointX = mSlideBarRect.left + (mSlideBarRect.right - mSlideBarRect.left) / 2.0f
        mTextPaint.color = mTextColor
        mTextPaint.textSize = mTextSize.toFloat()
        mTextPaint.textAlign = Paint.Align.CENTER
        for (index in mLetters.indices) {
            val center = mSlideBarRect.top + mContentPadding + itemHeight * index + itemHeight / 2
            val baseLine: Float = TextDrawUtils.getTextBaseLineByCenter(
                center,
                mTextPaint,
                mTextSize
            )
            canvas.drawText(mLetters[index], pointX, baseLine, mTextPaint)
        }
    }

    /**
     * 绘制选中的slide bar上的那个文字
     */
    private fun drawHint(canvas: Canvas) {
        //x轴的移动路径
        val circleCenterX: Float =
            measuredWidth + mHintCircleRadius - (2.0f * mWaveRadius + 2.0f * mHintCircleRadius) * mAnimationRatio
        var centerY = mTouchY.toFloat()
        if (centerY < mHintCircleRadius) {
            centerY = mHintCircleRadius.toFloat()
        } else if (centerY > mSlideBarRect.bottom - mHintCircleRadius * 1.2f) {
            centerY = mSlideBarRect.bottom - mHintCircleRadius * 1.2f
        }
        mWavePaint.style = Paint.Style.FILL
        mWavePaint.color = mHintCircleColor
        canvas.drawCircle(circleCenterX, centerY, mHintCircleRadius.toFloat(), mWavePaint)
        // 绘制提示字符
        if (mAnimationRatio >= 0.9f && mSelect != -1) {
            val target = mLetters[mSelect]
            val textY = TextDrawUtils.getTextBaseLineByCenter(centerY, mTextPaint, mHintTextSize)
            mTextPaint.color = mHintTextColor
            mTextPaint.textSize = mHintTextSize.toFloat()
            mTextPaint.textAlign = Paint.Align.CENTER
            canvas.drawText(target, circleCenterX, textY, mTextPaint)
        }
    }

    /**
     * 绘制选中时的提示信息(圆＋文字)
     */
    private fun drawSelect(canvas: Canvas) {
        if (mSelect != -1) {
            mTextPaint.color = mSelectTextColor
            mTextPaint.textSize = mSelectTextSize.toFloat()
            mTextPaint.textAlign = Paint.Align.CENTER
            val itemHeight =
                (mSlideBarRect.bottom - mSlideBarRect.top - mContentPadding * 2) / mLetters.size
            val centerY =
                mSlideBarRect.top + mContentPadding + itemHeight * mSelect + itemHeight / 2
            val baseLine = TextDrawUtils.getTextBaseLineByCenter(centerY, mTextPaint, mTextSize)
            val pointX = mSlideBarRect.left + (mSlideBarRect.right - mSlideBarRect.left) / 2.0f
            mSelectBgPaint.style = Paint.Style.FILL
            mSelectBgPaint.color = Color.parseColor("#FFDCE7")
            canvas.drawCircle(pointX, centerY, itemHeight / 2, mSelectBgPaint)
            canvas.drawText(mLetters[mSelect], pointX, baseLine, mTextPaint)
        }
    }

    private fun startAnimator(value: Float) {
        if (mRatioAnimator == null) {
            mRatioAnimator = ValueAnimator()
        }
        mRatioAnimator!!.cancel()
        mRatioAnimator!!.setFloatValues(value)
        mRatioAnimator!!.addUpdateListener { value ->
            mAnimationRatio = value.animatedValue as Float
            //球弹到位的时候，并且点击的位置变了，即点击的时候显示当前选择位置
            if (mAnimationRatio == 1f && mPreSelect != mNewSelect) {
                if (mNewSelect >= 0 && mNewSelect < mLetters.size) {
                    mSelect = mNewSelect
                    mListener?.onLetterChange(mLetters[mNewSelect])
                }
            }
            invalidate()
        }
        mRatioAnimator!!.start()
    }

    interface OnLetterChangeListener {
        fun onLetterChange(letter: String?)
    }
}