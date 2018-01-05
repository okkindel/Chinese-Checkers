package checkers.frontend;

import kernel.frontend.AssetsLoader;
import kernel.frontend.GameMenu;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameViewTest {
    GameMenu gameMenu;
    GameView gameView;

    @Test
    public void paint() throws Exception {

        gameMenu = new GameMenu();
        gameMenu.setVisible(false);

        AssetsLoader.loadConfiguration();
        gameView = new GameView();
        gameView.paint();
    }

}