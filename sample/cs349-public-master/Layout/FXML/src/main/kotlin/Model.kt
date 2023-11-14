class Model {
    private var value: String? = null
    fun setValue(s: String?) {
        value = s
        println("Set value to: $value")
    }

    fun getValue(): String? {
        return value
    }
}