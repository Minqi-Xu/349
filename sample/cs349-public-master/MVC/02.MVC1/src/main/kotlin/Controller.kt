class Controller(
    var model: Model
    ) {

    fun handle() {
        println("Controller: changing Model (actionPerformed)")
        model.incrementCounter()
    }
}