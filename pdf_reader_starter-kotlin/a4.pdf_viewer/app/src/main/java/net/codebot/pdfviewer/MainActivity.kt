package net.codebot.pdfviewer

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

// PDF sample code from
// https://medium.com/@chahat.jain0/rendering-a-pdf-document-in-android-activity-fragment-using-pdfrenderer-442462cb8f9a
// Issues about cache etc. are not at all obvious from documentation, so we should expect people to need this.
// We may wish to provide this code.
class MainActivity : AppCompatActivity() {
    val LOGNAME = "pdf_viewer"
    val FILENAME = "shannon1948.pdf"
    val FILERESID = R.raw.shannon1948

    // manage the pages of the PDF, see below
    lateinit var pdfRenderer: PdfRenderer
    lateinit var parcelFileDescriptor: ParcelFileDescriptor
    var currentPage: PdfRenderer.Page? = null

    // custom ImageView class that captures strokes and draws them over the image
    lateinit var pageImage: PDFimage
    val info = infos()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layout = findViewById<LinearLayout>(R.id.pdfLayout)
        layout.isEnabled = true
        // val total_page = pdfRenderer.pageCount


        val title = findViewById<TextView>(R.id.fileName)
        val pagestatus = findViewById<TextView>(R.id.page)
        val pen = findViewById<ImageButton>(R.id.Pen)
        val highlight = findViewById<ImageButton>(R.id.Highlight)
        val eraser = findViewById<ImageButton>(R.id.Eraser)
        val prevpage = findViewById<Button>(R.id.Prev)
        val nextpage = findViewById<Button>(R.id.Next)
        val undobutton = findViewById<Button>(R.id.Undo)
        val redobutton = findViewById<Button>(R.id.Redo)

        title.text = FILENAME

        pagestatus.text = "Page 0/0"


        pageImage = PDFimage(this, info)
        layout.addView(pageImage)

        pageImage.minimumWidth = 1000
        pageImage.minimumHeight = 2000

        // open page 0 of the PDF
        // it will be displayed as an image in the pageImage (above)
        try {
            openRenderer(this)
            showPage(info.current_page)
            // closeRenderer()
        } catch (exception: IOException) {
            Log.d(LOGNAME, "Error opening PDF")
        }

        info.num_page = pdfRenderer.pageCount
        for(i in 0..info.num_page-1) {
            val tempUndoList = ArrayList<drawings>()
            val tempRedoList = ArrayList<drawings>()
            info.undo_stack.add(tempUndoList)
            info.redo_stack.add(tempRedoList)

        }

        prevpage.setOnClickListener {
            info.current_page--
            if(info.current_page < 0) {
                info.current_page = 0
            }
            showPage(info.current_page)
        }

        nextpage.setOnClickListener {
            info.current_page++
            if(info.current_page >= pdfRenderer.pageCount) {
                info.current_page--
            }
            showPage(info.current_page)
        }

        pen.setOnClickListener {
            info.selectedTool = 1
        }

        highlight.setOnClickListener {
            info.selectedTool = 2
        }

        eraser.setOnClickListener {
            info.selectedTool = 3
        }

        undobutton.setOnClickListener {
            Log.d(LOGNAME, "debug!")
            if(info.undo_stack[info.current_page].size != 0) {
                val temp = info.undo_stack[info.current_page].last()
                info.redo_stack[info.current_page].add(temp)
                if(temp.type == 3) {
                    for(i in 0..temp.erase_index.size-1) {
                        info.undo_stack[info.current_page][temp.erase_index[i]].visible = 1
                        Log.d(LOGNAME, "i="+i+" change to 1")
                    }
                }
                info.undo_stack[info.current_page].removeLast()
            }
        }

        redobutton.setOnClickListener {
            if(info.redo_stack[info.current_page].size != 0) {
                val temp = info.redo_stack[info.current_page].last()
                info.undo_stack[info.current_page].add(temp)
                if(temp.type ==3) {
                    for(i in 0..temp.erase_index.size-1) {
                        info.undo_stack[info.current_page][i].visible = 0
                        Log.d(LOGNAME, "i="+i+" change to 0")
                    }
                }
                info.redo_stack[info.current_page].removeLast()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            closeRenderer()
        } catch (ex: IOException) {
            Log.d(LOGNAME, "Unable to close PDF renderer")
        }
    }

    @Throws(IOException::class)
    private fun openRenderer(context: Context) {
        // In this sample, we read a PDF from the assets directory.
        val file = File(context.cacheDir, FILENAME)
        if (!file.exists()) {
            // pdfRenderer cannot handle the resource directly,
            // so extract it into the local cache directory.
            val asset = this.resources.openRawResource(FILERESID)
            val output = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var size: Int
            while (asset.read(buffer).also { size = it } != -1) {
                output.write(buffer, 0, size)
            }
            asset.close()
            output.close()
        }
        parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)

        // capture PDF data
        // all this just to get a handle to the actual PDF representation
        pdfRenderer = PdfRenderer(parcelFileDescriptor)
    }

    // do this before you quit!
    @Throws(IOException::class)
    private fun closeRenderer() {
        currentPage?.close()
        pdfRenderer.close()
        parcelFileDescriptor.close()
    }

    private fun showPage(index: Int) {
        if (pdfRenderer.pageCount <= index) {
            return
        }
        // Close the current page before opening another one.
        currentPage?.close()

        // Use `openPage` to open a specific page in PDF.
        currentPage = pdfRenderer.openPage(index)

        if (currentPage != null) {
            // Important: the destination bitmap must be ARGB (not RGB).
            val bitmap = Bitmap.createBitmap(currentPage!!.getWidth(), currentPage!!.getHeight(), Bitmap.Config.ARGB_8888)

            // Here, we render the page onto the Bitmap.
            // To render a portion of the page, use the second and third parameter. Pass nulls to get the default result.
            // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
            currentPage!!.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

            // Display the page
            pageImage.setImage(bitmap)
        }

        val pagestatus = findViewById<TextView>(R.id.page)
        pagestatus.text = "Page " + (index + 1) + "/" + pdfRenderer.pageCount


    }


}