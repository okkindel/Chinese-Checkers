package kernel.frontend;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

/**
 * AbstractView Class
 * Observer, MVC.
 */
public abstract class AbstractView extends JPanel implements Observer {

    protected JFrame window;
    JPanel main_panel;
    JPanel not_main_panel;
    protected BufferedImage wallpaper;
    private BufferedImage box = (BufferedImage) AssetsLoader.load("src/assets/box.png", "image");
    private String message = "null";

    private void setMessage(String message) {
        this.message = message;
    }

    /**
     * Just paint method.
     */
    protected void paint() {
        Image icon = (Image) AssetsLoader.load("src/assets/icon.png", "image");
        window = new JFrame("Chinese Checkers");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage(icon);
        main_panel = new JPanel();
        not_main_panel = new JPanel();
    }

    /**
     * Shows error msg.
     */
    static void displayError(String errorMessage) {
        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
    }

    /**
     * Shows message
     */
    protected void show_msg(Graphics g) {
        g.drawImage(box, 50, 0, null);
        g.setFont(new Font("Sheriff", Font.PLAIN, 20));
        g.drawString(message, 100, 20);
    }

    /**
     * Overrides Observer
     */
    @Override
    public void update(Observable arg0, Object arg) {
        //we check prefix
        if (arg != null) {
            StringBuilder build = new StringBuilder((String) arg); //split
            String messageCheck = build.substring(0, 4); //check first 4 sgn
            String message = build.substring(4, build.length());

            //normal message
            if (messageCheck.equals("msg-")) {
                setMessage(message);
                //error
            } else if (messageCheck.equals("err-")) {
                JOptionPane.showMessageDialog(window, message, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Quit Menu
     */
    public class QuitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            window.setVisible(false);
            new GameMenu();
        }
    }
}