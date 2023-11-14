package net.codebot.pdfviewer
import android.graphics.*

class drawings {
    var type = 0    // 1-pen, 2-highlight, 3-erase
    var visible = 0     // 0- not visible, 1- visible
    var shape: Path? = null
    var points = ArrayList<p>()
    var erase_index = ArrayList<Int>()
}