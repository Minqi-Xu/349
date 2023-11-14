import java.io.File
import java.text.DecimalFormat

fun main() {
    Archey()
}

class Archey {
    private var formatter = DecimalFormat("###,###,###,###")
    private val ansi_red = "\u001B[31m"
    private val ansi_green = "\u001B[32m"
    private val ansi_yellow = "\u001B[33m"
    private val ansi_blue = "\u001B[34m"
    private val ansi_purple = "\u001B[35m"
    private val ansi_cyan = "\u001B[36m"

    init {
        val diskPartition = File("/")
        println("")
        println(ansi_red + "               ####                   User: " + System.getProperty("user.name"))
        println("               ###                    Home: " + System.getProperty("user.home"))
        println("       #######    #######             Name: " + java.net.InetAddress.getLocalHost().hostName)
        println(ansi_yellow + "     ######################           OS: " + System.getProperty("os.name"))
        println("    #####################             Version: " + System.getProperty("os.version"))
        println(ansi_green + "    ####################              CPU: " + System.getProperty("os.arch"))
        println("    ####################              Cores: " + Runtime.getRuntime().availableProcessors())
        println("$ansi_blue    #####################             Free Memory: " + formatter.format(
            Runtime.getRuntime().freeMemory() / (1024 * 1024)) + " GB")
        println("     ######################           Total Memory: " + formatter.format(
            Runtime.getRuntime().totalMemory() / (1024 * 1024)) + " GB")
        println(ansi_cyan + "      ####################            Disk Size: " + formatter.format(diskPartition.freeSpace / (1024 * 1024)) + " GB")
        println("        ################              Free Space: " + formatter.format(diskPartition.usableSpace / (1024 * 1024)) + " GB")
        println(ansi_purple + "         ####     #####               IP Address: " + java.net.InetAddress.getLocalHost().hostAddress)
        println("")
    }
}
