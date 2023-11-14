fun main() {
    val items1 = listOf("apple", "banana", "kiwifruit")
    for (item in items1) {
        println(item)
    }

    val items2 = listOf("apple", "banana", "kiwifruit")
    for (index in items2.indices) {
        println("item at $index is ${items2[index]}")
    }
}