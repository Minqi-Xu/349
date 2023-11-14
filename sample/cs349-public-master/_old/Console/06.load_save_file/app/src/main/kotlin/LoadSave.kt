import java.io.FileReader
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.FileWriter
import java.io.BufferedWriter

const val delimiter = ","
const val endl = "\n"

fun main() {
    save()
    load()
}

private fun save() {
    println("Saving")

    var file: FileWriter? = null
    var writer: BufferedWriter? = null
    try {
        file = FileWriter("data.txt")
        writer = BufferedWriter(file)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    var row = 0
    val s = "entry_"
    val pi = 3.14159
    val e = 2.71828

    // one line is written for each row of data
    if (writer != null) {
        writer.write(
            row.toString() + delimiter +
                    s + row + delimiter +
                    pi + endl
        )

        // these represent two different row (and each row has multiple values)
        // DELIMITER separates values, ENDL ends the row
        row++
        writer.write(
            row.toString() + delimiter +
                    s + row + delimiter +
                    e + endl
        )
    }

    writer!!.close()
    file!!.close()
}

private fun load() {
    println("Loading")

    var file: FileReader? = null
    var reader: BufferedReader? = null
    var values: Array<String>

    // open input file
    try {
        file = FileReader("data.txt")
        reader = BufferedReader(file)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }

    // read and process lines one at a time
    try {
        val strings = reader?.readLines()
        if (strings != null) {
            for (line in strings) {
                values = line.split(delimiter).toTypedArray()
                for (value in values) {
                    print("$value ")
                }
                println()
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    reader!!.close()
    file!!.close()
}