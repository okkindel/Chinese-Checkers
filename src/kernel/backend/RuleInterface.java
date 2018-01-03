package kernel.backend;

/**
 * RuleInterface Interface
 * Standardise the way of rules.
 */
public interface RuleInterface {
    /**
     * Checks or not the rule itself can be valid.
     *
     * @param abstractBoard the board :v
     * @param undoInterface The undoInterface where rule is checked against
     * @return A boolean
     */
    boolean checkValid(AbstractBoard abstractBoard, UndoInterface undoInterface);

    String retarded_move();
}