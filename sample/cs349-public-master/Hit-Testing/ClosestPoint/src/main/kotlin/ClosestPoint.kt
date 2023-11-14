import javafx.geometry.Point2D

// find closest point using projection method
// (segment flag useful for testing)
fun closestPoint(M: Point2D, P0: Point2D, P1: Point2D,
                 segmentOnly: Boolean = true): Point2D {

    val v = P1.subtract(P0) // v = P1 - P0

    // early out if line is less than 1 pixel long
    if (v.magnitude() < 1.0)
        return P0

    val u = M.subtract(P0) // u = M - P0

    // scalar of vector projection ...
    val s = u.dotProduct(v) / v.dotProduct(v)

    // for testing, useful to return point on infinite like
    if (!segmentOnly) {
        return P0.add(v.multiply(s))
    }

    // find point for constrained line segment
    if (s < 0)
        return P0
    else if (s > 1)
        return P1
    else {
        val w = v.multiply(s) // w = s * v
        return P0.add(w) // Q = P0 + w
    }
}



