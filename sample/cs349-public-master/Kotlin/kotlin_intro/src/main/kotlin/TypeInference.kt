fun main() {
    val name = "Jeff"
    displayType(name)

    val ch = 'J'
    displayType(ch)

    val num = 5
    displayType(num)

    val pi = 3.14159265359
    displayType(pi)

    val shortPi = 3.14159265359f
    displayType(shortPi)

    val shorterPi: Float = 3.1415f
    displayType(shorterPi)		// Float

    // this also works
    println("More precisely, $shorterPi is a ${shorterPi::class.qualifiedName}")
}

fun displayType(a: Int) = println("$a is an Int")
fun displayType(a: Float) = println("$a is a Float")
fun displayType(a: Double) = println("$a is a Double")
fun displayType(a: String) = println("$a is a String")
fun displayType(a: Char) = println("$a is a Char")
