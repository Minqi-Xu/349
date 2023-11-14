package com.example.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/*
@JvmOverloads is kotlin shorthand for this:
class DrawingView: View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}
more info: https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/
*/

class DrawingView @JvmOverloads constructor(context: Context,
   attrs: AttributeSet? = null, defStyleAttr: Int = 0)
: View(context, attrs, defStyleAttr) {

   var brush: Paint =
      Paint().apply {
         isAntiAlias = true
         color = Color.BLUE
      }

   init {

   }

   var x = 0.0
   var y = 0.0

   override fun setOnTouchListener(l: OnTouchListener) {
      super.setOnTouchListener(l)
   }

      override fun onTouchEvent(event: MotionEvent): Boolean {


         when (event.action) {
            MotionEvent.ACTION_DOWN -> {
               invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
               x = event.x.toDouble()
               y = event.y.toDouble()
               invalidate()
            }
            MotionEvent.ACTION_UP -> {
               invalidate()
            }
         }
         return true
      }

      override fun onDraw(c: Canvas) {
         super.onDraw(c)
         val r = 100f
         c.drawOval(x.toFloat() - r, y.toFloat() - r,
            x.toFloat() + r, y.toFloat() + r, brush)
      }
   }
