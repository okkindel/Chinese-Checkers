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
    private int start_y = 100;
    private int pawn_height = 27;
    private int pawn_width = 27;
    private BufferedImage players_pawn_image;
    private BufferedImage selected_image;
    private BufferedImage available_move;
    private GameField tile_selected;
    public GameBoard board;

    GameBoard getBoard() {
        return board;
    }

    /**
     * Constructor
     */
    public BoardView(GameBoard board) {
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
     * Overrides the paint method.
     */
    @Override
    public void paint(Graphics graphics) {
        graphics.drawImage(wallpaper, 0, 0, null);

        int width = start_x;
        int height = start_y;
        boolean even = true;
        int x, y;

        for (y = 0; y < 17; y++) {
            for (x = 0; x < 13; x++) {
                if (board.getTiles()[x][y].getPawn() != null) {
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
                    graphics.drawImage(players_pawn_image, width, height, null);
                }
                else if (board.getTiles()[x][y].available_getter()) {
                    players_pawn_image = (BufferedImage) AssetsLoader.load("src/assets/area_checked.png", "image");
                    graphics.drawImage(players_pawn_image, width, height, null);
                }
                width += pawn_width;
            }
            height += pawn_height;
            width = start_x;
            if (even) {
                width += (pawn_width / 2);
                even = false;
            } else {
                even = true;
            }
        }
        show_msg(getGraphics());
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
    protected void drawSelected(int pos_x, int pos_y) {

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
            draw_all_moves(pos_x, pos_y);
        } catch (Exception ignore) { /*nothing here*/}
    }

    /**
     * All available moves for the user
     *
     * @param pos_x the x position.
     * @param pos_y the y position.
     */
    private void draw_all_moves(int pos_x, int pos_y) {
        //get all possible moves
        ArrayList<GameField[]> moves = board.findMoves(board.getTiles()[pos_x][pos_y]);

        //if no moves
        if (moves.isEmpty()) {
            return;
        }

        for (GameField[] move : moves) {
            //paint them
            draw_single_move(move[1].getX(), move[1].getY());
        }
    }

    /**
     * Draws a move.
     *
     * @param pos_x the x position
     * @param pos_y the y position
     */
    private void draw_single_move(int pos_x, int pos_y) {
        //convert the tile numbers to pixel values
        int width = start_x + (pos_x * pawn_width);
        int height = start_y + (pos_y * pawn_height);

        //for odd row
        if (!(pos_y % 2 == 0)) {
            width += (pawn_width / 2);
        }
        getGraphics().drawImage(available_move, width, height, null);
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
                    draw_all_moves(x, y);
                }
            }
        }
    }
}