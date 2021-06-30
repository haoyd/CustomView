package com.example.customview.views.shapes;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import com.example.customview.R;

public class CirCleProgressBar extends View {

    private Paint circlePaint;
    private Paint scalePaint;
    private Paint textPaint;
    private Paint bottomTextPaint;

    private int circleColor;//圆弧颜色
    private int circleStartColor = 0;//圆弧渐变起始颜色
    private int circleEndColor = 0;//圆弧结束颜色
    private int circleBgColor;//圆弧背景颜色
    private float circleWidth;//圆弧宽度
    private float circleBgWidth;//圆弧背景宽度
    private int textColor;//字体颜色
    private float textSize;//字体大小
    private int totalAngle;//总角度
    private int startAngle;//开始角度
    private float currentProgress;//当前进度
    private float maxProgress;//最大进度
    private float section;//分段

    private float currentAngle;//当前角度
    private float lastAngle;
    private ValueAnimator progressAnimator;//圆弧动画
    private int duration = 1000;//动画时长
    private boolean isDefaultText;//是否设置文字显示的值
    private String mTextValue;//字体显示的值

    private int scaleSize = ConvertUtils.dp2px(4);
    private int scaleWidth = ConvertUtils.dp2px(1);
    private int scaleColor = Color.parseColor("#D67FDE");

    private String bottomText = "综合得分";
    private float bottomTextSize = ConvertUtils.dp2px(12);
    private int bottomTextColor = Color.parseColor("#AEAFB7");

    private int dp8 = ConvertUtils.dp2px(8);

    public CirCleProgressBar(Context context) {
        this(context, null);
    }

    public CirCleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirCleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        circlePaint = new Paint();
        textPaint = new Paint();
        bottomTextPaint = new Paint();
        scalePaint = new Paint();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ymyy_CirCleProgressBar);
        circleColor = typedArray.getColor(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_color, Color.RED);
        circleStartColor = typedArray.getColor(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_start_color, 0);
        circleEndColor = typedArray.getColor(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_end_color, 0);
        circleBgColor = typedArray.getColor(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_bg_color, Color.YELLOW);
        circleWidth = typedArray.getDimension(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_width, 2);
        circleBgWidth = typedArray.getDimension(R.styleable.ymyy_CirCleProgressBar_ymyy_circle_bg_width, 2);
        textColor = typedArray.getColor(R.styleable.ymyy_CirCleProgressBar_ymyy_text_color, Color.BLUE);
        textSize = typedArray.getDimension(R.styleable.ymyy_CirCleProgressBar_ymyy_text_size, 10);
        totalAngle = typedArray.getInteger(R.styleable.ymyy_CirCleProgressBar_ymyy_total_angle, 360);
        startAngle = typedArray.getInteger(R.styleable.ymyy_CirCleProgressBar_ymyy_start_angle, 0);
        currentProgress = typedArray.getFloat(R.styleable.ymyy_CirCleProgressBar_ymyy_current_progress, 0);
        maxProgress = typedArray.getFloat(R.styleable.ymyy_CirCleProgressBar_ymyy_max_progress, 100);
        setCurrentProgress(currentProgress);
        setMaxProgress(maxProgress);
        typedArray.recycle();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = (int) (centre - circleWidth / 2) - 2; // 圆环的半径

        drawCircle(canvas, centre, radius);
        drawProgressText(canvas, centre);
        drawBottomText(canvas, centre);
        drawScale(canvas, centre);
        invalidate();
    }

    public void setMaxProgress(float maxProgress) {
        if (maxProgress < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.maxProgress = maxProgress;
        section = totalAngle / maxProgress;
    }

    public void setCurrentProgress(float progress) {
        if (progress >= 0) {
            this.currentProgress = progress;
            if (progress > maxProgress) {
                progress = maxProgress;
            }
            lastAngle = currentAngle;
            setAnimation(lastAngle, progress * section, duration);
        }

    }

    private void setAnimation(float last, float current, int duration) {
        progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(duration);
        progressAnimator.setTarget(currentAngle);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentAngle = (float) valueAnimator.getAnimatedValue();
                currentProgress = currentAngle / section;
            }
        });
        progressAnimator.start();
    }

    /**
     * @param isText 为true,自定义设置字体显示
     * @param text
     */
    public void setText(boolean isText, String text) {
        isDefaultText = isText;
        mTextValue = text;
    }

    /**
     * 绘制圆
     * @param canvas
     * @param centre
     * @param radius
     */
    private void drawCircle(Canvas canvas, int centre, int radius) {
        circlePaint.setColor(circleBgColor);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);// 圆头
        circlePaint.setStrokeWidth(circleBgWidth);
        circlePaint.setShader(null);
        RectF oval = new RectF(centre - radius - 1, centre - radius - 1, centre + radius + 1, centre + radius + 1); // 用于定义的圆弧的形状和大小的界限
        //背景圆
        canvas.drawArc(oval, startAngle, totalAngle, false, circlePaint);
        //数据圆
        circlePaint.setStrokeWidth(circleWidth);

        if (circleStartColor != 0 && circleEndColor != 0) {
            int[] colors = new int[]{circleStartColor, circleEndColor};
            LinearGradient linearGradient = new LinearGradient(0, 0, getWidth(), 0, colors, null, LinearGradient.TileMode.CLAMP);
            circlePaint.setShader(linearGradient);
        } else {
            circlePaint.setColor(circleColor);
        }
        canvas.drawArc(oval, startAngle, currentAngle, false, circlePaint);
    }

    /**
     * 绘制刻度
     * @param canvas
     * @param centre
     */
    private void drawScale(Canvas canvas, int centre) {
        scalePaint.setColor(scaleColor);
        scalePaint.setStyle(Paint.Style.STROKE);
        scalePaint.setAntiAlias(true);
        scalePaint.setStrokeWidth(scaleWidth);

        float totalScale = totalAngle / 360f * 28;
        float scaleNum = currentProgress / 100 * totalScale;
        float initRotate = totalAngle / -2f + 2;
        float eachDegrees = 360f / 28;

        canvas.rotate(initRotate, centre, centre);
        for (int i = 0; i < scaleNum; i++) {
            canvas.drawLine(centre, (circleWidth - scaleSize) / 2, centre, (circleWidth - scaleSize) / 2 + scaleSize, scalePaint);
            canvas.rotate(eachDegrees, centre, centre);
        }
        canvas.save();
        canvas.restore();
    }

    /**
     * 绘制进度文案
     * @param canvas
     * @param centre
     */
    private void drawProgressText(Canvas canvas, int centre) {
        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        float textWidth = textPaint.measureText((int) currentProgress + "");
        if (!isDefaultText) {
            canvas.drawText(String.valueOf((int) currentProgress), centre - textWidth / 2, centre + textSize / 4, textPaint);
        } else {
            canvas.drawText(mTextValue, centre - textWidth / 2, centre + textSize / 2, textPaint);
        }
    }

    /**
     * 绘制底部文案
     * @param canvas
     * @param centre
     */
    private void drawBottomText(Canvas canvas, int centre) {
        if (!TextUtils.isEmpty(bottomText)) {
            bottomTextPaint.setAntiAlias(true);
            bottomTextPaint.setColor(bottomTextColor);
            bottomTextPaint.setTextSize(bottomTextSize);
            float bottomTextWidth = bottomTextPaint.measureText(bottomText);
            canvas.drawText(bottomText, centre - bottomTextWidth / 2, centre + textSize / 2 + dp8, bottomTextPaint);
        }
    }
}

