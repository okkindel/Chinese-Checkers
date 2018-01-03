package kernel.frontend;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import checkers.frontend.GameView;

/**
 * Menu Class
 */
public class GameMenu extends AbstractView {

    private JButton game;
    private JButton online;
    private JButton options;
    private JButton exit;

    public GameMenu() {

        game = new JButton("Play Local");
        online = new JButton("Play Online");
        options = new JButton("Options");
        exit = new JButton("Exit");
        game.addActionListener(new NewGame());
        online.addActionListener(new OnlineGame());
        options.addActionListener(new Options());
        exit.addActionListener(new ExitGame());
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
        window.setSize(new Dimension(500, 300));
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    /**
     * Checkers new game.
     */
    public void play() {
        window.setVisible(false);
        AssetsLoader.loadConfiguration();
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
            window.setVisible(false);
            new ServerMenu();
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