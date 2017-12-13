package core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public abstract class Board extends Observable implements Serializable {
    protected List<Rule> rules;
    protected ArrayList<Player> players;
    protected Player activePlayer;
    protected boolean gameEnd;
    protected Player localPlayer;
}
