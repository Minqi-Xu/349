import kotlin.math.pow

/*
 * Linear interpolation from start to end, using
 * time factor t (t should be in [0, 1])
 */
fun lerp(start: Double, end: Double, t: Double): Double {
    return start + (end - start) * t
}

/*
 * Easing functions
 */
val flip = { x: Double -> 1 - x }
val easeIn = { x: Double -> x.pow(2) }
val easeOut = { x: Double -> flip(easeIn(flip(x))) }
val easeInOut = { x: Double -> lerp(easeIn(x), easeOut(x), x) }

// Tweening class
class Tween (
    val startValue: Double,
    val endValue: Double,
    // time is in ms
    val time: Long,
    // function for changing easing (default is linear)
    val easing: (x: Double) -> Double = { x -> x }
) {
    // tweened value
    var value: Double = startValue
        private set

    // use null as the not running flag
    var startTime: Long? = null

    // start the tween
    fun start() {
        // get start time for this tween
        startTime = System.nanoTime()
        // initialize tweened value
        value = startValue
    }

    val isRunning: Boolean
        get() = (startTime != null)

     fun update(currentTime: Long) {
         // check if animation is running
         if (startTime != null) {

            // elapsed time (converted to milliseconds)
            val dt = (currentTime - (startTime ?: 0)) / 1e6
            // proportion of time completed
            val t = dt / time
            // interpolate value
            value = lerp(startValue, endValue, easing(t))

            // if animation ended
            if (dt > time) {
                value = endValue // snap to final value
                startTime = null
            }
         }
     }
}