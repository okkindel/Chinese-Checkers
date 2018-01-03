package kernel.backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

/**
 * AbstractBoard Class
 * SHIT CODE LEVEL MASTER HERE
 * SERVER ALSO HERE
 * GOOD LUCK
 */
public abstract class AbstractBoard extends Observable implements Serializable {

    //net variables
    private int port;
    private String ip;
    protected Participant local_player;
    private volatile String msg_out;
    protected volatile String msg_in;
    private volatile boolean is_connected;

    //player_list, which should also be used by the subclasses of this class
    protected ArrayList<Participant> player_list;
    //the rulezz for each checkers, used of this class
    protected List<RuleInterface> set_of_rulez;
    protected Executor executor;
    protected Participant activePlayer;
    protected boolean gameFinished;

    /**
     * Constructor for class.
     */
    protected AbstractBoard() {
        executor = new Executor();
        gameFinished = false;
        is_connected = false;
        msg_out = null;
        msg_in = null;
        activePlayer = null;
        set_of_rulez = new ArrayList<>();
        player_list = new ArrayList<>();
    }

    protected void nextPlayer() {
        int next = player_list.indexOf(activePlayer) + 1;

        if (next > player_list.size() - 1) {
            next = 0;
        }
        activePlayer = player_list.get(next);
        show_message("msg-" + activePlayer.getName() + "'s turn"); //show that the active player has changed, and the name of the new player
        show_message("turnChange");
    }

    /**
     * Relays a message from objects outside of this class to the observers of this class. In particular used by Robot to relay the messages it has to show the player.
     * <p>
     * The benefits of this method are twofold: setChanged is protected, so it would have to be overridden in this class. As well as this it condenses Robot having to call both setChanged and notifyObservers, since instead they only need one call to this method.
     *
     * @param message The message to be relayed to the observers of this class
     */
    protected void show_message(String message) {
        setChanged();
        notifyObservers(message);
    }

    protected void addPlayer(String playerName, boolean AI) {
        if (AI) {
            player_list.add(new Robot(playerName));
        } else {
            player_list.add(new Human(playerName));
        }
    }

    /**
     * New thread, new instance of the server.
     */
    public void runClient(String IP, String port) {
        ip = IP;
        this.port = Integer.parseInt(port);
        Thread thread = new Thread(new Client());
        thread.start();
    }

    /**
     * New thread and new instance of the server.
     */
    public void runServer() {
        Thread thread = new Thread(new Server());
        thread.start();
    }

    /**
     * Net Play Server, this runs alongside the main checkers once started, broadcasting to and receiving moves from the server on the other side.
     * <p>
     * Runs in it's own thread, and relies on some of the variables contained in AbstractBoard to function
     */
    private class Server implements Runnable {

        @Override
        public void run() {

            ServerSocket socket = null;

            try {
                socket = new ServerSocket(4444); //default port for this has been chosen as 4444 since it's something that is not usually used
            } catch (IOException e) {
                show_message("err-" + "Couldn't listen on the port\nExiting..."); //this means we couldn't listen on the port, likely that something else is listening on that port
                System.exit(1);
            }
            Socket clientSocket = null; //setup the socket for the client
            try {
                clientSocket = socket.accept(); //then assign it by connecting to this server's socket
            } catch (IOException e) { //if this fires, something indeterminate went wrong, could be a number of network problems
                show_message("err-" + "Problem connecting to client/nDo they have a firewall blocking communication?");
                System.exit(1);
            }
            connect_setter(); //if we got here, we're is_connected, so we set this to get rid of the loading screen and start up the checkers

            PrintWriter out; //get the printWriter ready so it can write messages to the client
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true); //connect the printWriter
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //setup the BufferedReader to receive input from the client

                String inbound = null; //setting up the placeholder variables so that we can check if messages sent and received are new or not
                String lastInbound = null;
                String outbound = null;
                String lastOutbound = null;

                out.println(msg_out); //sends the outbound message from the board, which at this point should denote the gametype of this checkers
                String clientGame = null;
                while (clientGame == null) { //get the gametype of the server
                    clientGame = in.readLine();
                }
                out_msg_setter(null); //if we get here the gametype is the same so we want to reset outbound message for it's true purpose of passing moves

                while (!gameFinished) { //while the checkers is still running, we want to keep broadcasting and receiving
                    while (Objects.equals(outbound, lastOutbound)) { //while the outbound message hasn't changed since we last received a different one
                        outbound = msg_out; //we set it to the message from the board
                        Thread.sleep(500); //and wait a few seconds so we aren't constantly assigning the same variable
                    }
                    lastOutbound = outbound; //then we set the placeholder so we can check it next time we're sending a message
                    out.println(outbound); //and send the message to the client, so that it can take a move from it
                    while (Objects.equals(inbound, lastInbound)) { //while the inbound message is the same as the last one we received
                        inbound = in.readLine(); //get the message from the client
                        Thread.sleep(500); //and wait a few seconds so we aren't constantly polling the client
                    }
                    in_msg_setter(inbound); //set the message in AbstractBoard so it can be parsed
                    lastInbound = inbound; //and set the placeholder message so that we can check next time we receive a message
                }
                out.close(); //now the checkers is over, we have no reason for the following, so we close them all
                in.close();
                clientSocket.close();
                socket.close();

            } catch (IOException e) { //if this fires we lost connection, so tell the user as such
                show_message("err-" + "Connection lost!");
            } catch (InterruptedException e) {
                show_message("err-" + "Something went wrong!\nLost connection!");//this should never fire, so we don't really have anything to tell the user if it does
            }
        }
    }

    /**
     * Net Play Client, this runs alongside the main checkers once started, broadcasting to and receiving moves from the server on the other side.
     * <p>
     * Runs in it's own thread, and relies on some of the variables contained in AbstractBoard to function
     */
    private class Client implements Runnable {

        @Override
        public void run() {

            Socket socket = null;
            PrintWriter out = null;
            BufferedReader in = null;

            try {
                socket = new Socket(ip, port);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (Exception e) {
                show_message("err-" + "Couldn't connect to the port on host: " + ip + "\nExiting...");
                System.exit(1);
            }
            connect_setter();

            String inbound = null; //setup placeholder variables so we can check the value of the incoming and outgoing messages
            String lastInbound = null;
            String outbound = null;
            String lastOutbound = null;

            out.println(msg_out); //sends the outbound message from the board, which at this point should denote the gametype of this checkers
            String serverGame = null;
            while (serverGame == null) { //get the gametype of the server
                try {
                    serverGame = in.readLine();
                } catch (IOException e) {
                    show_message("err-" + "Connection lost!"); //account for the possibility of the server closing before we can get the gametype
                }
            }
            out_msg_setter(null); //if we get here the gametype is the same so we want to reset outbound message for it's true purpose of passing moves

            try {
                while (!gameFinished) { //until the checkers ends, we want to keep broadcasting and receiving depending on whose turn it is

                    while (Objects.equals(inbound, lastInbound)) { //because we are the client, we receive first
                        inbound = in.readLine();
                        Thread.sleep(500); //break up the checks by waiting three seconds, so that we aren't constantly checking
                    }
                    in_msg_setter(inbound); //now that the inbound message is a new one, we set it in the board so we can then parse it to create a move
                    lastInbound = inbound; //also set the placeholder value for next time we're the client
                    while (Objects.equals(outbound, lastOutbound)) { //this should be true until we have made a move
                        outbound = msg_out;
                        Thread.sleep(500); //wait so that we aren't constantly assigning the same variable
                    }
                    lastOutbound = outbound; //set the placeholder for next time we're the server
                    out.println(outbound); //send the move to the server
                }
                out.close(); //we don't need the connection any more, since the checkers is over, so we close everything to be responsible
                in.close();
                socket.close();
            } catch (IOException e) {
                show_message("err-" + "Connection lost!");
            } catch (InterruptedException ignored) {
            }
        }
    }

    //getters and setter methods
    public Participant local_player_getter() {
        return local_player;
    }

    public ArrayList<Participant> player_list_getter() {
        return player_list;
    }

    public Participant active_player_getter() {
        return activePlayer;
    }

    public Executor executor_getter() {
        return executor;
    }

    private void in_msg_setter(String inbound) {
        this.msg_in = inbound;
    }

    public void out_msg_setter(String msg_out) {
        this.msg_out = msg_out;
    }

    private void connect_setter() {
        this.is_connected = true;
    }

    public void local_player_setter(Participant local_player) {
        this.local_player = local_player;
    }

    public boolean connect_getter() {
        return is_connected;
    }
}