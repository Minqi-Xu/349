
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage

class Main : Application() {

   override fun start(stage: Stage) {

      // number of primes to calculate
      // 200000+ can take several seconds
      val num = 250000

      val view =
         when (1) {
            // Blocking Demo (BAD)
         1 -> View(BlockingModel(1, num))
            // SubTasks Demo (BETTER)
         2 ->  View(SubtaskModel(1, num))
            // Thread Demo (BEST)
         else -> View(ThreadModel(1, num))
      }

      val scene = Scene(view, 300.0, 300.0)
      stage.title = "LongTasks"
      stage.scene = scene
      stage.show()

      // for lecture demos
//      startOnScreen2(stage)
   }

   // for lecture demos
   // keeps application window on secondary display
   fun startOnScreen2(stage: Stage) {
      val w = Screen.getScreens()[1].getVisualBounds().maxX;
      stage.x = w - stage.scene.width - 32.0
      stage.y = 64.0
   }

}
