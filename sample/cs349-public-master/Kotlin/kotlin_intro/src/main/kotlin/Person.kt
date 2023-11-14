
class Person(var first: String, var last: String) {
}

fun main() {
    val p = Person("Jeff", "Avery")
    println("${p.first} ${p.last}")
    p.first = "Bill"
}


