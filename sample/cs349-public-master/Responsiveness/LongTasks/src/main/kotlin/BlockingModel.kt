// Demo of blocking method for a long task
// DO NOT DO THIS!
class BlockingModel(
   min: Int,
   max: Int
) : AbstractModel(min, max) {

   // DO NOT DO THIS! This calculates all primes in a loop which
   // wil take a very long time. It will block the UI thread when
   // called from a UI event and make the UI unresponsive (aka "freeze")
   override fun calculatePrimes() {
      println("start")
      while (current <= max) {
         if (isPrime(current)) {
            addPrime(current)
         }
         current += 1
      }
      println(current)
      isRunning = false
      notifyObservers()
      println("done")
   }
}