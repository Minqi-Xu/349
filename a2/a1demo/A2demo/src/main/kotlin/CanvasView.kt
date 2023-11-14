import javafx.geometry.Point2D
import javafx.scene.canvas.Canvas
import javafx.scene.paint.Color
import java.lang.Math.pow
import kotlin.math.abs
import kotlin.math.pow

class CanvasView(val model: Model, w: Double, h: Double) : Canvas(), IView {

    var hitFlag = false

    fun closestPoint(M: Point2D, P0: Point2D, P1: Point2D, segmentOnly: Boolean = true): Point2D {
        val v = P1.subtract(P0)
        if(v.magnitude() < 1.0)
            return P0
        val u = M.subtract(P0)
        val s = u.dotProduct(v) / v.dotProduct(v)
        if(!segmentOnly) {
            return P0.add(v.multiply(s))
        }
        if(s < 0)
            return P0
        else if(s > 1)
            return P1
        else {
            val w = v.multiply(s)
            return P0.add(w)
        }
    }

    fun hittest(shape: MyShape, mx: Double, my: Double): Boolean {
        if(shape.type == "Circle") {
            val p = Point2D(shape.x1 + shape.w / 2, shape.y1 + shape.h / 2)
            val m = Point2D(mx, my)
            val dis = p.distance(m)
            if(dis < shape.w / 2 + shape.thick/2) {
                return true
            }
        } else if(shape.type == "Rectangle") {
            val s = shape.thick/2
            if(mx >= shape.x1 - s && mx <= shape.x1 + shape.w + s && my >= shape.y1 - s && my <= shape.y1 + shape.h + s) {
                return true
            }
        } else if(shape.type == "Line") {
            val m = Point2D(mx, my)
            val q = closestPoint(m, Point2D(shape.x1, shape.y1), Point2D(shape.x2, shape.y2))
            if(m.distance(q) <= shape.thick / 2) {
                return true
            }
        }
        return false;
    }

    init{
        // Use the graphics context to draw on a canvas
        var gc = this.graphicsContext2D
        this.width = w
        this.height = h
        gc.lineWidth = 5.0
        gc.stroke = Color.BLACK
        gc.fill = Color.YELLOW
        gc.strokeRect(75.0, 75.0, 100.0, 100.0)

        gc.fill = Color.AQUA
        gc.fillRect(100.0, 100.0, 100.0, 100.0)

        gc.fill = Color.YELLOWGREEN
        gc.fillRect(125.0, 125.0, 100.0, 100.0)

        this.setOnMouseClicked{
            println("Canvas clicked")
            if(model.selectedTool == "Select") {
                model.selecting = null
                for(sh in model.shapes) {
                    if(hittest(sh, it.x, it.y)) {
                        println("hit test success")
                        model.selecting = sh
                        model.selectedFillColor = sh.fillColor
                        model.selectedLineColor = sh.lineColor
                        model.notifyObservers()
                        break
                    }
                }
            } else if(model.selectedTool == "Erase") {
                for(sh in model.shapes) {
                    if(hittest(sh, it.x, it.y)) {
                        println("hit test success")
                        model.shapes.remove(sh)
                        model.notifyObservers()
                        break
                    }
                }
                model.selecting = null
            } else if(model.selectedTool == "Fill") {
                for(sh in model.shapes) {
                    if(hittest(sh, it.x, it.y)) {
                        println("hit test success")
                        sh.lineColor = model.selectedLineColor
                        sh.fillColor = model.selectedFillColor
                        sh.thick = model.selectedThick
                        sh.style = model.selectedStyle
                        model.notifyObservers()
                        model.selecting = sh
                        break
                    }
                }

            }
        }
        var startX = 0.0
        var startY = 0.0
        this.setOnMousePressed {
            startX = it.x
            startY = it.y
            if(model.selectedTool == "Select" && model.selecting != null && hittest(model.selecting!!, it.x, it.y)) {
                hitFlag = true;
            }
        }
        this.setOnMouseDragged {

            model.notifyObservers()

            if(model.selectedTool == "Circle") {
                val difx = abs(startX - it.x)
                val dify = abs(startY - it.y)
                var tempR = dify / 2
                if (difx > dify) {
                    tempR = difx / 2
                }

                var tempX = startX
                if(startX > it.x) {
                    tempX = startX - tempR * 2
                }
                var tempY = startY
                if(startY > it.y) {
                    tempY = startY - tempR * 2
                }

                gc = this.graphicsContext2D
                gc.fill = model.selectedFillColor
                gc.stroke = model.selectedLineColor
                gc.lineWidth = model.selectedThick.toDouble()
                gc.setLineDashes(model.selectedStyle.toDouble().pow(3.0) - 1)
                gc.strokeOval(tempX, tempY, 2*tempR, 2*tempR)
                gc.fillOval(tempX, tempY, 2*tempR, 2*tempR)
            } else if(model.selectedTool == "Rectangle") {
                val difx = abs(startX - it.x)
                val dify = abs(startY - it.y)
                var tempX = startX
                if(startX > it.x) {
                    tempX = it.x
                }
                var tempY = startY
                if(startY > it.y) {
                    tempY = it.y
                }
                gc = this.graphicsContext2D
                gc.fill = model.selectedFillColor
                gc.stroke = model.selectedLineColor
                gc.lineWidth = model.selectedThick.toDouble()
                gc.setLineDashes(model.selectedStyle.toDouble().pow(3.0) - 1)
                gc.strokeRect(tempX, tempY, difx, dify)
                gc.fillRect(tempX, tempY, difx, dify)
            } else if(model.selectedTool == "Line") {
                gc = this.graphicsContext2D
                gc.stroke = model.selectedLineColor
                gc.lineWidth = model.selectedThick.toDouble()
                gc.setLineDashes(model.selectedStyle.toDouble().pow(3.0) - 1)
                gc.strokeLine(startX, startY, it.x, it.y)
            } else if(model.selectedTool == "Select" && hitFlag == true) {
                val changx = it.x - startX
                val changy = it.y - startY
                val tempsh = MyShape()
                if(model.selecting!=null) {
                    tempsh.type = model.selecting!!.type
                    tempsh.x1 = model.selecting!!.x1
                    tempsh.y1 = model.selecting!!.y1
                    tempsh.x2 = model.selecting!!.x2
                    tempsh.y2 = model.selecting!!.y2
                    tempsh.w = model.selecting!!.w
                    tempsh.h = model.selecting!!.h
                    tempsh.lineColor = model.selecting!!.lineColor
                    tempsh.fillColor = model.selecting!!.fillColor
                    tempsh.thick = model.selecting!!.thick
                    tempsh.style = model.selecting!!.style

                }
                if(model.selecting != null) {
                    tempsh.x1 = model.selecting!!.x1 + changx
                    tempsh.y1 = model.selecting!!.y1 + changy
                    tempsh.x2 = model.selecting!!.x2 + changx
                    tempsh.y2 = model.selecting!!.y2 + changy
                    drawGC(tempsh)
                }
            }
        }
        this.setOnMouseReleased {
            model.notifyObservers()
            if(model.selectedTool == "Select" && hitFlag == true && model.selecting != null) {
                val changx = it.x - startX
                val changy = it.y - startY
                model.selecting!!.x1 += changx
                model.selecting!!.y1 += changy
                model.selecting!!.x2 += changx
                model.selecting!!.y2 += changy
                model.notifyObservers()
                hitFlag = false
            }
            if(model.selectedTool == "Circle") {
                val difx = abs(startX - it.x)
                val dify = abs(startY - it.y)
                var tempR = dify / 2
                if (difx > dify) {
                    tempR = difx / 2
                }
                var tempX = startX
                if(startX > it.x) {
                    tempX = startX - tempR * 2
                }
                var tempY = startY
                if(startY > it.y) {
                    tempY = startY - tempR * 2
                }
                gc = this.graphicsContext2D
                gc.fill = model.selectedFillColor
                gc.stroke = model.selectedLineColor
                gc.lineWidth = model.selectedThick.toDouble()
                gc.setLineDashes(model.selectedStyle.toDouble().pow(3.0) - 1)
                gc.strokeOval(tempX, tempY, 2*tempR, 2*tempR)
                gc.fillOval(tempX, tempY, 2*tempR, 2*tempR)
                val newShape = MyShape()
                newShape.type = "Circle"
                newShape.x1 = tempX
                newShape.y1 = tempY
                newShape.w = 2*tempR
                newShape.h = 2*tempR
                newShape.lineColor = model.selectedLineColor
                newShape.fillColor = model.selectedFillColor
                newShape.thick = model.selectedThick
                newShape.style = model.selectedStyle
                model.shapes.add(newShape)
            } else if(model.selectedTool == "Rectangle") {
                val difx = abs(startX - it.x)
                val dify = abs(startY - it.y)
                var tempX = startX
                if(startX > it.x) {
                    tempX = it.x
                }
                var tempY = startY
                if(startY > it.y) {
                    tempY = it.y
                }
                gc = this.graphicsContext2D
                gc.fill = model.selectedFillColor
                gc.stroke = model.selectedLineColor
                gc.lineWidth = model.selectedThick.toDouble()
                gc.setLineDashes(model.selectedStyle.toDouble().pow(3.0) - 1)
                gc.strokeRect(tempX, tempY, difx, dify)
                gc.fillRect(tempX, tempY, difx, dify)
                val newShape = MyShape()
                newShape.type = "Rectangle"
                newShape.x1 = tempX
                newShape.y1 = tempY
                newShape.w = difx
                newShape.h = dify
                newShape.lineColor = model.selectedLineColor
                newShape.fillColor = model.selectedFillColor
                newShape.thick = model.selectedThick
                newShape.style = model.selectedStyle
                model.shapes.add(newShape)
            } else if(model.selectedTool == "Line") {
                gc = this.graphicsContext2D
                gc.stroke = model.selectedLineColor
                gc.lineWidth = model.selectedThick.toDouble()
                gc.setLineDashes(model.selectedStyle.toDouble().pow(3.0) - 1)
                gc.strokeLine(startX, startY, it.x, it.y)
                val newShape = MyShape()
                newShape.type = "Line"
                newShape.x1 = startX
                newShape.y1 = startY
                newShape.x2 = it.x
                newShape.y2 = it.y
                newShape.lineColor = model.selectedLineColor
                newShape.fillColor = model.selectedFillColor
                newShape.thick = model.selectedThick
                newShape.style = model.selectedStyle
                model.shapes.add(newShape)
            }
        }

    }

    fun drawGC(sh: MyShape) {
        val gc = this.graphicsContext2D
        if(sh.type == "Circle") {
            gc.stroke = sh.lineColor
            gc.fill = sh.fillColor
            gc.lineWidth = sh.thick.toDouble()
            gc.setLineDashes(sh.style.toDouble().pow(3.0) - 1)
            gc.strokeOval(sh.x1, sh.y1, sh.w, sh.h)
            gc.fillOval(sh.x1, sh.y1, sh.w, sh.h)
        } else if(sh.type == "Rectangle") {
            gc.stroke = sh.lineColor
            gc.fill = sh.fillColor
            gc.lineWidth = sh.thick.toDouble()
            gc.setLineDashes(sh.style.toDouble().pow(3.0) - 1)
            gc.strokeRect(sh.x1, sh.y1, sh.w, sh.h)
            gc.fillRect(sh.x1, sh.y1, sh.w, sh.h)
        } else if(sh.type == "Line") {
            gc.stroke = sh.lineColor
            gc.lineWidth = sh.thick.toDouble()
            gc.setLineDashes(sh.style.toDouble().pow(3.0) - 1)
            gc.strokeLine(sh.x1, sh.y1, sh.x2, sh.y2)
        }
    }

    override fun updateView() {
        val gc = this.graphicsContext2D
        gc.clearRect(0.0, 0.0, this.width, height)
        for(sh in model.shapes) {
            drawGC(sh);
        }
    }
}