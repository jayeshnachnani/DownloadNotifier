package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat.getColor
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var arc = 0F
    private var progress = 0
    private var rectangleAnimator = ValueAnimator()
    private var circleAnimator = ValueAnimator()


    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading -> {
                circleAnimator = ValueAnimator.ofFloat(0F, 360F).apply {
                    duration = 1000
                    addUpdateListener { valueAnimator ->
                        arc = valueAnimator.animatedValue as Float
                        valueAnimator.repeatCount = ValueAnimator.INFINITE
                        //this@LoadingButton.invalidate()
                        invalidate()
                    }
                    start()
                }
                rectangleAnimator = ValueAnimator.ofInt(0, width).apply {
                    duration = 1000
                    addUpdateListener { valueAnimator ->
                        progress = animatedValue as Int
                        valueAnimator.repeatCount = ValueAnimator.INFINITE
                        invalidate()
                    }
                    start()
                }
            }
            ButtonState.Completed -> {
                circleAnimator.end()
                rectangleAnimator.end()
                arc = 0F
                progress = 0
                //this@LoadingButton.invalidate()
                invalidate()
            }

        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 40.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }


    init {
        isClickable = true

    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true
        invalidate()
        return true
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //Change State based on current state, color, text etc
        paint.color = Color.GREEN
        canvas?.drawRect(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(), paint)

        paint.color = getColor(context, R.color.colorPrimaryDark)
        canvas?.drawRect(
                0f,
                0f,
                width.toFloat() * progress / 100,
                height.toFloat(), paint)


        paint.color = Color.BLACK
        canvas?.drawText("DOWNLOAD", 0, 8, width / 2.toFloat(), height / 3.75.toFloat(), paint)
        paint.color = Color.YELLOW
        canvas?.drawArc(width / 1.3.toFloat(), (height / 12.toFloat()), width.toFloat(),
                height / 3.toFloat(), 0F, arc, true, paint)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
                MeasureSpec.getSize(w),
                heightMeasureSpec,
                0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }

}