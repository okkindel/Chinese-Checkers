package core.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class HumanTest {
    @Test
    public void isAI() throws Exception {
        Human human = new Human("Hajduk");
        assertEquals(false,human.isAI());
    }

}