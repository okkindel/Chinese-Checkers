package checkers.model;

import checkers.model.CheckersPiece;
import checkers.model.CheckersTile;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CheckersTileTest {

    CheckersTile tile;
    CheckersPiece piece;

    @Test
    public void getAccessible() throws Exception {
        tile = new CheckersTile(true, 1, 1);
        assertEquals(true,(tile.getAccessible()));

        tile = new CheckersTile(false, 1, 1);
        assertEquals(false,(tile.getAccessible()));
    }

    @Test
    public void getPiece() throws Exception {
        tile = new CheckersTile(true , 1, 1);
        piece = mock(CheckersPiece.class);
        tile.setPiece(piece);
        assertSame(piece, tile.getPiece());
    }

    @Test
    public void setPiece() throws Exception {
        tile = new CheckersTile(true , 1, 1);
        piece = mock(CheckersPiece.class);
        tile.setPiece(piece);
        assertSame(piece, tile.getPiece());
    }

    @Test
    public void getX() throws Exception {
        tile = new CheckersTile(true , 1, 1);
        assertEquals(1, tile.getX());
    }

    @Test
    public void getY() throws Exception {
        tile = new CheckersTile(true , 1, 1);
        assertEquals(1, tile.getY());
    }


    @Test
    public void getCoords() throws Exception {
        tile = new CheckersTile(true , 1, 1);
        assertEquals("1,1", tile.getCoords());
    }

}