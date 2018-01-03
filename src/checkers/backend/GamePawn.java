package checkers.backend;

import kernel.backend.AbstractPawn;
import kernel.backend.Participant;

/**
 * GamePawn Class
 * Simple class containing the differences from a normal piece ond other, ex: chinese checkers
 */
public class GamePawn extends AbstractPawn {

    private Participant owner;

    /**
     * Simple Constructor is de facto simple setter
     *
     * @param owner owner is owner
     */
    GamePawn(Participant owner) {
        this.owner = owner;
    }

    /**
     * And we need also getter here
     */
    public Participant getOwner() {
        return owner;
    }
}