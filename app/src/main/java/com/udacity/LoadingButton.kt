package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.graphics.drawable.PaintDrawable
import android.util.AttributeSet
import android.view.View
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var arc = 0F
    private var circleAnimator = ValueAnimator()

    //private val valueAnimator = ValueAnimator()

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        when (new) {
            ButtonState.Loading -> {
                circleAnimator = ValueAnimator.ofFloat(0F, 360F).apply {
                    duration  = 1000
                    addUpdateListener { valueAnimator ->
                        arc = valueAnimator.animatedValue as Float
                        valueAnimator.repeatCount = ValueAnimator.INFINITE
                        //valueAnimator.repeatMode = ValueAnimator.REVERSE
                        //this@LoadingButton.invalidate()
                        invalidate()
                    }
                    start()
                }
            }
            ButtonState.Completed -> {
                circleAnimator.end()
                arc = 0F
                //this@LoadingButton.invalidate()
                invalidate()
                }

        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        //val il: Int = 12
        //color = setColor(il)
        textSize = 40.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }


    init {
        isClickable = true

    }

    override fun performClick(): Boolean {
        if (super.performClick()) return true

        //fanSpeed = fanSpeed.next()
        //contentDescription = "test"\
        //invalidate()
        //paint.color = Color.GREEN
        //refreshDrawableState()
        //canvas?.drawRect(100.0F,100.0F,30.0F,50.0F,paint)
        //canvas?.drawRect(width/2.toFloat(),height/2.toFloat(),width.toFloat(),height.toFloat(),paint)

        invalidate()
        return true
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //Change State based on current state, color, text etc
        paint.color = Color.BLACK
        //canvas?.drawRect(100.0F,100.0F,30.0F,50.0F,paint)
        //canvas?.drawRect(width/2.toFloat(),height/2.toFloat(),width.toFloat(),height.toFloat(),paint)
        canvas?.drawText("DOWNLOAD",0,8,width/2.toFloat(),height/3.75.toFloat(),paint)
        paint.color = Color.MAGENTA
        //canvas?.drawCircle(width/1.20.toFloat(),height/4.toFloat(),height/6.toFloat(),paint)
        //paint.color = Color.RED
        canvas?.drawArc(width/1.3.toFloat(),(height / 12.toFloat()) , width.toFloat(),
                height / 3.toFloat(), 0F,arc, true,paint)

        //paint.color = Color.YELLOW
        //canvas?.drawArc(width/1.2.toFloat(),height/4.toFloat(),width/1.6.toFloat(),height/3.toFloat(),0.toFloat(),360.toFloat(),true,paint)
        //contentDescription
        //canvas?.drawColor(30)


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