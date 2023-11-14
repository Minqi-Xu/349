
fun main() {
    // list itself is immutable (due to listOf)
    var fruits = listOf("avocado", "banana", "cantaloupe")
    println("LIST")
    println(fruits.get(0))
    println(fruits[1])
//    fruits.add("dragon fruit") // error

//     list itself is mutable (due to mutableListOf)
//    println("\nMUTABLE LIST")
//    val mutableFruits = mutableListOf("dragonfruit", "elderberry")
//
//    mutableFruits.add("cantaloupe")
//    println(mutableFruits.first())
//    println(mutableFruits.last())
//    mutableFruits.add("fig")
//    println(mutableFruits.last())


    // immutable pair
//    val p = Pair("A", "Apple")
//    p.first = "B"
//    p[0] = "B"

//    val p = Pair("Calvin", "Hobbes")
//

//    val mmap = mutableMapOf(1 to "a", 2 to "b", 3 to "c", 4 to "d", 5 to "e", 6 to "f")
//    mmap.put(7, "g")
//    println(mmap)
//    val result = mmap.get(0)
//
//    // iterate over map
//    for ((k, v) in mmap) {
//        println("$k = $v")
//    }
}
