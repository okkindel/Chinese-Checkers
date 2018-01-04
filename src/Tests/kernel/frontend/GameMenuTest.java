package kernel.frontend;

import org.junit.Test;

public class GameMenuTest {
    GameMenu menu;

    @Test
    public void paint() throws Exception {
        new GameMenu();
    }

    @Test
    public void play() throws Exception {
        menu = new GameMenu();
        menu.play();
    }

}