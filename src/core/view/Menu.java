package core.view;

import core.model.Assets;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Menu extends AbstractView {

    private JButton startButton, exitButton;
    private JButton newGameButton, netPlayButton, optionsButton;
    private JButton backButton, saveOptions;
    private JComboBox playerOneTypeComboBox;
    private JComboBox playerTwoTypeComboBox;
    private JComboBox playerThreeTypeComboBox;
    private JComboBox playerFourTypeComboBox;
    private JComboBox playerFiveTypeComboBox;
    private JComboBox playerSixTypeComboBox;

    public Menu() {
        startButton = new JButton("Start Game");
        exitButton = new JButton("Exit Game");
        newGameButton = new JButton("New Game");
        netPlayButton = new JButton("Network Play");
        optionsButton = new JButton("Options");
        backButton = new JButton("Back");
        saveOptions = new JButton("Save");

        startButton.addActionListener(new StartGamePressed());
        exitButton.addActionListener(new ExitPressed());
        newGameButton.addActionListener(new NewGamePressed());
        //netPlayButton.addActionListener(new NetPlayPressed());
        optionsButton.addActionListener(new OptionsPressed());
        backButton.addActionListener(new BackPressed());
        saveOptions.addActionListener(new SaveOptionsPressed());
        draw();
    }

    void draw() {

        super.draw();
        JLabel imageLabel = new JLabel(new ImageIcon((BufferedImage) Assets.load("src/assets/logo.png", "image")));
        mainPanel.add(imageLabel);
        underPanel.add(startButton);
        underPanel.add(exitButton);

        frame.add(mainPanel, BorderLayout.NORTH);
        frame.add(underPanel, BorderLayout.SOUTH);
        frame.setSize(new Dimension(400, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void gameButtonPressed() {

        frame.setVisible(false);
        super.draw();
        JLabel imageLabel;
        imageLabel = new JLabel(new ImageIcon((BufferedImage) Assets.load("src/assets/chineseCheckersLogo.png", "image")));
        mainPanel.add(imageLabel);
        underPanel.setLayout(new GridLayout(5, 1, 15, 20));
        underPanel.add(newGameButton);
        underPanel.add(netPlayButton);
        underPanel.add(optionsButton);
        underPanel.add(backButton);

        frame.add(mainPanel, BorderLayout.WEST);
        frame.add(underPanel, BorderLayout.EAST);
        frame.setSize(new Dimension(300, 300));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void newGame() {
        frame.setVisible(false);
        //new CheckersGameView();
    }

    private void drawOptionMenu() {
        super.draw();

        JLabel options = new JLabel("Options");
        options.setVerticalAlignment(JLabel.CENTER);
        options.setHorizontalAlignment(JLabel.CENTER);
        options.setPreferredSize(new Dimension(100, 50));

        String[] playerOptions = {"Human", "AI", "Off"};

        JLabel playerOneOptionsLabel = new JLabel("Player One");
        playerOneOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerOneOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerOneTypeComboBox = new JComboBox(playerOptions);
        playerOneTypeComboBox.setSelectedIndex(0);

        JLabel playerTwoOptionsLabel = new JLabel("Player Two");
        playerTwoOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerTwoOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerTwoTypeComboBox = new JComboBox(playerOptions);
        playerTwoTypeComboBox.setSelectedIndex(1);

        JLabel playerThreeOptionsLabel = new JLabel("Player Three");
        playerThreeOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerThreeOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerThreeTypeComboBox = new JComboBox(playerOptions);
        playerThreeTypeComboBox.setSelectedIndex(0);

        JLabel playerFourOptionsLabel = new JLabel("Player Four");
        playerThreeOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerThreeOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerFourTypeComboBox = new JComboBox(playerOptions);
        playerFourTypeComboBox.setSelectedIndex(0);

        JLabel playerFiveOptionsLabel = new JLabel("Player Five");
        playerFiveOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerFiveOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerFiveTypeComboBox = new JComboBox(playerOptions);
        playerFiveTypeComboBox.setSelectedIndex(0);

        JLabel playerSixOptionsLabel = new JLabel("Player Six");
        playerSixOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerSixOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerSixTypeComboBox = new JComboBox(playerOptions);
        playerSixTypeComboBox.setSelectedIndex(0);

        mainPanel.setLayout(new GridLayout(7, 2, 15, 20));
        mainPanel.add(playerOneOptionsLabel);
        mainPanel.add(playerOneTypeComboBox);
        mainPanel.add(playerTwoOptionsLabel);
        mainPanel.add(playerTwoTypeComboBox);
        mainPanel.add(playerThreeOptionsLabel);
        mainPanel.add(playerThreeTypeComboBox);
        mainPanel.add(playerFourOptionsLabel);
        mainPanel.add(playerFourTypeComboBox);
        mainPanel.add(playerFiveOptionsLabel);
        mainPanel.add(playerFiveTypeComboBox);

        mainPanel.add(new JLabel());
        mainPanel.add(saveOptions);
        mainPanel.add(new JLabel());
        mainPanel.add(new JLabel());
        mainPanel.add(backButton);

//        playerOneTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerOne.type")));
//        playerTwoTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerTwo.type")));
//        playerThreeTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerThree.type")));
//        playerFourTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerFour.type")));
//        playerFiveTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerFive.type")));
//        playerSixTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerSix.type")));

        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.add(options, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private class ExitPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class StartGamePressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameButtonPressed();
        }
    }

    private class NewGamePressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { //the new game button has been pressed
            newGame(); //so we start a new game
        }
    }

    private class OptionsPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            drawOptionMenu();
        }
    }

    private class SaveOptionsPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            System.setProperty("checkers.playerOne.type", playerOneTypeComboBox.getSelectedIndex() + "");
            System.setProperty("checkers.playerTwo.type", playerTwoTypeComboBox.getSelectedIndex() + "");
            System.setProperty("checkers.playerThree.type", playerThreeTypeComboBox.getSelectedIndex() + "");
            System.setProperty("checkers.playerFour.type", playerFourTypeComboBox.getSelectedIndex() + "");
            System.setProperty("checkers.playerFive.type", playerFiveTypeComboBox.getSelectedIndex() + "");
            System.setProperty("checkers.playerSix.type", playerSixTypeComboBox.getSelectedIndex() + "");
            File optionsFile = (File) Assets.load("src/resources/config.properties", "textFile");

            FileWriter writer;
            try {
                writer = new FileWriter(optionsFile);
                System.getProperties().store(writer, "Saved.");
                writer.close();
            } catch (IOException ex) {
                showError("Problems saving.");
            }
            frame.setVisible(false);
            draw();
        }
    }

    private class BackPressed implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.setVisible(false);
            draw();
        }
    }
}
