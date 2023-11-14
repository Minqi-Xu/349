import javafx.application.Platform

class SubtaskModel(
   min: Int,
   max: Int
) : AbstractModel(min, max) {

   // Calculate a some primes on the UI thread. If necessary, schedule
   // ourselves to calculate more a little bit later.
   override fun calculatePrimes() {
      Platform.runLater {
            calculateSomePrimes(100)
            if (!cancelled && current <= max) {
               calculatePrimes()
            }
         }
   }

   // Calculate some prime numbers for duration ms.
   // Quit when we run out of time or we're
   // cancelled or we've reached the maximum prime to look for.
   private fun calculateSomePrimes(duration: Long) {
      val start = System.currentTimeMillis()
      while (true) {
         if (current > max) {
            isRunning = false
            notifyObservers()
            return
         } else if (System.currentTimeMillis() - start >= duration) {
            notifyObservers()
            return
         } else if (isPrime(current)) {
            addPrime(current)
         }
         current += 1
      }
   }
}