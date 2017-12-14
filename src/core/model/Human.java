package core.model;

public class Human extends Player {

    public Human(String name) {
        super(name);
    }
    @Override
    public boolean isAI() {
        return false;
    }
}