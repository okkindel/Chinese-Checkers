package core.model;

import checkers.model.CheckersTile;

import java.util.ArrayList;
import java.util.Random;

public class AI extends Player {

    private ArrayList<CheckersTile> finishedTiles;
    private Random rand;

    public AI(String name)
    {
        super(name);
        isAI = true; //this is an AI, so we set it as such
        finishedTiles = new ArrayList<CheckersTile>();
        rand = new Random();
    }
}
