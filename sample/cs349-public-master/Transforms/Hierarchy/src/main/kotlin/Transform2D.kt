import javafx.geometry.Point2D
import javafx.scene.transform.Affine
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class Transform2D(var m11: Double = 1.0, var m12: Double = 0.0, var m13: Double = 0.0,
                  var m21: Double = 0.0, var m22: Double = 1.0, var m23: Double = 0.0,
                  var m31: Double = 0.0, var m32: Double = 0.0, var m33: Double = 1.0)
 {
    // post multiply point
    fun multiply(p: Point2D): Point2D {
        return Point2D( m11 * p.x + m12 * p.y + m13,
                        m21 * p.x + m22 * p.y + m23 )
    }

    // post multiply matrix
    fun multiply(t: Transform2D): Transform2D {

        return Transform2D(
            // row 1
            m11 * t.m11 + m12 * t.m21 + m13 * t.m31,
            m11 * t.m12 + m12 * t.m22 + m13 * t.m32,
            m11 * t.m13 + m12 * t.m23 + m13 * t.m33,
            // row 2
            m21 * t.m11 + m22 * t.m21 + m23 * t.m31,
            m21 * t.m12 + m22 * t.m22 + m23 * t.m32,
            m21 * t.m13 + m22 * t.m23 + m23 * t.m33,
            // row 3
            m31 * t.m11 + m32 * t.m21 + m33 * t.m31,
            m31 * t.m12 + m32 * t.m22 + m33 * t.m32,
            m31 * t.m13 + m32 * t.m23 + m33 * t.m33,
            )
    }

     // post multiply vector
     // (vectors should not be translated)
     fun multiplyAsVector(p: Point2D): Point2D {
         return Point2D( m11 * p.x + m12 * p.y,
             m21 * p.x + m22 * p.y )
     }

     fun set(m11: Double = 1.0, m12: Double = 0.0, m13: Double = 0.0,
             m21: Double = 0.0, m22: Double = 1.0, m23: Double = 0.0,
             m31: Double = 0.0, m32: Double = 0.0, m33: Double = 1.0) {
         // row 1
         this.m11 = m11
         this.m12 = m12
         this.m13 = m13
         // row 2
         this.m21 = m21
         this.m22 = m22
         this.m23 = m23
         // row 3
         this.m31 = m31
         this.m32 = m32
         this.m33 = m33
     }

     fun inverse(): Transform2D {
         // borrow JavaFX matrix inversion
         val t = Affine(m11, m12, m13, m21, m22, m23)
         t.invert()
         return Transform2D(
             t.mxx, t.mxy, t.tx,
             t.myx, t.myy, t.ty,
             0.0, 0.0, 1.0
         )
     }
}

// create standard transformation matrices

fun translate(tx: Double, ty: Double): Transform2D {
    return Transform2D(
        1.0,    0.0,    tx,
        0.0,    1.0,    ty,
        0.0,    0.0,    1.0,
    )
}

fun rotate(theta: Double): Transform2D {
    return Transform2D(
        cos(theta), -sin(theta),    0.0,
        sin(theta), cos(theta),     0.0,
        0.0,        0.0,            1.0,
    )
}

fun scale(sx: Double, sy: Double = sx): Transform2D {
    return Transform2D(
        sx,     0.0,    0.0,
        0.0,    sy,      0.0,
        0.0,    0.0,    1.0,
    )
}

fun rotateAroundPoint(theta: Double, x: Double, y: Double): Transform2D {
    TODO("not implemented")
    return Transform2D(
        cos(theta), -sin(theta),    0.0,
        sin(theta), cos(theta),     0.0,
        0.0,        0.0,            1.0,
    )
}

fun scaleAroundPoint(theta: Double, x: Double, y: Double): Transform2D {
    TODO("not implemented")
    return Transform2D(
        cos(theta), -sin(theta),    0.0,
        sin(theta), cos(theta),     0.0,
        0.0,        0.0,            1.0,
    )
}

fun radians(degrees: Double): Double {
    return degrees * (PI / 180.0)
}
