package checkers.frontend;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import checkers.backend.GameField;
import kernel.frontend.AssetsLoader;
import checkers.backend.GameBoard;
import checkers.backend.GamePawn;

import kernel.frontend.AbstractView;

/**
 * BoardView Class
 * Graphical component.
 */

public class BoardView extends AbstractView implements Observer {

    private int start_x = 174;
    private int start_y = 85;
    private int pawn_height = 27;
    private int pawn_width = 27;
    private BufferedImage players_pawn_image;
    private BufferedImage selected_image;
    private BufferedImage available_move;
    private GameField tile_selected;
    private GameBoard board;

    GameBoard getBoard() {
        return board;
    }

    /**
     * Constructor
     */
    BoardView(GameBoard board) {
        this.board = board;
        wallpaper = (BufferedImage) AssetsLoader.load("src/assets/board.png", "image");
        selected_image = (BufferedImage) AssetsLoader.load("src/assets/pawn_selected.png", "image");
        available_move = (BufferedImage) AssetsLoader.load("src/assets/area_checked.png", "image");
        addMouseListener(new gridListener());
        tile_selected = null;
        board.addObserver(this);
    }

    /**
     * Update in AbstractView.
     *
     * @param observable Being observed
     * @param object     Observed object
     */
    @Override
    public void update(Observable observable, Object object) {
        super.update(observable, object);
        //redraw this board
        paint(getGraphics());
    }

    /**
     * Mouse listener that performs the tasks needed for the user to interact with the graphical interface
     */
    private class gridListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            //if both player AIs, call next player, since we can't call it before board drawn
            if (board.player_list_getter().get(0).isRobot() && board.player_list_getter().get(1).isRobot()) {
                board.nextPlayer();
                return;
            }

            if (board.local_player_getter() != null && board.active_player_getter() != board.local_player_getter()) { //if the local player has been set, and the active player isn't the player who owns this board, we want to wait for a move
                board.w8_4_move();
                return;
            }

            int xPos, yPos;

            if (!(e.getX() > start_x && e.getX() < 525 && e.getY() > start_y && e.getY() < 544)) { //user clicked outside of the checkers area
                return; //so we don't need to do anything else and we can return
            }

            xPos = ((e.getX() - start_x) / pawn_width); //this division converts the position in pixels to the tile numbers needed to interact with the backend
            yPos = ((e.getY() - start_y) / pawn_height);

            if (!(yPos % 2 == 0)) { //if it's on an odd row, (1,3,5)
                xPos = (((e.getX() - start_x) + (pawn_width / 2)) / pawn_width) - 1; //+13 to the x position, so that the tile is shifted over half a tile's worth of x, the -1 puts it back in bounds for checking against arrays etc
            }

            if (tile_selected == null) { //if the player hasn't selected a tile
                tile_selected = board.getTiles()[xPos][yPos]; //then we select a tile
                drawSelected(xPos, yPos); //and paint that the user selected that tile
            } else {
                board.move(tile_selected, board.getTiles()[xPos][yPos]); //otherwise, the user has a selected tile, so we move the piece to the new tile
                tile_selected = null; //and set the placeholder for the selected tile to null so the player can select a tile next turn
            }
        }
    }

    /**
     * Draws a selected piece, checks that there is a piece and belonging.
     *
     * @param pos_x The x position.
     * @param pos_y The y position.
     * */
    private void drawSelected(int pos_x, int pos_y) {

        if (board.getTiles()[pos_x][pos_y].getPawn().getOwner() != board.active_player_getter()) {
            update(board, "msg-" + "Not yours pawn");
            tile_selected = null;
            return;
        }
        if (board.getTiles()[pos_x][pos_y].getPawn() == null) {
            update(board, "msg-" + "Select a piece");
            tile_selected = null;
            return;
        }

        int width = start_x + (pos_x * pawn_width);
        int height = start_y + (pos_y * pawn_height);

        //for odd row
        if (!(pos_y % 2 == 0)) {
            width += (pawn_width / 2);
        }

        getGraphics().drawImage(selected_image, width, height, null);
        drawViableMoves(pos_x, pos_y);
    }

    /**
     * Uses the drawViableMove method to paint all the available moves for the user
     *
     * @param x the x position of the source tile
     * @param y the y position of the source tile
     */
    private void drawViableMoves(int x, int y) {
        ArrayList<GameField[]> moves = board.findMoves(board.getTiles()[x][y]); //get all the possible moves that the source tile can make

        if (moves.isEmpty()) { // if there are no moves, we simply return, since there's nothing more we can do
            return;
        }

        for (GameField[] move : moves) { //iterate through all the possible moves
            drawViableMove(move[1].getX(), move[1].getY()); //and then paint them using the drawViableMove method
        }
    }

    /**
     * Draws a move representation, on the given tile number, by converting the tile number to the pixel values for width and height
     *
     * @param xPos the x position of the tile to paint as a viable move
     * @param yPos the y position of the tile to paint as a viable move
     */
    private void drawViableMove(int xPos, int yPos) {
        int wid = start_x + (xPos * pawn_width); //convert the tile numbers to pixel values
        int hig = start_y + (yPos * pawn_height);

        if (!(yPos % 2 == 0)) { //if it's on an even row
            wid += (pawn_width / 2); //then we shift it over half a space
        }

        getGraphics().drawImage(available_move, wid, hig, null); //performs the actual drawing, using the pixel values
    }

    /**
     * Overrides the paint method of JComponent, to paint the necessities needed for the Checkers AbstractBoard
     *
     * @param g The graphics object used to paint the images required
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(wallpaper, 0, 0, null); //paint the wallpaper image at the top left pixel

        //paint pieces start
        int wid = start_x;
        int hig = start_y;
        boolean ev = true; //placeholder value denoting whether currently on a even row (0,2 etc)

        int y;
        int x;

        for (y = 0; y < 17; y++) { //iterate through the board
            for (x = 0; x < 13; x++) {
                if (board.getTiles()[x][y].getPawn() != null) { //if there is a piece in the tile
                    switch (board.player_list_getter().indexOf(board.getTiles()[x][y].getPawn().getOwner())) { //get the owner
                        case 0:
                            players_pawn_image = (BufferedImage) AssetsLoader.load("src/assets/pawn_blue.png", "image");
                            break;
                        case 1:
                            players_pawn_image = (BufferedImage) AssetsLoader.load("src/assets/pawn_red.png", "image");
                            break;
                        case 2:
                            players_pawn_image = (BufferedImage) AssetsLoader.load("src/assets/pawn_yellow.png", "image");
                            break;
                        case 3:
                            players_pawn_image = (BufferedImage) AssetsLoader.load("src/assets/pawn_green.png", "image");
                            break;
                        case 4:
                            players_pawn_image = (BufferedImage) AssetsLoader.load("src/assets/pawn_white.png", "image");
                            break;
                        case 5:
                            players_pawn_image = (BufferedImage) AssetsLoader.load("src/assets/pawn_black.png", "image");
                            break;
                        default:
                            break;
                    }
                    g.drawImage(players_pawn_image, wid, hig, null); //and then paint the piece
                }
                wid += pawn_width; //add to the pixel value for the width on which we paint pieces
            }
            hig += pawn_height; //and add to the height value for where we paint
            wid = start_x; //then reset the width value since we exited the inner loop
            if (ev) { //if we're on an even row, then we need to shift the tile over half the width of a tile
                wid += (pawn_width / 2);
                ev = false; //and then reset it for the next row
            } else { //if we're not on an even row then we only need to reset the variable
                ev = true;
            }
        }
        //paint pieces end
        show_msg(getGraphics()); //now that we've repainted, we redraw the message in case it's also changed
    }
    /**
     * Shows the available moves - hint.
     */
    void showAvailableMoves() {
        //whole board
        for (int y = 0; y < 17; y++) {
            //which tiles are accessible
            for (int x = 0; x < 13; x++) {
                GamePawn piece = board.getTiles()[x][y].getPawn();
                if (piece != null && piece.getOwner() == board.active_player_getter()) {
                    drawViableMoves(x, y);
                }
            }
        }
    }
}