package kernel.frontend;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import checkers.backend.GameBoard;
import checkers.backend.OptionsMenu;
import checkers.frontend.GameView;

/**
 * Menu Class
 */
public class GameMenu extends AbstractView {

    private JButton game;
    private JButton online;
    private JButton options;
    private JButton exit;
    private JButton make_server;
    private JButton join_server;
    private JFormattedTextField ip_field;
    private JFormattedTextField port_field;


    public GameMenu() {

        game = new JButton("Play Local");
        online = new JButton("Play Online");
        options = new JButton("Options");
        exit = new JButton("Exit");
        make_server = new JButton("Host Server");
        join_server = new JButton("Join Server");
        game.addActionListener(new NewGame());
        online.addActionListener(new OnlineGame());
        options.addActionListener(new Options());
        make_server.addActionListener(new ServerHost());
        join_server.addActionListener(new JoinGame());
        exit.addActionListener(new ExitGame());
        AssetsLoader.loadProperties();
        paint();
    }


    /**
     * This draws gui.
     */
    protected void paint() {
        super.paint();

        JLabel picLabel = new JLabel(new ImageIcon((BufferedImage) AssetsLoader.load("src/assets/logo.png", "image")));
        main_panel.add(picLabel);
        window.add(main_panel, BorderLayout.NORTH);
        main_panel.add(picLabel);
        main_panel.setBackground(new Color(30, 0, 0));

        game.setBackground(new Color(34, 0, 0));
        online.setBackground(new Color(34, 10, 10));
        options.setBackground(new Color(34, 20, 20));
        exit.setBackground(new Color(34, 30, 30));

        not_main_panel.setLayout(new GridLayout(4, 1, 0, 2));
        not_main_panel.add(game);
        not_main_panel.add(online);
        not_main_panel.add(options);
        not_main_panel.add(exit);
        not_main_panel.setBackground(new Color(40, 0, 0));
        window.add(main_panel, BorderLayout.NORTH);
        window.add(not_main_panel, BorderLayout.SOUTH);
        window.setSize(new Dimension(500, 800));
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    /**
     * Draws the options of Online Game.
     */
    private void drawNetPlayOptions() {

        window.setVisible(false);
        super.paint();

        JLabel ip_label = new JLabel("IP:");
        JLabel port_label = new JLabel("Port:");
        ip_field = new JFormattedTextField("127.0.0.1");
        port_field = new JFormattedTextField("4444");
        main_panel.add(ip_label);
        main_panel.add(ip_field);
        main_panel.add(port_label);
        main_panel.add(port_field);
        not_main_panel.add(join_server);
        not_main_panel.add(make_server);
        join_server.setBackground(new Color(50, 30, 30));
        make_server.setBackground(new Color(50, 30, 30));
        not_main_panel.setBackground(new Color(40, 0, 0));
        main_panel.setBackground(new Color(40, 0, 0));
        window.add(main_panel, BorderLayout.NORTH);
        window.add(not_main_panel, BorderLayout.SOUTH);
        ip_field.setColumns(15);
        port_field.setColumns(6);
        window.setSize(new Dimension(500, 100));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    /**
     * Draws a gif screen
     */
    private void drawLoadingScreen() {

        window.setVisible(false);
        super.paint();
        main_panel.add(new JLabel("Please wait..."));
        JLabel picLabel = new JLabel();
        main_panel.setBackground(new Color(40, 0, 0));
        main_panel.add(picLabel);
        window.add(main_panel);
        window.setSize(new Dimension(550, 70));
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }


    /**
     * Checkers new game.
     */
    public void play()
    {
        window.setVisible(false);
        new GameView();
    }

    /**
     * Checkers options menu.
     */
    private class Options implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new OptionsMenu();
        }
    }

    /**
     * Host Server Menu
     */
    private class ServerHost implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            drawLoadingScreen();
            SwingWorker<?, ?> worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws InterruptedException {
                    GameBoard board = new GameBoard();
                    board.runServer();
                    board.local_player_setter(board.player_list_getter().get(0));
                    while (!board.connect_getter()) {}
                    window.setVisible(false);
                    new GameView(board);

                    return null;
                }
            };
            worker.execute();
        }
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
     * New Game
     */
    private class NewGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            play();
        }
    }

    /**
     * Online Game
     */
    private class OnlineGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawNetPlayOptions();
        }

    }

    /**
     * Game exit
     */
    private class ExitGame implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }
}