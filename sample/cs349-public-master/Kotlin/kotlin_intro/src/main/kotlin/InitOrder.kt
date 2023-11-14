
class InitOrderDemo(name: String) {
    val first = name.split(" ").first()
    init {
        println("First: $first")
    }

    val surname = name.split(" ").last()
    init {
        println("Surname: $surname")
    }
}

fun main() {
    val a = InitOrderDemo("Billy Bishop")
}

// First init: Billy
// Second init: Bishop
