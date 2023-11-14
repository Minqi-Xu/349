
import java.util.*

/**
 * AbstractPrimesModel contains the infrastructure for calculating
 * a set of primes, including the code to support MVC.  Subclasses illustrate
 * the right and wrong ways to support a long task in an interactive
 * application.
 *
 * @author bwbecker
 * @date 22-Mar-2012 factored out common code
 *
 * @author j2avery
 * @date 30-Jun-2021 ported to Kotlin, refactored into separate classes and projects.
 */

// MVC base Model to calculate all the prime numbers between min and max
abstract class AbstractModel(
    protected var min: Int,
    protected var max: Int
    ) {

    //region View Management

    // all views of this model
    private val views: ArrayList<IView> = ArrayList()

    fun addView(view: IView) {
        views.add(view)
        view.update()
    }

    fun removeView(view: IView) {
        views.remove(view)
    }

    // Update all views/observers with details about a change to the model
    @Synchronized
    protected fun notifyObservers() {
        for (view in views) {
            view.update()
        }
    }

    //endregion

    var primes = Vector<Int>()

    protected var cancelled = false
    protected var current : Int = min
    var isRunning = false

    fun start() {
        cancelled = false
        current = min
        isRunning = false
        primes.clear()
        notifyObservers()
        // now calculate primes
        calculatePrimes()
    }

    fun cancel() {
        cancelled = true
    }

    val progress: Double
        get() = current.toDouble() / (max - min)


    // demos will use different implementations to show
    // problems and solutions for long tasks
    abstract fun calculatePrimes()

    @Synchronized
    protected fun addPrime(i: Int) {
        primes.add(i)
    }

    // Determine whether n is prime.
    // For the purposes of a long-running task demo,
    // we DO NOT want to optimize this!
    protected fun isPrime(n: Int): Boolean {
        if (n == 1) return false
        for (i in 2 until n) {
            if (n % i == 0) return false
        }
        return true
    }

}