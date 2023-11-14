import java.text.DecimalFormat

fun main(args: Array<String>) {
  JavaInfo()
}

class JavaInfo {
  val formatter = DecimalFormat("###,###,###,###");
  val ANSI_BLACK = "\u001B[30m"
  val ANSI_YELLOW = "\u001B[33m"
  val ANSI_WHITE = "\u001B[37m"

  init {
    println()
    println(ANSI_WHITE + "  ;)(;  ")
    println(ANSI_YELLOW + " :----: " + ANSI_BLACK + " Vendor:   " + System.getProperty("java.vendor"))
    println(ANSI_YELLOW + "C|" + ANSI_WHITE + "====" + ANSI_YELLOW + "| " + ANSI_BLACK + " JDK Name: " + System.getProperty("java.vm.name"))
    println(ANSI_YELLOW + " |    | " + ANSI_BLACK + " Version:  " + System.getProperty("java.version"))
    println(ANSI_YELLOW + " `----' ")
    println()
  }
}