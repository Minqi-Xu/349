interface Shape {
    fun dimensions(w: Double, h: Double)
    fun area(): Double
}

class Rectangle : Shape {
    var width: Double = 0.0
    var height: Double = 0.0

    override fun dimensions(w: Double, h: Double) { width = w; height = h }
    override fun area(): Double { return width * height }
}

fun main() {
    val rect = Rectangle()
    rect.height = 600.0
    rect.width = 800.0
    println("Area is ${rect.area()}")
}