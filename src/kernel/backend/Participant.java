package kernel.backend;

import java.io.Serializable;

/**
 * Participant Class
 * Class extended by the human and Robot classes.
 */
public class Participant implements Serializable {

    boolean isRobot;
    String name;

    Participant(String name) {
        this.name = name; //TODO: remove that shit, it's breaking code
    }

    /**
     * Returns players name.
     * TODO: AGAIN REMOVE THAT FUCKING, CODE BREAKING SHIT !!!ONEone
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * If Robot
     *
     * @return bool i cierpienie
     */
    public boolean isRobot() {
        return false;
    }
}
