package kernel.frontend;

import checkers.backend.GameBoard;
import checkers.frontend.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerMenu extends AbstractView {

    private JButton make_server;
    private JButton join_server;
    private JFormattedTextField ip_field;
    private JFormattedTextField port_field;

    public ServerMenu() {
        make_server = new JButton("Host Server");
        join_server = new JButton("Join Server");
        make_server.addActionListener(new ServerHost());
        join_server.addActionListener(new JoinGame());
        AssetsLoader.loadConfiguration();
        paintOptions();
    }

    /**
     * Draws the options of Online Game.
     */
    private void paintOptions() {

        super.paint();

        JLabel ip_label = new JLabel("IP:");
        ip_label.setForeground(new Color(65, 52, 52));
        JLabel port_label = new JLabel("Port:");
        port_label.setForeground(new Color(65, 52, 52));
        ip_field = new JFormattedTextField("127.0.0.1");
        port_field = new JFormattedTextField("4444");
        main_panel.add(ip_label);
        main_panel.add(ip_field);
        main_panel.add(port_label);
        main_panel.add(port_field);
        not_main_panel.add(join_server);
        not_main_panel.add(make_server);
        join_server.setBackground(new Color(50, 20, 20));
        make_server.setBackground(new Color(50, 20, 20));
        not_main_panel.setBackground(new Color(40, 0, 0));
        main_panel.setBackground(new Color(40, 0, 0));
        window.add(main_panel, BorderLayout.CENTER);
        window.add(not_main_panel, BorderLayout.SOUTH);
        ip_field.setColumns(15);
        port_field.setColumns(6);
        window.setSize(new Dimension(500, 100));
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    /**
     * Draws a gif screen
     */
    private void waitingRoom() {

        window.setVisible(false);
        super.paint();
        JLabel text = new JLabel("Please wait...");
        text.setForeground(Color.WHITE);
        main_panel.add(text);
        ImageIcon loading = (ImageIcon) AssetsLoader.load("src/assets/loading.gif", "imageIcon");
        JLabel label = new JLabel();
        label.setIcon(loading);
        not_main_panel.add(label);
        window.add(main_panel);
        window.add(not_main_panel);
        main_panel.setBackground(new Color(40, 0, 0));
        not_main_panel.setBackground(new Color(40, 0, 0));
        window.add(main_panel, BorderLayout.CENTER);
        window.add(not_main_panel, BorderLayout.SOUTH);
        window.setSize(new Dimension(550, 150));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    /**
     * Join Online Game
     */
    private class JoinGame implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {
            GameBoard board = new GameBoard();
            //start thread client
            board.runClient(ip_field.getText(), port_field.getText());
            //local player is second player
            board.local_player_setter(board.player_list_getter().get(1));
            window.setVisible(false);
            new GameView(board);
        }

    }

    /**
     * Host Server Menu
     */
    private class ServerHost implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            waitingRoom();
            SwingWorker<?, ?> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws InterruptedException {
                    GameBoard board = new GameBoard();
                    board.runServer();
                    board.local_player_setter(board.player_list_getter().get(0));
                    while (true) {
                        if (board.connect_getter()) break;
                    }
                    window.setVisible(false);
                    new GameView(board);

                    return null;
                }
            };
            worker.execute();
        }
    }
}