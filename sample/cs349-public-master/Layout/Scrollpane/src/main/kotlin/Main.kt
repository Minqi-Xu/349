import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.stage.Stage

// See the Oracle JavaFX scrollpane sample
// https://docs.oracle.com/javafx/2/ui_controls/scrollpane.htm

class Main : Application() {
    override fun start(stage: Stage) {

        val root = ScrollPane()

        // you can force the ScrollPane's use of scrollbars
//        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // change this string to run the different demos
        when ("Example 1") {

            "Example 1" -> {

                // ** Example 1 **
                // scrolling image as content
                val roses = Image("roses.jpg")
                root.content = ImageView(roses)
            }

            "Example 2" -> {

                // ** Example 2 **
                // a vertical scrollable list of something

                // flowpane gives us the wrapping
                val pane = FlowPane()
                pane.hgap = 10.0
                pane.vgap = 10.0

                // quickly create some things to flow
                repeat(100) {
                    val b = Button("$it")
                    b.prefWidth = 50.0
                    b.prefHeight = 40.0
                    pane.children.add(b)
                }

                // uncomment this to see what's going on with the FlowPane
                // it defaults to 400px wide, and the ScrollPane will
                // not adjust its size automatically
//                pane.background = Background(BackgroundFill(Color.RED, null, null))

                // put it in the scrollbar
                root.content = pane

                // for Example 2, it's important to tell the ScrollPane to adjust
                // the size of its content
                // (try commenting this out to see the problem)
                root.isFitToWidth = true
            }
        }

        stage.scene = Scene(root, 400.0, 300.0)
        stage.show()
    }
}