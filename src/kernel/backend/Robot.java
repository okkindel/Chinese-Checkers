package kernel.backend;

import java.util.ArrayList;
import java.util.Random;

import checkers.backend.*;

/**
 * Robot Class
 * The AI logic that can play chinese checkers etc... i hope
 */
public class Robot extends Participant {

    //used in checkers, AI has an idea of which tiles it should still send pieces towards, and which tiles it has filled
    private ArrayList<GameField> finishedTiles;
    private Random random;

    /**
     * Constructor for Robot.
     */
    Robot(String name) {
        super(name); //sets the name, using the superclass constructor
        isRobot = true; //lel
        finishedTiles = new ArrayList<>();
        random = new Random();
    }

    /**
     * Finds a move, calculating a good move to take.
     * Just kidding, strait forward XDDDDD
     * @param board The board on which to find a move
     */
    public void checkersMove(GameBoard board) {
        GameField goalArea = null;
        GameField[] goalTiles = new GameField[15];
        GameField previous_field, destiny_field;
        ArrayList<GameField[]> possible_moves = new ArrayList<GameField[]>();
        int bestMove;

        //goal tiles
        switch (board.player_list.indexOf(this)) {
            case 0:
                goalTiles = new GameField[]{board.getTiles()[6][16], board.getTiles()[5][15],
                        board.getTiles()[6][15], board.getTiles()[5][14], board.getTiles()[6][14],
                        board.getTiles()[7][14], board.getTiles()[4][13], board.getTiles()[5][13],
                        board.getTiles()[6][13], board.getTiles()[7][13], board.getTiles()[7][12],
                        board.getTiles()[6][12], board.getTiles()[5][12], board.getTiles()[4][12]};
                break;
            case 1:
                goalTiles = new GameField[]{board.getTiles()[6][0], board.getTiles()[5][1],
                        board.getTiles()[6][1], board.getTiles()[5][2], board.getTiles()[6][2],
                        board.getTiles()[7][2], board.getTiles()[4][3], board.getTiles()[5][3],
                        board.getTiles()[6][3], board.getTiles()[7][3], board.getTiles()[4][4],
                        board.getTiles()[5][4], board.getTiles()[6][4], board.getTiles()[7][4]};
                break;
            case 2:
                goalTiles = new GameField[]{board.getTiles()[12][12], board.getTiles()[11][12],
                        board.getTiles()[11][10], board.getTiles()[10][12], board.getTiles()[10][11],
                        board.getTiles()[10][10], board.getTiles()[11][9], board.getTiles()[9][12],
                        board.getTiles()[9][9], board.getTiles()[9][10], board.getTiles()[9][11],
                        board.getTiles()[9][12], board.getTiles()[10][8], board.getTiles()[10][7]};
                break;
            case 3:
                goalTiles = new GameField[]{board.getTiles()[0][4], board.getTiles()[1][4],
                        board.getTiles()[0][5], board.getTiles()[2][4], board.getTiles()[2][5],
                        board.getTiles()[1][6], board.getTiles()[3][4], board.getTiles()[3][5],
                        board.getTiles()[2][6], board.getTiles()[2][7], board.getTiles()[2][8],
                        board.getTiles()[3][8], board.getTiles()[3][9], board.getTiles()[3][10]};
                break;
            case 4:
                goalTiles = new GameField[]{board.getTiles()[0][12], board.getTiles()[1][11],
                        board.getTiles()[1][12], board.getTiles()[1][10], board.getTiles()[2][11],
                        board.getTiles()[2][12], board.getTiles()[1][9], board.getTiles()[2][10],
                        board.getTiles()[3][11], board.getTiles()[3][12], board.getTiles()[3][10],
                        board.getTiles()[3][9], board.getTiles()[3][8], board.getTiles()[4][10]};
                break;
            case 5:
                goalTiles = new GameField[]{board.getTiles()[12][4], board.getTiles()[11][4],
                        board.getTiles()[11][5], board.getTiles()[10][4], board.getTiles()[10][5],
                        board.getTiles()[11][6], board.getTiles()[9][4], board.getTiles()[9][5],
                        board.getTiles()[10][6], board.getTiles()[10][7], board.getTiles()[8][4],
                        board.getTiles()[8][5], board.getTiles()[8][6], board.getTiles()[8][7]};
                break;
            default:
                break;
        }

        //determining which tile is currently optimal
        for (GameField goal : goalTiles) {
            if (goal.getPawn() == null) {
                goalArea = goal;
                break;
            }
        }

        //available moves for each piece, is not already in a goal
        for (int y = 0; y < 17; y++) {
            for (int x = 0; x < 13; x++) {
                if (board.getTiles()[x][y].getPawn() != null && board.getTiles()[x][y].getPawn().getOwner() == this) {
                    if (finishedTiles.contains(board.getTiles()[x][y])) {
                        continue;
                    }
                    possible_moves.addAll(board.findMoves(board.getTiles()[x][y]));
                }
            }
        }

        if (possible_moves.isEmpty()) {
            board.show_message("msg-" + "no moves for " + name);
            return;
        } else {
            bestMove = random.nextInt(possible_moves.size());
        }
        previous_field = possible_moves.get(bestMove)[0];
        destiny_field = possible_moves.get(bestMove)[1];
        assert goalArea != null;
        if (destiny_field.getX() == goalArea.getX() && destiny_field.getY() == goalArea.getY()) {
            finishedTiles.add(destiny_field);
        }
        board.move(previous_field, destiny_field);
    }

    @Override
    public boolean isRobot() {
        return true;
    }
}
