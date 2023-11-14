import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

/*
* Triangle model
*
* @author Original code by Byron Weber Becker
* @author Refactored into Kotlin and JavaFX by Jeff Avery
*/

class Model {
    /* A list of the model's views. */
    private val views: ArrayList<IView> = ArrayList()

    // length of the base
    // Set the base to a new value. Must be between 0 and MAX_BASE.
    var base = 50.0f
        set(value) {
            val tmp: Float = max(0.0f, value)
            field = min(tmp, MAX_SIDE)
            updateAllViews() // update Views!
        }

    // height of the triangle
    // Set the height to a new value. Must be between 0 and MAX_HEIGHT.
    var height  = 50.0f
        set(value) {
            field = min(max(0.0f, value), MAX_SIDE).toFloat()
            updateAllViews() // update Views!
        }

    /** Get the length of this triangle's hypotenuse.  */
    val hypotenuse: Float
        get() = sqrt(base * base + height * height).toFloat()

    /** Add a new view of this triangle.  */
    fun addView(view: IView) {
        views.add(view)
        view.updateView() // update Views!
    }

    /** Remove a view from this triangle.  */
    fun removeView(view: IView) {
        views.remove(view)
    }

    /** Update all the views that are viewing this triangle.  */
    private fun updateAllViews() {
        for (view in views) {
            view.updateView()
        }
    }

    companion object {
        // Limit the size of the triangle.
        public val MAX_SIDE = 100.0f
        public val MAX_HYPO = sqrt(MAX_SIDE * MAX_SIDE + MAX_SIDE * MAX_SIDE)
    }
}