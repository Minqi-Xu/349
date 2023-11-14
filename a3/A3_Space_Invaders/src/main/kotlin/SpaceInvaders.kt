import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.stage.Stage
import kotlin.math.abs
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import java.io.File
import java.lang.Long.min
import java.util.*
import kotlin.collections.ArrayList

class SpaceInvaders : Application() {
    val playerSpeed = 4.0
    val bulletSpeed = 6.0
    val enemySpeed = 0.5
    var level: Int = 1
    val player = Player()
    val srcDir = "${System.getProperty("user.dir")}/src/main/resources"
    var score : Int = 0
    var restLifes : Int = 3
    var time1: Long = 0
    var time2: Long = 0
    var frameCount = 0
    var criticalAmount = 240
    

    var enemies = ArrayList<Enemy>()
    var enemyBullets = ArrayList<EnemyBullet>()
    var playerBullets = ArrayList<PlayerBullet>()

    var scene_start : Scene? = null
    var scene_game : Scene? = null
    var scene_lose : Scene? = null
    var scene_win : Scene? = null
//    val classLoader = Thread.currentThread().contextClassLoader

    enum class SCENES {
        START, SCENEGAME, LOSE, WIN
    }

    override fun start(stage: Stage?) {
        stage!!.title = "Space Invaders"
        player.view = Image(srcDir + "/images/player.png")

        // START
        val imageBorder = BorderPane()
        val blankBorder1 = BorderPane()
        val blankBorder2 = BorderPane()
        val blankBorder3 = BorderPane()
        val blankBorder4 = BorderPane()
        val blankBorder5 = BorderPane()
        val blankBorder6 = BorderPane()
        val blankBorder7 = BorderPane()
        val textBorder1 = BorderPane()
        val textBorder2 = BorderPane()
        val textBorder3 = BorderPane()
        val textBorder4 = BorderPane()
        val textBorder5 = BorderPane()
        val textBorder6 = BorderPane()
        val infoBorder = BorderPane()
        var lose_text3 = Text("")
        var win_text3 = Text("")
        val lose_text_Border3 = BorderPane()
        val win_text_Border3 = BorderPane()

        val image = ImageView(Image(srcDir+"/images/logo.png"))
        imageBorder.center = image
        val blank1 = Pane()
        blank1.prefHeight = 100.0
        blankBorder1.center = blank1
        val text1 = Text("Instructions")
        text1.style = "-fx-font: 40 arial;"
        textBorder1.center = text1
        val blank2 = Pane()
        blank2.prefHeight = 25.0
        blankBorder2.center = blank2
        val text2 = Text("ENTER - Start Game")
        text2.style = "-fx-font: 16 arial;"
        textBorder2.center = text2
        val blank3 = Pane()
        blank3.prefHeight = 12.0
        blankBorder3.center = blank3
        val text3 = Text("A, D - Move ship left or right")
        text3.style = "-fx-font: 16 arial;"
        textBorder3.center = text3
        val blank4 = Pane()
        blank4.prefHeight = 12.0
        blankBorder4.center = blank4
        val text4 = Text("SPACE - Fire!")
        text4.style = "-fx-font: 16 arial;"
        textBorder4.center = text4
        val blank5 = Pane()
        blank5.prefHeight = 12.0
        blankBorder5.center = blank5
        val text5 = Text("Q or Esc - Quit Game")
        text5.style = "-fx-font: 16 arial;"
        textBorder5.center = text5
        val blank6 = Pane()
        blank6.prefHeight = 12.0
        blankBorder6.center = blank6
        val text6 = Text("1 or 2 or 3 - Start Game at a specific level")
        text6.style = "-fx-font: 16 arial;"
        textBorder6.center = text6
        val blank7 = Pane()
        blank7.prefHeight = 50.0
        blankBorder7.center = blank7
        val vbox = VBox(imageBorder, blankBorder1, textBorder1, blankBorder2, textBorder2,
            blankBorder3, textBorder3, blankBorder4, textBorder4, blankBorder5, textBorder5,
            blankBorder6, textBorder6, blankBorder7)
        val root = BorderPane()
        root.center = vbox
        val info = Text("Produced by Minqi Xu, StudentID 20845758, QuestID m259xu")
        info.style = "-fx-font: 11 arial;"
        infoBorder.center = info
        root.bottom = infoBorder
        scene_start = Scene(root, 800.0, 600.0, Color.WHITE)

        // keyboard event to switch scene
        scene_start!!.onKeyPressed = EventHandler {event: KeyEvent ->
            if(event.code == KeyCode.ESCAPE) {
                Platform.exit()
            }
            if(event.code == KeyCode.Q) {
                Platform.exit()
            }
            if(event.code == KeyCode.ENTER) {
                init(1)
                restLifes = 3
                score = 0
                setScene(stage, SCENES.SCENEGAME)
            }
/*            if(event.code == KeyCode.L) {
                setScene(stage, SCENES.LOSE)
            }
            if(event.code == KeyCode.W) {
                setScene(stage, SCENES.WIN)
            }

 */
            if(event.code == KeyCode.DIGIT1) {
                init(1)
                restLifes = 3
                score = 0
                setScene(stage, SCENES.SCENEGAME)
            }
            if(event.code == KeyCode.DIGIT2) {
                init(2)
                restLifes = 3
                score = 0
                setScene(stage, SCENES.SCENEGAME)
            }
            if(event.code == KeyCode.DIGIT3) {
                init(3)
                restLifes = 3
                score = 0
                setScene(stage, SCENES.SCENEGAME)
            }
        }
        // end of START scene


        // SCENEGAME
        val gameroot = Group()
        val game = Canvas(800.0, 600.0)
        val gc = game.graphicsContext2D
        gameroot.children.add(game)
        val timer = object: AnimationTimer() {
            override fun handle(now: Long) {
                if(!(enemies.size == 0 && enemyBullets.size == 0 && playerBullets.size == 0)) {
                    gc.clearRect(0.0, 0.0, game.width, game.height)
                    autochange()
                    if(restLifes <= 0) {
                        lose_text3 = Text("Your Score: " + score)
                        lose_text3.style = "-fx-font: 24 arial;"
                        lose_text_Border3.center = lose_text3
                        setScene(stage, SCENES.LOSE)
                    }
                    else if(enemies.size == 0) {
                        level++
                        if(level >= 4) {
                            enemies.clear()
                            enemyBullets.clear()
                            playerBullets.clear()
                            win_text3 = Text("Your Score: " + score)
                            win_text3.style = "-fx-font: 24 arial;"
                            win_text_Border3.center = win_text3
                            setScene(stage, SCENES.WIN)
                        } else {
                            init(level)
                        }
                    }

                    draw(game)
                }

            }
        }
        timer.start()



        scene_game = Scene(gameroot, 800.0, 600.0, Color.BLACK)
        scene_game!!.onKeyPressed = EventHandler {event: KeyEvent ->
            if(event.code == KeyCode.ESCAPE) {
                enemies.clear()
                enemyBullets.clear()
                playerBullets.clear()
                setScene(stage, SCENES.START)
            }
            if(event.code == KeyCode.A) {
                player.x = player.x - playerSpeed
                if(player.x < 0) {
                    player.x = 0.0
                }
            }
            if(event.code == KeyCode.D) {
                player.x = player.x + playerSpeed
                if(player.x > 800 - player.w) {
                    player.x = 800 - player.w
                }
            }
            if(event.code == KeyCode.SPACE) {
                val tempT = System.nanoTime()
                val earlyT = min(time1, time2)
                if(tempT - earlyT >= 1000000000) {
                    if(earlyT == time1) {
                        time1 = tempT
                    } else {
                        time2 = tempT
                    }
                    val pb = PlayerBullet()
                    pb.x = player.x + player.w/2
                    pb.y = player.y + pb.h
                    pb.view = Image(srcDir+"/images/player_bullet.png")
                    playerBullets.add(pb)
                    MediaPlayer(Media(File(srcDir+"/sounds/shoot.wav").toURI().toString())).play()
                }
            }


        }
        // end of SCENEGAME


        // LOSE
        val lose_text = Text("YOU LOSE")
        lose_text.style = "-fx-font: 50 arial;"
        val lose_text_Border = BorderPane()
        lose_text_Border.center = lose_text
        val lose_blank = Pane()
        lose_blank.prefHeight = 150.0
        val lose_blank2 = Pane()
        lose_blank2.prefHeight = 30.0
        val lose_text2 = Text("press BACKSPACE to back to the start menu or ESC to quit")
        lose_text2.style = "-fx-font: 16 arial;"
        val lose_text_Border2 = BorderPane()
        lose_text_Border2.center = lose_text2
        val lose_blank3 = Pane()
        lose_blank3.prefHeight = 50.0
        lose_text3 = Text("Your Score: " + score)
        lose_text3.style = "-fx-font: 24 arial;"
        lose_text_Border3.center = lose_text3
        val lose_vbox = VBox(lose_blank, lose_text_Border, lose_blank2, lose_text_Border2, lose_blank3, lose_text_Border3)
        scene_lose = Scene(lose_vbox, 800.0, 600.0, Color.WHITE)

        scene_lose!!.onKeyPressed = EventHandler {event: KeyEvent ->
            if(event.code == KeyCode.BACK_SPACE) {
                setScene(stage, SCENES.START)
            }
            if(event.code == KeyCode.ESCAPE) {
                Platform.exit()
            }
            if(event.code == KeyCode.Q) {
                Platform.exit()
            }
        }
        //end of LOSE scene


        // WIN
        val win_text = Text("YOU WIN")
        win_text.style = "-fx-font: 50 arial;"
        val win_text_Border = BorderPane()
        win_text_Border.center = win_text
        val win_blank = Pane()
        win_blank.prefHeight = 150.0
        val win_blank2 = Pane()
        win_blank2.prefHeight = 30.0
        val win_text2 = Text("press BACKSPACE to back to the start menu or ESC to quit")
        win_text2.style = "-fx-font: 16 arial;"
        val win_text_Border2 = BorderPane()
        win_text_Border2.center = win_text2
        val win_blank3 = Pane()
        win_blank3.prefHeight = 50.0
        win_text3 = Text("Your Score: " + score)
        win_text3.style = "-fx-font: 24 arial;"
        win_text_Border3.center = win_text3
        val win_vbox = VBox(win_blank, win_text_Border, win_blank2, win_text_Border2, win_blank3, win_text_Border3)
        scene_win = Scene(win_vbox, 800.0, 600.0, Color.WHITE)

        scene_win!!.onKeyPressed = EventHandler {event: KeyEvent ->
            if(event.code == KeyCode.BACK_SPACE) {
                setScene(stage, SCENES.START)
            }
            if(event.code == KeyCode.ESCAPE) {
                Platform.exit()
            }
            if(event.code == KeyCode.Q) {
                Platform.exit()
            }
        }
        //end of WIN scene

        // show starting scene
        setScene(stage, SCENES.START)
        stage.isResizable = false
        stage.show()

    }

    private fun setScene(stage: Stage, scene : SCENES?) {
        when(scene) {
            SCENES.START -> {
                stage.scene = scene_start
            }
            SCENES.SCENEGAME -> {
                stage.scene = scene_game
            }
            SCENES.WIN -> {
                stage.scene = scene_win
            }
            SCENES.LOSE -> {
                stage.scene = scene_lose
            }
            else -> return
        }
    }

    private fun autochange() {
        var edgeFlag = false
        var currentES = enemySpeed + (level - 1) * 0.2
        for(pb in playerBullets) {
            var crashFlag = false
            pb.y = pb.y - bulletSpeed
            if(pb.y + pb.h <= 50) {
                playerBullets.remove(pb)
                crashFlag = true
            }
            if(crashFlag) {
                break
            }
        }

        for(eb in enemyBullets) {
            var crashFlag = false
            eb.y = eb.y + bulletSpeed
            for(pb in playerBullets) {
                if(((eb.x + eb.w) > pb.x) && (eb.x < (pb.x + pb.w)) && ((eb.y + eb.h) > pb.y) && (eb.y < (pb.y + pb.h))) {
                    //MediaPlayer(Media(classLoader.getResource(srcDir+"/sounds/explosion.wav")?.toString())).play()
                    crashFlag = true
                    MediaPlayer(Media(File(srcDir+"/sounds/explosion.wav").toURI().toString())).play()
                    enemyBullets.remove(eb)
                    playerBullets.remove(pb)
                    break
                }
            }
            if(eb.y >= 600) {
                enemyBullets.remove(eb)
                crashFlag = true
            }
            if(crashFlag) {
                break
            }
        }

        for(e in enemies) {
            var crashFlag = false
            if(e.moveLeft) {
                currentES = -abs(currentES)
            }
            e.x = e.x + currentES
            if(e.x <= 0 || e.x + e.w >= 800) {
                edgeFlag = true
                val rand: Int = Random().nextInt(4) + 1
                MediaPlayer(Media(File(srcDir+"/sounds/fastinvader"+rand+".wav").toURI().toString())).play()
            }
            for(pb in playerBullets) {
                if(((pb.x + pb.w) > e.x) && (pb.x < (e.x + e.w)) && (pb.y < (e.y + e.h)) && ((pb.y + pb.h) > e.y)) {
                    //MediaPlayer(Media(classLoader.getResource(srcDir+"/sounds/explosion.wav")?.toString())).play()
                    MediaPlayer(Media(File(srcDir+"/sounds/explosion.wav").toURI().toString())).play()
                    crashFlag = true
                    playerBullets.remove(pb)
                    enemies.remove(e)
                    score += 1000 * level
                    break
                }
            }
            if(crashFlag) {
                break
            }
        }
        if(enemies.size <= 0) {
            enemies.clear()
            enemyBullets.clear()
            playerBullets.clear()
            return
        }

        if(edgeFlag) {
            for(e in enemies) {
                e.moveLeft = !e.moveLeft
                e.y = e.y + e.h
                if(e.y + e.h >= 800) {
                    enemies.clear()
                    enemyBullets.clear()
                    playerBullets.clear()
                    restLifes = 0
                    return
                }
            }
        }
        frameCount++
        if(frameCount == criticalAmount) {
            frameCount = 0
            val eb = EnemyBullet()
            val lim = enemies.size
            val rand: Int = Random().nextInt(lim)
            eb.x = enemies[rand].x + enemies[rand].w/2
            var tempy = 0.0
            for(e in enemies) {
                if(e.y > tempy)
                    tempy = e.y
            }
            eb.y = tempy + enemies[rand].h
            val rand2: Int = Random().nextInt(3) + 1
            eb.view = Image(srcDir+"/images/bullet"+rand2+".png")
            enemyBullets.add(eb)
        }

        for(eb in enemyBullets) {
            var crashFlag = false
            if(eb.x + eb.w > player.x && eb.x < player.x + player.w && eb.y + eb.h > player.y && eb.y < player.y + player.h) {
                MediaPlayer(Media(File(srcDir+"/sounds/explosion.wav").toURI().toString())).play()
                crashFlag = true
                restLifes--
                enemyBullets.remove(eb)
                if(restLifes <= 0) {
                    // game over, player lose
                    enemies.clear()
                    enemyBullets.clear()
                    playerBullets.clear()
                }
                else {
                    while(true) {
                        val rand = Random().nextDouble(800 - player.w)
                        player.x = rand
                        var validFlag = true
                        for(eb in enemyBullets) {
                            if(eb.x + eb.w > player.x && eb.x < player.x + player.w &&
                                eb.y + eb.h > player.y && eb.y < player.y + player.h) {
                                validFlag = false
                                break
                            }
                        }
                        if(validFlag == false) {
                            continue
                        }
                        for(eb in enemies) {
                            if(eb.x + eb.w > player.x && eb.x < player.x + player.w &&
                                eb.y + eb.h > player.y && eb.y < player.y + player.h) {
                                validFlag = false
                                break
                            }
                        }
                        if(validFlag == false) {
                            continue
                        }
                        if(validFlag) {
                            break
                        }
                    }
                }
            }
            if(crashFlag) {
                break
            }
        }

        for(e in enemies) {
            var crashFlag = false
            if(e.x + e.w > player.x && e.x < player.x + player.w && e.y + e.h > player.y && e.y < player.y + player.h) {
                MediaPlayer(Media(File(srcDir+"/sounds/explosion.wav").toURI().toString())).play()
                crashFlag = true
                restLifes--
                enemies.remove(e)
                if(restLifes <= 0) {
                    // game over, player lose
                    enemies.clear()
                    enemyBullets.clear()
                    playerBullets.clear()
                }
                else {
                    while(true) {
                        val rand = Random().nextDouble(800 - player.w)
                        player.x = rand
                        var validFlag = true
                        for(eb in enemyBullets) {
                            if(eb.x + eb.w > player.x && eb.x < player.x + player.w &&
                                eb.y + eb.h > player.y && eb.y < player.y + player.h) {
                                validFlag = false
                                break
                            }
                        }
                        if(validFlag == false) {
                            continue
                        }
                        for(eb in enemies) {
                            if(eb.x + eb.w > player.x && eb.x < player.x + player.w &&
                                eb.y + eb.h > player.y && eb.y < player.y + player.h) {
                                validFlag = false
                                break
                            }
                        }
                        if(validFlag == false) {
                            continue
                        }
                        if(validFlag) {
                            break
                        }
                    }
                }
            }
            if(crashFlag) {
                break
            }
        }

    }

    private fun draw(c: Canvas) {
        val gc = c.graphicsContext2D
        for(e in enemies) {
            gc.drawImage(e.view, 0.0, 0.0, e.view!!.width, e.view!!.height, e.x, e.y, e.w, e.h)
        }
        gc.drawImage(player.view, 0.0, 0.0, player.view!!.width, player.view!!.height, player.x, player.y, player.w, player.h)
        for(eb in enemyBullets) {
            gc.drawImage(eb.view, 0.0, 0.0, eb.view!!.width, eb.view!!.height, eb.x, eb.y, eb.w, eb.h)
        }
        for(pb in playerBullets) {
            gc.drawImage(pb.view, 0.0, 0.0, pb.view!!.width, pb.view!!.height, pb.x, pb.y, pb.w, pb.h)
        }
        gc.fill = Color.WHITE
        gc.font = Font(24.0)
        gc.fillText("Score: " + score, 10.0, 22.0)
        gc.fillText("Lives: " + restLifes + "  Level: " + level, 600.0, 22.0)

    }

    private fun init(l: Int) {
        level = l
        frameCount = 0
        if(level == 1) {
            criticalAmount = 240
        } else if(level == 2) {
            criticalAmount = 180
        } else {
            criticalAmount = 100
        }
        enemies.clear()
        enemyBullets.clear()
        playerBullets.clear()
        for(i in 3 downTo 1) {
            for(j in 0..9) {
                val e = Enemy()
                e.x = j * e.w
                e.y = 50.0 + (3-i) * e.h
                e.view = Image(srcDir+"/images/enemy"+i+".png")
                e.moveLeft = false
                enemies.add(e)
            }
        }
        for(i in 3 downTo 2) {
            for(j in 0..9) {
                val e = Enemy()
                e.x = j * e.w
                e.y = 50.0 + (6-i) * e.h
                e.view = Image(srcDir+"/images/enemy"+i+".png")
                e.moveLeft = false
                enemies.add(e)
            }
        }
    }

}