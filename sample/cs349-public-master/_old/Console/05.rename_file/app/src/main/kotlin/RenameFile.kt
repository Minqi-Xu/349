import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 2) {
        println("Usage: java rename [file1] [file2]")
        exitProcess(0)
    }

    // get file references
    val file1 = File(args[0])
    val file2 = File(args[1])

    // check for errors
    if (!file1.exists()) {
        println("Error: $file1 does not exist")
        exitProcess(0)
    }
    if (file2.exists()) {
        println("Error: $file2 already exists")
        exitProcess(0)
    }
    val success = file1.renameTo(file2)
    if (success) {
        println("Successfully renamed $file1 to $file2")
    } else {
        println("Error renaming $file1 to $file2")
    }
}