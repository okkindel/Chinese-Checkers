package checkers.model;

import core.model.Board;
import core.model.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertSame;


public class CheckersBoardTest {
    CheckersBoard board;
    CheckersTile [][] tiles;
    Player player;
    ArrayList<Player> players;

    @Test
    public void getTiles() throws Exception {
        board = new CheckersBoard();
        boolean equals = false;
        if(board.getTiles()[0][0] instanceof CheckersTile){
            equals = true;
        }

        assertSame(true, equals);

        //tu jest coś spierdolone, to że assert equals i same odwołują się do
        //różnych referencji do obiektów
        //zwracanie działa poprawnie tylko, żeby je sprawdzić poprawnie
        //trzeba zmienić tablice CheckersTile[][] w CheckersBoard na publiczną
        //gdyż na razie jest prywatna

    }

    @Test
    public void nextPlayer() throws Exception {
        //TODO: (nie mam nadal servera)

    }

    @Test
    public void findMoves() throws Exception {
        //TODO:
    }

}