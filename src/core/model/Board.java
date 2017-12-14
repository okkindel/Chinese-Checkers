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
    protected volatile boolean connected;
    protected String serverIp;
    protected int serverPort;

    protected Board()
    {
        players = new ArrayList<Player>();
        rules = new ArrayList<Rule>();
        activePlayer = null;
        gameEnd = false;
        connected = false;
    }

    public boolean getConnected() {
        return connected;
    }
    public Player getLocalPlayer() {
        return localPlayer;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }

    protected void addPlayer(String playerName, boolean AI)
    {
        if (AI) { //the new player has to be either an AI or a human
            players.add(new AI(playerName));
        }
        else {
            players.add(new Human(playerName));
        }
    }

    protected void nextPlayer()
    {
        int next = players.indexOf(activePlayer)+1;

        if (next > players.size()-1) { //we're at the end of the list
            next = 0; //so we go back to the start
        }
        activePlayer = players.get(next);
        relayMessage("msg-"+activePlayer.getName()+ "'s turn"); //show that the active player has changed, and the name of the new player
        relayMessage("turnChange");
    }

    protected void relayMessage(String message)
    {
        setChanged();
        notifyObservers(message);
    }

    public void runClient(String IP, String port) {
        serverIp = IP;
        serverPort = Integer.parseInt(port);
        Thread t = new Thread(new Board.NetPlayClient());
        t.start();
    }

    public void runServer() {
        Thread t = new Thread(new Board.NetPlayServer());
        t.start();
    }
}
