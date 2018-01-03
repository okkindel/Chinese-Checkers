package core.model;
import java.io.Serializable;

public class Player implements Serializable {

    protected String name;
    protected boolean isAI;

    public Player(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public boolean isAI()
    {
        return false;
    }
}
