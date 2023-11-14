import java.awt.Shape
import java.awt.geom.AffineTransform
import java.awt.geom.Rectangle2D
import javax.swing.undo.AbstractUndoableEdit
import javax.swing.undo.CannotRedoException
import javax.swing.undo.UndoManager

// MVC Model with Undo/Redo capability
// **NOTE**: Uses Java Swing UI Framework

class Model internal constructor() {

    //region MVC View observer code

    val observers = mutableListOf<IView>();

    fun addObserver(view: IView) {
        observers.add(view)
    }

    fun updateViews() {
        for (view in observers) {
            view.update()
        }
    }

    //endregion

    //region Java Swing Undo and Redo Manager

    private val undoManager: UndoManager = UndoManager()

    fun undo() { if (canUndo) undoManager.undo() }

    fun redo() { if (canRedo) undoManager.redo() }

    val canUndo get() = undoManager.canUndo()

    val canRedo get() = undoManager.canRedo()

    //endregion

    //region Shape position and transformation

    var rect: Rectangle2D = Rectangle2D.Double(0.0, 0.0, 64.0, 64.0)

    // absolute position of the shape
    private var absX = 30
    private var absY = 30

    // position before dragged
    private var pX = 30
    private var pY = 30

    fun translate(dx: Int, dy: Int) {
        absX += dx
        absY += dy
        updateViews()
    }

    val shape: Shape
        get() {
            // affine transformation to translate
            val t = AffineTransform()
            t.translate(absX.toDouble(), absY.toDouble())
            // create translated shape
            return t.createTransformedShape(rect)
        }

    //endregion

    var recUndoable: RectUndoable? = null

    fun endEdit() {
        recUndoable = RectUndoable(pX, pY, absX, absY)
        undoManager.addEdit(recUndoable)
        pX = absX
        pY = absY
    }

    // the undoable object for the stack
    inner class RectUndoable(px: Int, py: Int, x: Int, y: Int) : AbstractUndoableEdit() {
        // position for undo
        var p_translateX = 0
        var p_translateY = 0

        // position for redo
        var n_translateX = 0
        var n_translateY = 0

        @Throws(CannotRedoException::class)
        override fun undo() {
            super.undo()
            absX = p_translateX
            absY = p_translateY
            println("Model: undo location to $absX,$absY")
            updateViews()
        }

        @Throws(CannotRedoException::class)
        override fun redo() {
            super.redo()
            absX = n_translateX
            absY = n_translateY
            println("Model: redo location to $absX,$absY")
            updateViews()
        }

        init {
            // position for undo
            p_translateX = px
            p_translateY = py
            // position for redo
            n_translateX = x
            n_translateY = y
        }
    }
}
