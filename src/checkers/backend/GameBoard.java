package checkers.backend;

import java.util.ArrayList;

import kernel.backend.Robot;
import kernel.backend.AbstractBoard;
import kernel.backend.RuleInterface;

/**
 * GameBoard Class
 * Checkers logic.
 * Im fucking tired.
 */

public class GameBoard extends AbstractBoard {

    private GameField[][] fields;

    /**
     * Constructor
     */
    public GameBoard() {
        super();
        int[] order = {
                0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0,
                0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0,
                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
                0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0,
        };
        fields = new GameField[13][17];

        int counter = 0;

        for (int y = 0; y < 17; y++) {
            for (int x = 0; x < 13; x++) {
                GameField tile;
                if (order[counter] == 1) {
                    tile = new GameField(true, x, y);
                } else {
                    tile = new GameField(false, x, y);
                }
                fields[x][y] = tile;
                counter++;
            }
        }

        setupPlayers();
        set_of_rulez.add(new MovingRules());
        if (!player_list_getter().get(0).isRobot()) {
            nextPlayer();
        }
    }

    public GameField[][] getTiles() {
        return fields;
    }

    /**
     * Takes two fields and moves the piece to the destination tile.
     */
    public void move(GameField previous_area, GameField destiny_area) {
        if (previous_area.getPawn() == null) {
            show_message("msg-" + "Please select a piece");
            return;
        }
        if (destiny_area.getPawn() != null) {
            show_message("msg-" + "There's already a piece in that tile");
            return;
        }

        //we make command out of it
        GameCommands command = new GameCommands(previous_area, destiny_area);

        //if passes rulez
        for (RuleInterface set_of_rules : set_of_rulez) {
            if (!set_of_rules.checkValid(this, command)) {
                show_message("msg-" + set_of_rules.retarded_move());
                return;
            }
        }
        executor.addCommand(command);
        executor.executeCommand();
        setChanged();
        notifyObservers();

        if (check_if_win()) {
            setChanged();
            notifyObservers("msg-" + activePlayer.getName() + " wins!");
            return;
        }
        if (activePlayer.equals(local_player)) {
            super.out_msg_setter(command.getPrevious_area().coordinates_getter() + ","
                    + command.getDestiny_area().coordinates_getter());
        }
        nextPlayer();
        if (local_player != null && activePlayer != local_player) {
            w8_4_move();
        }
    }

    /**
     * Checks if the win condition.
     * Not check who has won the checkers.
     *
     * @return True if the checkers has been won.
     */
    private boolean check_if_win() {
        boolean ret = false;

        switch (player_list.indexOf(activePlayer)) {
            case 0:
                //if farthest tile contains piece belonging to the active player
                if (fields[6][16].getPawn() != null && fields[6][16].getPawn().getOwner() == player_list.get(0)) {
                    //fields that need to  be occupied by active player
                    GameField[] goalTiles = {fields[5][15], fields[6][15], fields[5][14], fields[6][14], fields[7][14],
                            fields[4][13], fields[5][13], fields[6][13], fields[7][13]};
                    ret = true;
                    for (GameField goalTile : goalTiles) {
                        if (goalTile.getPawn() == null || goalTile.getPawn().getOwner() != player_list.get(0)) {
                            ret = false;
                            break;
                        }
                    }
                }
                break;

            case 1:
                if (fields[6][0].getPawn() != null && fields[6][0].getPawn().getOwner() == player_list.get(1)) {
                    GameField[] goalTiles = {fields[5][1], fields[6][1], fields[5][2], fields[6][2], fields[7][2],
                            fields[4][3], fields[5][3], fields[6][3], fields[7][3]};
                    ret = true;
                    for (GameField goalTile : goalTiles) {
                        if (goalTile.getPawn() == null || goalTile.getPawn().getOwner() != player_list.get(1)) {
                            ret = false;
                            break;
                        }
                    }
                }
                break;

            case 2:
                if (fields[12][12].getPawn() != null && fields[12][12].getPawn().getOwner() == player_list.get(2)) {
                    GameField[] goalTiles = {fields[11][12], fields[11][11], fields[11][10], fields[10][12],
                            fields[10][11], fields[10][10], fields[11][9], fields[9][12], fields[12][11]};
                    ret = true;
                    for (GameField goalTile : goalTiles) {
                        if (goalTile.getPawn() == null || goalTile.getPawn().getOwner() != player_list.get(2)) {
                            ret = false;
                            break;
                        }
                    }
                }
                break;

            case 3:
                if (fields[0][4].getPawn() != null && fields[0][4].getPawn().getOwner() == player_list.get(3)) {
                    GameField[] goalTiles = {fields[1][4], fields[0][5], fields[2][4], fields[2][5], fields[1][6],
                            fields[3][4], fields[3][5], fields[2][6], fields[2][7]};
                    ret = true;
                    for (GameField goalTile : goalTiles) {
                        if (goalTile.getPawn() == null) {
                            ret = false;
                            break;
                        } else if (goalTile.getPawn().getOwner() != player_list.get(3)) {
                            ret = false;
                            break;
                        }
                    }
                }
                break;

            case 4:
                if (fields[0][12].getPawn() != null && fields[0][12].getPawn().getOwner() == player_list.get(4)) {
                    GameField[] goalTiles = {fields[0][12], fields[1][11], fields[1][12], fields[1][10], fields[2][11],
                            fields[2][12], fields[1][9], fields[2][10], fields[3][11], fields[3][12]};
                    ret = true;
                    for (GameField goalTile : goalTiles) {
                        if (goalTile.getPawn() == null) {
                            ret = false;
                            break;
                        } else if (goalTile.getPawn().getOwner() != player_list.get(4)) {
                            ret = false;
                            break;
                        }
                    }
                }
                break;

            case 5:
                if (fields[12][4].getPawn() != null && fields[12][4].getPawn().getOwner() == player_list.get(5)) {
                    GameField[] goalTiles = {fields[12][4], fields[11][4], fields[11][5], fields[10][4], fields[10][5],
                            fields[11][6], fields[9][4], fields[9][5], fields[10][6], fields[10][7]};
                    ret = true;
                    for (GameField goalTile : goalTiles) {
                        if (goalTile.getPawn() == null) {
                            ret = false;
                            break;
                        } else if (goalTile.getPawn().getOwner() != player_list.get(5)) {
                            ret = false;
                            break;
                        }
                    }
                }
                break;
            default:
                //should be impossible to get here
                break;
        }
        gameFinished = ret;
        return ret;
    }

    /**
     * Returns all valid moves that can be made from tile.
     *
     * @return an ArrayList of all moves that can be made from that tile.
     * <p>
     * The following might look a little complex, but it is necessary.
     * Since the movement of a piece is not in a square grid, but in a hex shape, we need to shave the corners off.
     * Since the board is comprised by shifting the rows to make it shaped correctly, this means that the corners that we need to
     * shave off are different depending on whether the row is a 'shifted' one or not.
     **/
    public ArrayList<GameField[]> findMoves(GameField start) {

        ArrayList<GameField[]> foundMoves;
        foundMoves = new ArrayList<>();

        int position_x;
        int position_y = start.getY();

        for (int xOffset = -1; xOffset < 2; xOffset++) {
            for (int yOffset = -1; yOffset < 2; yOffset++) {
                position_x = start.getX();
                boolean out_of_board = (position_x + xOffset < 0 || position_y + yOffset < 0
                        || position_x + xOffset > 12 || position_y + yOffset > 16);


                boolean shifted_row = (!(position_y % 2 == 0));
                //if we are in a shifted row, no access to top left and bottom left cells.
                boolean modify_when_shifted = (position_y + yOffset != position_y && (position_x + xOffset) == position_x - 1);
                boolean non_modify_shift = (position_y + yOffset != position_y && (position_x + xOffset) == position_x + 1);

                //if true, we should ignore that cell
                if (out_of_board || (shifted_row && modify_when_shifted) || (!shifted_row && non_modify_shift)) {
                    continue;
                }

                //we've found an empty tile for the piece to move to
                if (getTiles()[position_x + xOffset][position_y + yOffset].getPawn() == null
                        && getTiles()[position_x + xOffset][position_y + yOffset].available_getter()) {
                    GameField[] move = {start, getTiles()[position_x + xOffset][position_y + yOffset]};
                    foundMoves.add(move);
                    //there should be pieces we can jump over, and still be in bounds
                    //we need to modify the position_x accordingly to jumping over pieces and the change
                } else if (!(position_x + xOffset * 2 < 0 || position_y + yOffset * 2 < 0 ||
                        position_x + xOffset * 2 > 12 || position_y + yOffset * 2 > 16)) {
                    if (shifted_row && !((position_y + yOffset * 2) == position_y)) {
                        position_x -= 1;
                    } else if (yOffset != 0) {
                        position_x += 1;
                    }

                    //if space is free to jump
                    try {
                        boolean emptyDestination = (getTiles()[position_x + (xOffset * 2)][position_y + (yOffset * 2)].getPawn() == null);

                        //if accessible cell
                        boolean accessible = (getTiles()[position_x + (xOffset * 2)][position_y + (yOffset * 2)].available_getter());
                        if (emptyDestination && accessible) {
                            foundMoves.add(new GameField[]{start, getTiles()[position_x + (xOffset * 2)][position_y + (yOffset * 2)]});
                        }
                    } catch (Exception ignore) { /*nothing here*/}
                }
            }
        }
        return foundMoves;
    }

    /**
     * Not the best code at all, sets up all players.
     */
    private void setupPlayers() {
        //for online game
        if (local_player != null) {
            addPlayer("Participant One", false);
            fields[6][0].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][1].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][1].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][2].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][2].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[7][2].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[4][3].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][3].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][3].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[7][3].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            addPlayer("Participant Two", false);
            fields[6][16].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][15].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][15].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][14].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][14].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[7][14].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[4][13].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][13].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][13].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[7][13].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            return;
        }

        if (!System.getProperty("checkers.playerOne.type").equals("2")) {
            if (System.getProperty("checkers.playerOne.type").equals("0")) {
                addPlayer(System.getProperty("playerOne.name"), false);
            } else {
                addPlayer(System.getProperty("playerOne.name"), true);
            }
            fields[6][0].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][1].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][1].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][2].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][2].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[7][2].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[4][3].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][3].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][3].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[7][3].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
        }

        if (!System.getProperty("checkers.playerTwo.type").equals("2")) {
            if (System.getProperty("checkers.playerTwo.type").equals("0")) {
                addPlayer(System.getProperty("playerTwo.name"), false);
            } else {
                addPlayer(System.getProperty("playerTwo.name"), true);
            }
            fields[6][16].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][15].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][15].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][14].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][14].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[7][14].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[4][13].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[5][13].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[6][13].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[7][13].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
        }

        if (!System.getProperty("checkers.playerThree.type").equals("2")) {
            if (System.getProperty("checkers.playerThree.type").equals("0")) {
                addPlayer(System.getProperty("playerThree.name"), false);
            } else {
                addPlayer(System.getProperty("playerThree.name"), true);
            }
            fields[3][4].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[2][5].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[2][6].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[1][7].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[1][6].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[1][5].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[2][4].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[1][4].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[0][5].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[0][4].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
        }

        if (!System.getProperty("checkers.playerFour.type").equals("2")) {
            if (System.getProperty("checkers.playerFour.type").equals("0")) {
                addPlayer(System.getProperty("playerFour.name"), false);
            } else {
                addPlayer(System.getProperty("playerFour.name"), true);
            }
            fields[10][9].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[10][10].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[9][11].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[9][12].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[11][10].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[10][11].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[10][12].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[11][11].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[11][12].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[12][12].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
        }

        if (!System.getProperty("checkers.playerFive.type").equals("2")) {
            if (System.getProperty("checkers.playerFive.type").equals("0")) {
                addPlayer(System.getProperty("playerFive.name"), false);
            } else {
                addPlayer(System.getProperty("playerFive.name"), true);
            }
            fields[12][4].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[11][4].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[9][4].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[9][5].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[10][5].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[11][5].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[11][6].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[10][7].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[10][6].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[10][4].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
        }

        if (!System.getProperty("checkers.playerSix.type").equals("2")) {
            if (System.getProperty("checkers.playerSix.type").equals("0")) {
                addPlayer(System.getProperty("playerSix.name"), false);
            } else {
                addPlayer(System.getProperty("playerSix.name"), true);
            }
            fields[0][12].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[1][12].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[0][11].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[1][10].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[1][11].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[2][12].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[3][12].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[2][11].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[2][10].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
            fields[1][9].pawn_setter(new GamePawn(player_list_getter().get(player_list_getter().size() - 1)));
        }
    }

    /**
     * Wait for move from opponent in online game
     */
    public void w8_4_move() {
        show_message("Waiting for opponent move");

        if (activePlayer != local_player) {
            do {
                while (msg_in == null) { /*message is received and we need to parse it*/ }
                String[] split = msg_in.split(",");
                GameField previous = getTiles()[Integer.parseInt(split[0])][Integer.parseInt(split[1])];
                GameField destiny = getTiles()[Integer.parseInt(split[2])][Integer.parseInt(split[3])];
                msg_in = null;
                move(previous, destiny);
                return;
            } while (activePlayer != local_player);
        }
    }

    /**
     * Switches players.
     */
    public void nextPlayer() {
        super.nextPlayer();

        if (activePlayer.isRobot()) {
            Robot bot = (Robot) activePlayer;
            bot.checkersMove(this);
        }
    }
}