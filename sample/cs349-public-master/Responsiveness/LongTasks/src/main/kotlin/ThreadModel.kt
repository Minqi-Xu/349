import AbstractModel
import javafx.application.Platform

class ThreadModel(
   min: Int,
   max: Int
) : AbstractModel(min, max) {

   override fun calculatePrimes() {

      // create a new thread and do task there ...
      object : Thread() {
         override fun run() {
            isRunning = true
            var start = System.currentTimeMillis()
            while (true) {
               if (cancelled || current > max) {
                  isRunning = false
                  updateAllViewsInUiThread()
                  return
               } else if (isPrime(current)) {
                  addPrime(current)
               }
               current += 1
               // update every 100 ms
               if (System.currentTimeMillis() - start >= 100) {
                  updateAllViewsInUiThread()
                  start = System.currentTimeMillis()
               }
            }
         }

         private fun updateAllViewsInUiThread() {
            Platform.runLater { notifyObservers() }
         }

      }.start()
   }
}