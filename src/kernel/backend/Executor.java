package kernel.backend;

import java.io.Serializable;
import java.util.Stack;

/**
 * Executor Class
 * Executing commands
 */
public class Executor implements Serializable {

    private static Stack<UndoInterface> undoStack;

    Executor() {
        undoStack = new Stack<>();
    }

    public void undo() {
        if (!undoStack.empty()) {
            undoStack.pop().undo();
        }
    }

    /**
     * also important
     */
    public void executeCommand() {
        undoStack.peek().execute();
    }

    public void addCommand(UndoInterface undoInterface) {
        undoStack.push(undoInterface);
    }
}