package core.view;

import core.model.Assets;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Menu extends AbstractView {
    public Menu() {
        draw();
    }

    void draw() {

        super.draw();
        
        JLabel imageLabel = new JLabel(new ImageIcon((BufferedImage) Assets.load("src/assets/logo.png", "image")));
        mainPanel.add(imageLabel);

        frame.add(mainPanel);
        frame.setSize(new Dimension(400, 400));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    void drawCheckersOptions(){

    }

    void drawLoadingScreen (String message) {

    }
}
