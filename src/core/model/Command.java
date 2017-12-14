package core.model;

public interface Command {

    void execute();
    void undo();

}
