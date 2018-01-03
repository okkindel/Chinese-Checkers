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
     * Mouse listener - user interact with the graphical interface.
     */
    private class gridListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            //if both player AIs, call next player, since we can't call it before board drawn
            if (board.player_list_getter().get(0).isRobot() && board.player_list_getter().get(1).isRobot()) {
                board.nextPlayer();
                return;
            }

            //wait for a move
            if (board.local_player_getter() != null && board.active_player_getter() != board.local_player_getter()) {
                board.w8_4_move();
                return;
            }

            int pos_x, pos_y;

            //out of band
            if (!(e.getX() > start_x && e.getX() < 525 && e.getY() > start_y && e.getY() < 544)) {
                return;
            }

            //converts pixels to the tile numbers to interact with backend
            pos_x = ((e.getX() - start_x) / pawn_width);
            pos_y = ((e.getY() - start_y) / pawn_height);

            //for odd row
            if (!(pos_y % 2 == 0)) {
                pos_x = (((e.getX() - start_x) + (pawn_width / 2)) / pawn_width) - 1;
            }

            if (tile_selected == null) {
                tile_selected = board.getTiles()[pos_x][pos_y];
                drawSelected(pos_x, pos_y);
            } else {
                board.move(tile_selected, board.getTiles()[pos_x][pos_y]);
                tile_selected = null;
            }
        }
    }

    /**
     * Draws a selected piece, checks that there is a piece and belonging.
     *
     * @param pos_x The x position.
     * @param pos_y The y position.
     */
    private void drawSelected(int pos_x, int pos_y) {

        try {
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
        } catch (Exception ignore) { /*nothing here*/}
    }

    /**
     * All available moves for the user
     *
     * @param pos_x the x position.
     * @param pos_y the y position.
     */
    private void drawViableMoves(int pos_x, int pos_y) {
        //get all possible moves
        ArrayList<GameField[]> moves = board.findMoves(board.getTiles()[pos_x][pos_y]);

        //if no moves
        if (moves.isEmpty()) {
            return;
        }

        for (GameField[] move : moves) { //iterate through all the possible moves
            simpleMove(move[1].getX(), move[1].getY()); //and then paint them using the simpleMove method
        }
    }

    /**
     * Draws a move representation, on the given tile number, by converting the tile number to the pixel values for width and height
     *
     * @param pos_x the x position of the tile to paint as a viable move
     * @param pos_y the y position of the tile to paint as a viable move
     */
    private void simpleMove(int pos_x, int pos_y) {
        int wid = start_x + (pos_x * pawn_width); //convert the tile numbers to pixel values
        int hig = start_y + (pos_y * pawn_height);

        if (!(pos_y % 2 == 0)) { //if it's on an even row
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