fun main() {
    var non_empty: String = "abc"
    println("string is ${non_empty.length} characters in length")

    var empty: String? = null
    println("string is ${empty?.length} characters in length")

//    println("string is ${empty?.length ?: 0} characters in length")

}