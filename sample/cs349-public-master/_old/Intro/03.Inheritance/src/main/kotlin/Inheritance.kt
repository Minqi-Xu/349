fun main() {
    val kitten = Cat()
    speak(kitten)

    val puppy = Dog()
    speak(puppy)
}

fun speak(a: Pet) {
    a.talk()
}

// base class
open class Pet {
    open fun talk() {}

    init {
        println("Creating a pet")
    }
}

// derived classes
class Cat : Pet() {
    override fun talk() {
        println(" Meow!")
    }

    init {
        println("Creating a cat")
    }
}

class Dog : Pet() {
    override fun talk() {
        println(" Woof!")
    }

    init {
        println("Creating a dog")
    }
}
