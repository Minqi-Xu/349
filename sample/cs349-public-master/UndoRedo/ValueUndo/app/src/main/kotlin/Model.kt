import javax.swing.undo.*

// MVC Model with Undo/Redo capability
// **NOTE**: Uses Java Swing UI Framework

class Model(
    value: Int,
    min: Int,
    max: Int
) {

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

    // Model data
    private var value: Int

    // could make these settable and undoable too
    val min: Int
    val max: Int

    init {
        this.value = value
        this.min = min
        this.max = max
    }

    fun getValue(): Int {
        return value
    }

    fun setValue(v: Int) {
        println("Model: set value to $v")

        // create undoable edit
        val undoableEdit = object : AbstractUndoableEdit() {
            // capture variables for closure
            val oldValue = value
            val newValue = v

            // Method that is called when we must redo the undone action
            @Throws(CannotRedoException::class)
            override fun redo() {
                super.redo()
                value = newValue
                println("Model: redo value to $value")
                updateViews()
            }

            @Throws(CannotUndoException::class)
            override fun undo() {
                super.undo()
                value = oldValue
                println("Model: undo value to $value")
                updateViews()
            }
        }

        // Add this undoable edit to the undo manager
        undoManager.addEdit(undoableEdit)

        // finally, set the value and notify views
        value = v
        updateViews()
    }

    fun incrementValue() {
        println("Model: increment value ")

        // constrain value to valid range
        if (value + 1 > max) return

        // create undoable edit
        val undoableEdit: UndoableEdit = object : AbstractUndoableEdit() {
            // Method that is called when we must redo the undone action
            @Throws(CannotRedoException::class)
            override fun redo() {
                super.redo()
                value += 1
                println("Model: redo value to $value")
                updateViews()
            }

            @Throws(CannotUndoException::class)
            override fun undo() {
                super.undo()
                value -= 1
                println("Model: undo value to $value")
                updateViews()
            }
        }

        // Add this undoable edit to the undo manager
        undoManager.addEdit(undoableEdit)
        value += 1
        updateViews()
    }

    fun decrementValue() {
        println("Model: decrement value ")

        // constrain value to valid range
        if (value - 1 < min) return

        // create undoable edit
        val undoableEdit: UndoableEdit = object : AbstractUndoableEdit() {
            // Method that is called when we must redo the undone action
            @Throws(CannotRedoException::class)
            override fun redo() {
                super.redo()
                value -= 1
                println("Model: redo value to $value")
                updateViews()
            }

            @Throws(CannotUndoException::class)
            override fun undo() {
                super.undo()
                value += 1
                println("Model: undo value to $value")
                updateViews()
            }
        }

        // Add this undoable edit to the undo manager
        undoManager.addEdit(undoableEdit)
        value -= 1
        updateViews()
    }
}
