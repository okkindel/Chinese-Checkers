package core.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player;


    @Test
    public void getName() throws Exception {
        player = new Player("Bartek");
        assertEquals("Bartek", player.getName());
    }

    @Test
    public void isAI() throws Exception {
        player = new Player("Bartek");
        assertEquals(false, player.isAI());
    }

}