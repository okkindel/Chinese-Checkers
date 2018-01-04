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
    public Participant activePlayer;
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
        //name of the new player
        show_message("msg-" + activePlayer.getName() + "'s turn");
        show_message("turnChange");
    }

    /**
     * Relays a message
     * Observer.
     *
     * @param message The message.
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
     * New thread, new instance of the client.
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
     * Server, this runs, broadcasting to and receiving moves from the server.
     * Runs thread.
     */
    private class Server implements Runnable {

        @Override
        public void run() {

            ServerSocket socket = null;

            try {
                socket = new ServerSocket(4444);
            } catch (IOException e) {
                show_message("err-" + "Couldn't listen on the port\nExiting...");
                System.exit(1);
            }
            //setup the socket for the client
            Socket clientSocket = null;
            try {
                //then assign it
                clientSocket = socket.accept();
            } catch (IOException e) {
                show_message("err-" + "Problem connecting.");
                System.exit(1);
            }
            //we're connected, start up the checkers
            connect_setter();
            //setup PrintWriter so it can write messages
            PrintWriter out;

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                //setup the BufferedReader to receive
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                //variables - if messages sent and received are new or not
                String inbound = null;
                String lastInbound = null;
                String outbound = null;
                String lastOutbound = null;

                //outbound message
                out.println(msg_out);
                String clientGame = null;
                while (clientGame == null) {
                    clientGame = in.readLine();
                }
                //true purpose of passing moves
                out_msg_setter(null);
                while (!gameFinished) {
                    while (Objects.equals(outbound, lastOutbound)) {
                        outbound = msg_out;
                        Thread.sleep(500);
                    }
                    //we can check it next time we're sending a message
                    lastOutbound = outbound;
                    //and send the message to the client
                    out.println(outbound);
                    //while message is same
                    while (Objects.equals(inbound, lastInbound)) {
                        //get the message
                        inbound = in.readLine();
                        Thread.sleep(500);
                    }
                    //set the message in AbstractBoard so it can be parsed
                    in_msg_setter(inbound);
                    //we can check next time we receive a message
                    lastInbound = inbound;
                }
                out.close();
                in.close();
                clientSocket.close();
                socket.close();

            } catch (IOException e) {
                show_message("err-" + "Connection lost!");
            } catch (Exception ignore) { /*none*/ }
        }
    }

    /**
     * Server Client, this runs, broadcasting to and receiving moves from the server.
     * Own thread.
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

            //check value of incoming and outgoing messages
            String inbound = null;
            String lastInbound = null;
            String outbound = null;
            String lastOutbound = null;

            //sends the outbound message from the board
            out.println(msg_out);
            String serverGame = null;
            while (serverGame == null) {
                try {
                    serverGame = in.readLine();
                } catch (IOException e) {
                    show_message("err-" + "Connection lost!");
                }
            }
            //want to reset outbound message for it's true purpose of passing moves
            out_msg_setter(null);

            try {
                //keep broadcasting and receiving
                while (!gameFinished) {
                    //we receive first
                    while (Objects.equals(inbound, lastInbound)) {
                        inbound = in.readLine();
                        Thread.sleep(500);
                    }
                    // we can then parse it to create a move
                    in_msg_setter(inbound);
                    lastInbound = inbound;
                    //true until we have made a move
                    while (Objects.equals(outbound, lastOutbound)) {
                        outbound = msg_out;
                        Thread.sleep(500);
                    }
                    lastOutbound = outbound;
                    //send the move to the server
                    out.println(outbound);
                }
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                show_message("err-" + "Connection lost!");
            } catch (Exception ignore) { /*none */}
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

    protected void out_msg_setter(String msg_out) {
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