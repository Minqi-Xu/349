fun main() {
    val stmt = "Let's do some math!"
    println(stmt)
    println(underline(n=stmt.length))
    println("1+2=${sum(1,2)}")
}

// return string
fun underline(ch:String = "-", n:Int = 5): String {
    return ch.repeat(n)
}

// return int
fun sum(a: Int, b: Int): Int {
    return a + b
}

// return nothing (Unit)
fun printSum(a: Int, b: Int): Unit {
    println("sum of $a and $b is ${a + b}")
}
