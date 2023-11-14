package net.codebot.pdfviewer

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import java.lang.Double.max
import java.lang.Double.min

@SuppressLint("AppCompatCustomView")
class PDFimage  // constructor
    (context: Context?, info: infos) : ImageView(context) {
    val LOGNAME = "pdf_image"

    // drawing path
    var path: Path? = null
    var points = ArrayList<p>()

    // image to display
    var bitmap: Bitmap? = null
    var paint = Paint(Color.BLUE)

    val info = info

    // the code for zoom and pan are refered to the sample demo in the git public repo
    var x1 = 0f
    var x2 = 0f
    var y1 = 0f
    var y2 = 0f
    var old_x1 = 0f
    var old_y1 = 0f
    var old_x2 = 0f
    var old_y2 = 0f
    var mid_x = -1f
    var mid_y = -1f
    var old_mid_x = -1f
    var old_mid_y = -1f
    var p1_id = 0
    var p1_index = 0
    var p2_id = 0
    var p2_index = 0

    var currentMatrix = Matrix()
    var inverse = Matrix()



    // capture touch events (down/move/up) to create a path
    // and use that to create a stroke that we can draw
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var inverted = floatArrayOf()
        when (event.pointerCount) {
            1-> {
                p1_id = event.getPointerId(0)
                p1_index = event.findPointerIndex(p1_id)

                inverse = Matrix()
                currentMatrix.invert(inverse)

                inverted = floatArrayOf(event.getX(p1_index), event.getY(p1_index))
                inverse.mapPoints(inverted)

                x1 = inverted[0]
                y1 = inverted[1]

                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        points.clear()
                        if (info.selectedTool == 1 || info.selectedTool == 2 || info.selectedTool == 3) {
                            Log.d(LOGNAME, "Action down")
                            val tempdraw = drawings()
                            path = Path()
                            tempdraw.shape = path
                            tempdraw.type = info.selectedTool
                            if (info.selectedTool == 3) {
                                tempdraw.visible = 0
                            } else {
                                tempdraw.visible = 1
                            }
                            tempdraw.shape!!.moveTo(x1, y1)
                            val tempp = p()
                            tempp.x = x1.toDouble()
                            tempp.y = y1.toDouble()
                            tempdraw.points.add(tempp)
                            info.undo_stack[info.current_page].add(tempdraw)
                        }

                    }
                    MotionEvent.ACTION_MOVE -> {
                        if (info.selectedTool == 1 || info.selectedTool == 2 || info.selectedTool == 3) {
                            Log.d(LOGNAME, "Action move")
                            info.undo_stack[info.current_page].last().shape!!.lineTo(x1, y1)
                            val tempp = p()
                            tempp.x = x1.toDouble()
                            tempp.y = y1.toDouble()
                            info.undo_stack[info.current_page].last().points.add(tempp)
                        }

                    }
                    MotionEvent.ACTION_UP -> {
                        if (info.selectedTool == 1) {
                            Log.d(LOGNAME, "Action up")
                            info.redo_stack[info.current_page].clear()
                        } else if (info.selectedTool == 2) {
                            Log.d(LOGNAME, "Action up")
                            info.redo_stack[info.current_page].clear()
                        } else if (info.selectedTool == 3) {
                            Log.d(LOGNAME, "Action up")
                            var isErase = false
                            for (i in 0..info.undo_stack[info.current_page].size - 2) {
                                if (info.undo_stack[info.current_page][i].type != 3 &&
                                    checkcross(
                                        info.undo_stack[info.current_page].last(),
                                        info.undo_stack[info.current_page][i]
                                    )
                                ) {
                                    isErase = true
                                    info.undo_stack[info.current_page].last().erase_index.add(i)
                                    Log.d(LOGNAME, "i=" + i + " erased")
                                    info.undo_stack[info.current_page][i].visible = 0
                                }
                            }
                            if (!isErase) {
                                info.undo_stack[info.current_page].removeLast()
                            } else {
                                info.redo_stack[info.current_page].clear()
                            }
                        }
                        points.clear()
                    }
                }
            }
            2->{
                // point 1
                p1_id = event.getPointerId(0)
                p1_index = event.findPointerIndex(p1_id)

                // mapPoints returns values in-place
                inverted = floatArrayOf(event.getX(p1_index), event.getY(p1_index))
                inverse.mapPoints(inverted)

                // first pass, initialize the old == current value
                if(old_x1 < 0 || old_y1 < 0) {
                    x1 = inverted.get(0)
                    old_x1 = x1
                    y1 = inverted.get(1)
                    old_y1 = y1
                } else {
                    old_x1 = x1
                    old_y1 = y1
                    x1 = inverted.get(0)
                    y1 = inverted.get(1)
                }

                // point 2
                p2_id = event.getPointerId(1)
                p2_index = event.findPointerIndex(p2_id)

                // mapPoints returns values in-place
                inverted = floatArrayOf(event.getX(p2_index), event.getY(p2_index))
                inverse.mapPoints(inverted)

                // first pass, initialize the old == current value
                if(old_x2 < 0 || old_y2 < 0) {
                    x2 = inverted.get(0)
                    old_x2 = x2
                    y2 = inverted.get(1)
                    old_y2 = y2
                } else {
                    old_x2 = x2
                    old_y2 = y2
                    x2 = inverted.get(0)
                    y2 = inverted.get(1)
                }

                // midpoint
                mid_x = (x1+x2)/2
                mid_y = (y1+y2)/2
                old_mid_x = (old_x1+old_x2)/2
                old_mid_y = (old_y1+old_y2)/2

                // distance
                val d_old =
                    Math.sqrt(Math.pow((old_x1 - old_x2).toDouble(), 2.0) + Math.pow((old_y1 - old_y2).toDouble(), 2.0))
                        .toFloat()
                val d = Math.sqrt(Math.pow((x1 - x2).toDouble(), 2.0) + Math.pow((y1 - y2).toDouble(), 2.0))
                    .toFloat()


                // pan and zoom during MOVE event
                if (event.action == MotionEvent.ACTION_MOVE) {
                    Log.d(LOGNAME, "Multitouch move")
                    // pan == translate of midpoint
                    val dx = mid_x - old_mid_x
                    val dy = mid_y - old_mid_y
                    currentMatrix.preTranslate(dx, dy)
                    Log.d(LOGNAME, "translate: $dx,$dy")

                    // zoom == change of spread between p1 and p2
                    var scale = d / d_old
                    scale = Math.max(0f, scale)
                    currentMatrix.preScale(scale, scale, mid_x, mid_y)
                    Log.d(LOGNAME, "scale: $scale")

                    // reset on up
                } else if (event.action == MotionEvent.ACTION_UP) {
                    old_x1 = -1f
                    old_y1 = -1f
                    old_x2 = -1f
                    old_y2 = -1f
                    old_mid_x = -1f
                    old_mid_y = -1f
                }

            }
            else -> {}
        }
        return true
    }

    // set image as background
    fun setImage(bitmap: Bitmap?) {
        this.bitmap = bitmap
    }

    // set brush characteristics
    // e.g. color, thickness, alpha
    fun setBrush(paint: Paint) {
        this.paint = paint
    }

    fun checkcross(draw1: drawings, draw2: drawings): Boolean {
        if(draw2.visible == 0) {
            return false
        }
        for(i in 0..draw1.points.size-2) {
            for(j in 0..draw2.points.size-2) {
                val x1 = draw1.points[i].x
                val y1 = draw1.points[i].y
                val x2 = draw1.points[i+1].x
                val y2 = draw1.points[i+1].y
                val a1 = draw2.points[j].x
                val b1 = draw2.points[j].y
                val a2 = draw2.points[j+1].x
                val b2 = draw2.points[j+1].y
                val k1 = (y1-y2)/(x1-x2)
                val b_1 = y1 - k1*x1
                val k2 = (b1-b2)/(a1-a2)
                val b_2 = b1 - k2*a1
                val intx = (b_2 - b_1)/(k1-k2)
                if(intx >= min(x1,x2) - 1.25 && intx <= max(x1,x2) + 1.25 && intx >= min(a1,a2) - 1.25 && intx <= max(a1,a2) + 1.25) {

                    return true
                }
            }
        }
        return false
    }

    override fun onDraw(canvas: Canvas) {
        canvas.concat(currentMatrix)

        // draw background
        if (bitmap != null) {
            setImageBitmap(bitmap)
        }
        // draw lines over it
        for (d in info.undo_stack[info.current_page]) {
            if(d.type == 1 && d.visible == 1) {
                paint.style = Paint.Style.STROKE
                paint.color = Color.BLACK
                paint.strokeWidth = 2.0F
                paint.setAlpha(255)
                canvas.drawPath(d.shape!!, paint)
                /*
                for(i in 0..d.points.size-2) {
                    canvas.drawLine(d.points[i].x.toFloat(),d.points[i].y.toFloat(),d.points[i+1].x.toFloat(), d.points[i+1].y.toFloat(), paint)
                }*/
            } else if(d.type == 2 && d.visible == 1) {
                paint.style = Paint.Style.STROKE
                paint.color = Color.YELLOW
                paint.strokeWidth = 15.0F
                paint.setAlpha(140)
                canvas.drawPath(d.shape!!, paint)

            }
        }
        super.onDraw(canvas)
    }
}