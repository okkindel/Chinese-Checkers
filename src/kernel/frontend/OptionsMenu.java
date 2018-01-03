package kernel.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Properties;

/**
 * Options menu for checkers.
 */
class OptionsMenu extends AbstractView {

    private JComboBox<? extends String> playerOneTypeComboBox;
    private JComboBox<? extends String> playerTwoTypeComboBox;
    private JComboBox<? extends String> playerThreeTypeComboBox;
    private JComboBox<? extends String> playerFourTypeComboBox;
    private JComboBox<? extends String> playerFiveTypeComboBox;
    private JComboBox<? extends String> playerSixTypeComboBox;

    private JFormattedTextField playerOneNameTextField;
    private JFormattedTextField playerTwoNameTextField;
    private JFormattedTextField playerThreeNameTextField;
    private JFormattedTextField playerFourNameTextField;
    private JFormattedTextField playerFiveNameTextField;
    private JFormattedTextField playerSixNameTextField;
    private JButton save;
    private JButton close;

    OptionsMenu() {
        save = new JButton("Save");
        close = new JButton("Close");
        close.addActionListener(new Close());
        save.addActionListener(new Save());
        AssetsLoader.loadConfiguration();
        drawOptions();
    }

    private void drawOptions() {

        super.paint();
        window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        String[] playerOptions = {"Human", "AI", "Off"};

        JLabel playerOneOptionsLabel = new JLabel("Participant One:");
        playerOneOptionsLabel.setForeground(new Color(65, 52, 52));
        playerOneOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerOneOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerOneTypeComboBox = new JComboBox<>(playerOptions);
        playerOneTypeComboBox.setSelectedIndex(0);

        JLabel playerOneNameLabel = new JLabel("Name:");
        playerOneNameLabel.setForeground(new Color(65, 52, 52));
        playerOneNameLabel.setVerticalAlignment(JLabel.CENTER);
        playerOneNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerOneNameTextField = new JFormattedTextField();
        playerOneNameTextField.setColumns(10);

        JLabel playerTwoOptionsLabel = new JLabel("Participant Two:");
        playerTwoOptionsLabel.setForeground(new Color(65, 52, 52));
        playerTwoOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerTwoOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerTwoTypeComboBox = new JComboBox<>(playerOptions);
        playerTwoTypeComboBox.setSelectedIndex(1);

        JLabel playerTwoNameLabel = new JLabel("Name:");
        playerTwoNameLabel.setForeground(new Color(65, 52, 52));
        playerTwoNameLabel.setVerticalAlignment(JLabel.CENTER);
        playerTwoNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerTwoNameTextField = new JFormattedTextField();
        playerTwoNameTextField.setColumns(10);

        JLabel playerThreeOptionsLabel = new JLabel("Participant Three:");
        playerThreeOptionsLabel.setForeground(new Color(65, 52, 52));
        playerThreeOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerThreeOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerThreeTypeComboBox = new JComboBox<>(playerOptions);

        JLabel playerThreeNameLabel = new JLabel("Name:");
        playerThreeNameLabel.setForeground(new Color(65, 52, 52));
        playerThreeNameLabel.setVerticalAlignment(JLabel.CENTER);
        playerThreeNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerThreeNameTextField = new JFormattedTextField();
        playerThreeNameTextField.setColumns(10);

        JLabel playerFourOptionsLabel = new JLabel("Participant Four:");
        playerFourOptionsLabel.setForeground(new Color(65, 52, 52));
        playerFourOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerFourOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerFourTypeComboBox = new JComboBox<>(playerOptions);

        JLabel playerFourNameLabel = new JLabel("Name:");
        playerFourNameLabel.setForeground(new Color(65, 52, 52));
        playerFourNameLabel.setVerticalAlignment(JLabel.CENTER);
        playerFourNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerFourNameTextField = new JFormattedTextField();
        playerFourNameTextField.setColumns(10);

        JLabel playerFiveOptionsLabel = new JLabel("Participant Five:");
        playerFiveOptionsLabel.setForeground(new Color(65, 52, 52));
        playerFiveOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerFiveOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerFiveTypeComboBox = new JComboBox<>(playerOptions);

        JLabel playerFiveNameLabel = new JLabel("Name:");
        playerFiveNameLabel.setForeground(new Color(65, 52, 52));
        playerFiveNameLabel.setVerticalAlignment(JLabel.CENTER);
        playerFiveNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerFiveNameTextField = new JFormattedTextField();
        playerFiveNameTextField.setColumns(10);

        JLabel playerSixOptionsLabel = new JLabel("Participant Six:");
        playerSixOptionsLabel.setForeground(new Color(65, 52, 52));
        playerSixOptionsLabel.setVerticalAlignment(JLabel.CENTER);
        playerSixOptionsLabel.setHorizontalAlignment(JLabel.CENTER);
        playerSixTypeComboBox = new JComboBox<>(playerOptions);

        JLabel playerSixNameLabel = new JLabel("Name:");
        playerSixNameLabel.setForeground(new Color(65, 52, 52));
        playerSixNameLabel.setVerticalAlignment(JLabel.CENTER);
        playerSixNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerSixNameTextField = new JFormattedTextField();
        playerSixNameTextField.setColumns(10);

        window.add(main_panel, BorderLayout.CENTER);
        window.add(not_main_panel, BorderLayout.SOUTH);
        main_panel.add(playerOneOptionsLabel);
        main_panel.add(playerOneTypeComboBox);
        main_panel.add(playerOneNameLabel);
        main_panel.add(playerOneNameTextField);
        main_panel.add(playerTwoOptionsLabel);
        main_panel.add(playerTwoTypeComboBox);
        main_panel.add(playerTwoNameLabel);
        main_panel.add(playerTwoNameTextField);

        main_panel.add(playerThreeOptionsLabel);
        main_panel.add(playerThreeTypeComboBox);
        main_panel.add(playerThreeNameLabel);
        main_panel.add(playerThreeNameTextField);

        main_panel.add(playerFourOptionsLabel);
        main_panel.add(playerFourTypeComboBox);
        main_panel.add(playerFourNameLabel);
        main_panel.add(playerFourNameTextField);

        main_panel.add(playerFiveOptionsLabel);
        main_panel.add(playerFiveTypeComboBox);
        main_panel.add(playerFiveNameLabel);
        main_panel.add(playerFiveNameTextField);

        main_panel.add(playerSixOptionsLabel);
        main_panel.add(playerSixTypeComboBox);
        main_panel.add(playerSixNameLabel);
        main_panel.add(playerSixNameTextField);

        not_main_panel.add(new JLabel());
        not_main_panel.add(save);
        not_main_panel.add(new JLabel());
        not_main_panel.add(new JLabel());
        not_main_panel.add(close);
        main_panel.setBackground(new Color(30, 0, 0));
        not_main_panel.setBackground(new Color(30, 0, 0));
        save.setBackground(new Color(80, 0, 0));
        close.setBackground(new Color(80, 0, 0));
        main_panel.setLayout(new GridLayout(3, 2, 10, 20));

        try {
            playerOneTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerOne.type")));
            playerTwoTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerTwo.type")));
            playerThreeTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerThree.type")));
            playerFourTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerFour.type")));
            playerFiveTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerFive.type")));
            playerSixTypeComboBox.setSelectedIndex(Integer.parseInt(System.getProperty("checkers.playerSix.type")));

            playerOneNameTextField.setText(System.getProperty("playerOne.name"));
            playerTwoNameTextField.setText(System.getProperty("playerTwo.name"));
            playerThreeNameTextField.setText(System.getProperty("playerThree.name"));
            playerFourNameTextField.setText(System.getProperty("playerFour.name"));
            playerFiveNameTextField.setText(System.getProperty("playerFive.name"));
            playerSixNameTextField.setText(System.getProperty("playerSix.name"));
        } catch (Exception ignore) { /*none*/ }

        window.setSize(new Dimension(1000, 225));
        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
    }


    /**
     * Nothing.
     */
    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            window.setVisible(false);
        }
    }

    /**
     * Save options
     */
    private class Save implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Properties properties = new Properties();
            properties.setProperty("playerOne.name", playerOneNameTextField.getText());
            properties.setProperty("playerTwo.name", playerTwoNameTextField.getText());
            properties.setProperty("playerThree.name", playerThreeNameTextField.getText());
            properties.setProperty("playerFour.name", playerFourNameTextField.getText());
            properties.setProperty("playerFive.name", playerFiveNameTextField.getText());
            properties.setProperty("playerSix.name", playerSixNameTextField.getText());
            properties.setProperty("checkers.playerOne.type", playerOneTypeComboBox.getSelectedIndex() + "");
            properties.setProperty("checkers.playerTwo.type", playerTwoTypeComboBox.getSelectedIndex() + "");
            properties.setProperty("checkers.playerThree.type", playerThreeTypeComboBox.getSelectedIndex() + "");
            properties.setProperty("checkers.playerFour.type", playerFourTypeComboBox.getSelectedIndex() + "");
            properties.setProperty("checkers.playerFive.type", playerFiveTypeComboBox.getSelectedIndex() + "");
            properties.setProperty("checkers.playerSix.type", playerSixTypeComboBox.getSelectedIndex() + "");
            File optionsFile = (File) AssetsLoader.load("src/assets/options.json", "textFile");
            try {
                OutputStream outputStream = new FileOutputStream(optionsFile);
                properties.store(outputStream, null);
                outputStream.close();
            } catch (IOException ex) {
                displayError("Problems saving.");
            }
            window.setVisible(false);
        }
    }
}