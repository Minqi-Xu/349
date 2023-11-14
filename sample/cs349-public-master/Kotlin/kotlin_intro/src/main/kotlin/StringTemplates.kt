fun main() {
    var str: String = ""
    var thing: String = ""

    str = """abc""" // evaluates to "abc"
    thing = """\$str.length is""" // raw markers mean ignore esc, but string templates still work"
    println(thing + " ${str.length}") // \abc.length is 3

    str = """abc""" // evaluates to "abc"
    thing = "\$str.length is" // escape is processed in this case
    println(thing + " ${str.length}") // $str.length is 3
}