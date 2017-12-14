package core.model;

public interface Rule {

    boolean checkValid(Board board, Command command);
    String getInvalidMsg();
}