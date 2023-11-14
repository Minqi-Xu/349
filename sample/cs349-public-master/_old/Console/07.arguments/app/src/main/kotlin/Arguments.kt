import java.util.*

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        printHelp()
    } else {
        // extract options from command line
        // IntelliJ passes sample arguments if you run from the IDE (Run-Edit Configuration)
        println("Processing")
        val options = parse(args)

        // print them to verify that it worked
        println("\nResults")
        for ((key, value) in options) {
            println("$key:$value")
        }
    }
}

// Print command-line syntax
fun printHelp() {
    println("Usage: ProcessArgs [-arg val]")
}

// Build a dictionary of key:value pairs (without the leading "-")
fun parse(args: Array<String>): HashMap<String, String> {
    val arguments = HashMap<String, String>()

    // process each argument as either a key or value in the pair
    for (entry in args) {
        if (entry.startsWith("-") && entry.contains("=")) {
            val key = entry.split("=")[0].substring(1)
            val value = entry.split("=")[1]

            if (arguments.containsKey(key)) {
                println("Duplicate. Replacing $key")
            }
            println("Valid. Adding $entry")
            arguments[key] = value
        } else {
            println("Invalid. Skipping $entry")
        }
    }
    // return dictionary
    return arguments
}