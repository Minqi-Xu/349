fun main() {
    val fooPrimary = Foo(5)
    println(fooPrimary.x)

    val fooSecondary = Foo(x=2, y=3)
    println(fooSecondary.x)
    println(fooSecondary.y)

    // Second form
    val barPrimary = Bar(5)
    println(barPrimary.x)

    val barSecondary = Bar(2, 3)
    println(barSecondary.x)
    println(barSecondary.y)

    val barEmpty = Bar()
    println(barEmpty.x)
    println(barEmpty.y)
}

// (var x: Int) creates a public property of the same name
class Foo(var x: Int) {
    var y = 0
    // you cannot do the same thing with secondary constructors
    // delegate to the primary ctor and define all fields there
    constructor(x: Int, y: Int): this(x) {
        this.y = y
    }
}

// Remember that Kotlin has default values for arguments!
// You can use this feature to achieve the same results as overloading constructors
class Bar(var x: Int = 2, var y: Int = 3) {
}
