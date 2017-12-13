package core.view;

import core.model.Assets;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractView extends JPanel {

    JFrame frame;
    JPanel mainPanel;
    JPanel underPanel;

    void draw() {
        frame = new JFrame("Chinese Checkers");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Image icon = (Image) Assets.load("src/assets/icon.png", "image");
        frame.setIconImage(icon);

        mainPanel = new JPanel();
        underPanel = new JPanel();
    }

    public static void showError(String error) {
        JOptionPane.showMessageDialog(new JFrame(), error);
    }
}
