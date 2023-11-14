fun main() {
    var a = 1

    println(
        when(a) {
            0, 1 -> "a is 0 or 1"
            1 -> "a is 1"
            2 -> "a is 2"
            else -> "a is really big"
        }
    )
}