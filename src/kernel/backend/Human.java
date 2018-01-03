package kernel.backend;

/**
 * Human Class
 * Simply clever.
 */
public class Human extends Participant {

    Human(String name) {
        super(name); //TODO REMOVE THIS SHIT
    }

    @Override
    public boolean isRobot() {
        return false;
    }
}
