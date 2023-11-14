package net.codebot.pdfviewer

import android.graphics.Path

class infos {
    var num_page = 0
    val undo_stack: ArrayList<ArrayList<drawings>> = ArrayList()
    val redo_stack: ArrayList<ArrayList<drawings>> = ArrayList()
    var selectedTool = 0    // 1-pen, 2-highlight, 3-eraser
    var current_page = 0
}