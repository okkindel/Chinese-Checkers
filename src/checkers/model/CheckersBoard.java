package checkers.model;

import core.model.AI;
import core.model.Board;

public class CheckersBoard extends Board {

    private CheckersTile[][] tiles;

    public CheckersBoard() {
        super();

        int[] schedule = {

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
        tiles = new CheckersTile[13][17];

        int counter = 0;

        for (int y = 0; y < 17; y++) {
            for (int x = 0; x < 13; x++) {
                CheckersTile tile;
                if (schedule[counter] == 1)
                    tile = new CheckersTile(true, x, y);
                else
                    tile = new CheckersTile(false, x, y);
                tiles[x][y] = tile;
                counter++;
            }
        }

        setupPlayers();
        rules.add(new MoveRule());

        if (!getPlayers().get(0).isAI()) {
            nextPlayer();
        }
    }

    public CheckersTile[][] getTiles() {
        return tiles;
    }

    private void setupPlayers() {
        if (localPlayer != null) {
            //if the local player is set, network mode is enabled
            addPlayer("Player One", false);
            tiles[6][0].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][1].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][1].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][2].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][2].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[7][2].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[4][3].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][3].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][3].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[7][3].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            addPlayer("Player Two", false);
            tiles[6][16].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][15].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][15].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][14].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][14].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[7][14].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[4][13].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][13].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][13].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[7][13].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));

            return;
        }

        if (!System.getProperty("checkers.playerOne.type").equals("2")) { //if it's not two then this player is enabled
            if (System.getProperty("checkers.playerOne.type").equals("0")) { //zero means human
                addPlayer(System.getProperty("playerOne.name"), false); //so we create it using the name stored in properties
            } else {
                addPlayer(System.getProperty("playerOne.name"), true); //otherwise the player type should only ever be 1, which equates to AI
            }
            tiles[6][0].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1))); //after this all that remains is to actually add the pieces to the board
            tiles[5][1].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][1].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][2].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][2].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[7][2].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[4][3].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][3].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][3].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[7][3].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
        }

        if (!System.getProperty("checkers.playerTwo.type").equals("2")) {
            if (System.getProperty("checkers.playerTwo.type").equals("0")) {
                addPlayer(System.getProperty("playerTwo.name"), false);
            } else {
                addPlayer(System.getProperty("playerTwo.name"), true);
            }
            tiles[6][16].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][15].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][15].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][14].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][14].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[7][14].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[4][13].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[5][13].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[6][13].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[7][13].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
        }

        if (!System.getProperty("checkers.playerThree.type").equals("2")) {
            if (System.getProperty("checkers.playerThree.type").equals("0")) {
                addPlayer(System.getProperty("playerThree.name"), false);
            } else {
                addPlayer(System.getProperty("playerThree.name"), true);
            }
            tiles[3][4].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[2][5].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[2][6].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[1][7].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[1][6].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[1][5].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[2][4].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[1][4].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[0][5].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[0][4].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
        }

        if (!System.getProperty("checkers.playerFour.type").equals("2")) {
            if (System.getProperty("checkers.playerFour.type").equals("0")) {
                addPlayer(System.getProperty("playerFour.name"), false);
            } else {
                addPlayer(System.getProperty("playerFour.name"), true);
            }
            tiles[10][9].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[10][10].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[9][11].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[9][12].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[11][10].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[10][11].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[10][12].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[11][11].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[11][12].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[12][12].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
        }

        if (!System.getProperty("checkers.playerFive.type").equals("2")) {
            if (System.getProperty("checkers.playerFive.type").equals("0")) {
                addPlayer(System.getProperty("playerFive.name"), false);
            } else {
                addPlayer(System.getProperty("playerFive.name"), true);
            }
            tiles[12][4].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[11][4].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[9][4].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[9][5].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[10][5].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[11][5].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[11][6].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[10][7].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[10][6].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[10][4].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
        }

        if (!System.getProperty("checkers.playerSix.type").equals("2")) {
            if (System.getProperty("checkers.playerSix.type").equals("0")) {
                addPlayer(System.getProperty("playerSix.name"), false);
            } else {
                addPlayer(System.getProperty("playerSix.name"), true);
            }
            tiles[0][12].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[1][12].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[0][11].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[1][10].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[1][11].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[2][12].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[3][12].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[2][11].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[2][10].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
            tiles[1][9].setPiece(new CheckersPiece(getPlayers().get(getPlayers().size() - 1)));
        }
    }

    public void nextPlayer() {
        super.nextPlayer();

        if (activePlayer.isAI()) {
            AI bot = (AI) activePlayer;
            bot.checkersMove(this);
        }
    }

    private boolean checkWinCondition() {
        boolean have_been_won = false;

        switch (players.indexOf(activePlayer)) {
            case 0:
                //do a quick check to see if the farthest tile contains a piece belonging to the active
                // player, to save checking all the goal tiles unnecessarily
                if (tiles[6][16].getPiece() != null && tiles[6][16].getPiece().getOwner() == players.get(0)) {
                    //these are the tiles that need to occupied by the active player for them to win
                    CheckersTile[] goalTiles = {tiles[5][15], tiles[6][15], tiles[5][14], tiles[6][14], tiles[7][14],
                            tiles[4][13], tiles[5][13], tiles[6][13], tiles[7][13]};
                    have_been_won = true;
                    for (int i = 0; i < goalTiles.length; i++) {
                        if (goalTiles[i].getPiece() == null || goalTiles[i].getPiece().getOwner() != players.get(0)) {
                            have_been_won = false;
                            //break out since all the pieces in the goal tiles must be owned by the active player, and all must be in
                            // the goal tiles for the active player to have won, so no point checking any others
                            break;
                        }
                    }
                }
                break;

            case 1:
                if (tiles[6][0].getPiece() != null && tiles[6][0].getPiece().getOwner() == players.get(1)) {
                    CheckersTile[] goalTiles = {tiles[5][1], tiles[6][1], tiles[5][2], tiles[6][2], tiles[7][2],
                            tiles[4][3], tiles[5][3], tiles[6][3], tiles[7][3]};
                    have_been_won = true;
                    for (int i = 0; i < goalTiles.length; i++) {
                        if (goalTiles[i].getPiece() == null || goalTiles[i].getPiece().getOwner() != players.get(1)) {
                            have_been_won = false;
                            break;
                        }
                    }
                }
                break;

            case 2:
                if (tiles[12][12].getPiece() != null && tiles[12][12].getPiece().getOwner() == players.get(2)) {
                    CheckersTile[] goalTiles = {tiles[11][12], tiles[11][11], tiles[11][10], tiles[10][12],
                            tiles[10][11], tiles[10][10], tiles[11][9], tiles[9][12], tiles[12][11]};
                    have_been_won = true;
                    for (int i = 0; i < goalTiles.length; i++) {
                        if (goalTiles[i].getPiece() == null || goalTiles[i].getPiece().getOwner() != players.get(2)) {
                            have_been_won = false;
                            break;
                        }
                    }
                }
                break;

            case 3:
                if (tiles[0][4].getPiece() != null && tiles[0][4].getPiece().getOwner() == players.get(3)) {
                    CheckersTile[] goalTiles = {tiles[1][4], tiles[0][5], tiles[2][4], tiles[2][5], tiles[1][6],
                            tiles[3][4], tiles[3][5], tiles[2][6], tiles[2][7]};
                    have_been_won = true;
                    for (int i = 0; i < goalTiles.length; i++) {
                        if (goalTiles[i].getPiece() == null) {
                            have_been_won = false;
                            break;
                        } else if (goalTiles[i].getPiece().getOwner() != players.get(3)) {
                            have_been_won = false;
                            break;
                        }
                    }
                }
                break;

            case 4:
                if (tiles[0][12].getPiece() != null && tiles[0][12].getPiece().getOwner() == players.get(4)) {
                    CheckersTile[] goalTiles = {tiles[0][12], tiles[1][11], tiles[1][12], tiles[1][10], tiles[2][11],
                            tiles[2][12], tiles[1][9], tiles[2][10], tiles[3][11], tiles[3][12]};
                    have_been_won = true;
                    for (int i = 0; i < goalTiles.length; i++) {
                        if (goalTiles[i].getPiece() == null) {
                            have_been_won = false;
                            break;
                        } else if (goalTiles[i].getPiece().getOwner() != players.get(4)) {
                            have_been_won = false;
                            break;
                        }
                    }
                }
                break;

            case 5:
                if (tiles[12][4].getPiece() != null && tiles[12][4].getPiece().getOwner() == players.get(5)) {
                    CheckersTile[] goalTiles = {tiles[12][4], tiles[11][4], tiles[11][5], tiles[10][4], tiles[10][5],
                            tiles[11][6], tiles[9][4], tiles[9][5], tiles[10][6], tiles[10][7]};
                    have_been_won = true;
                    for (int i = 0; i < goalTiles.length; i++) {
                        if (goalTiles[i].getPiece() == null) {
                            have_been_won = false;
                            break;
                        } else if (goalTiles[i].getPiece().getOwner() != players.get(5)) {
                            have_been_won = false;
                            break;
                        }
                    }
                }
                break;
            default:
                //should never get here, but just in case
                break;
        }
        gameEnd = have_been_won;
        return have_been_won;
    }


}
