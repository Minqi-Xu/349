fun main(args: Array<String>) {
    ProgressBar()
}

class ProgressBar {
    private val esc = "\u001B" // escape code
    private val clr = "$esc[2J"
    private val left = "$esc[1D"
    private val right = "$esc[1C"
    private val up = "$esc[1A"
    private val down = "$esc[1B"
    private val startline = "$esc[250D"
    private val home = "$esc[H"
    private val block = "▋"

    init {
        for (i in 1..100) {
            print(startline)
            print(i.toString() + "% " + block.repeat(i))
            try {
                Thread.sleep(50)
            } catch (ex: Exception) {
                print(ex.toString())
            }
        }
        print("\n")
    }

    fun move(x: Int, y: Int) {
        print(esc + "[" + x + ";" + y + "H")
    }
}