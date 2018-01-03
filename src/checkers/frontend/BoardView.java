/**
 * BoardView Class
 * Graphical component that reflects the backend and user interface of the Checkers AbstractBoard.
 */

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

public class BoardView extends AbstractView implements Observer {

	//variable assignment here
	private int xStart = 174; //the width pixel value at which the interactive section of the board begins
	private int yStart = 85; //the height pixel value at which the interactive section of the board begins
	private int tileHeight = 27; //the height of a tile in pixels
	private int tileWidth = 27; //the width of a tile in pixels
	private GameBoard board; //the board to reflect
	private BufferedImage pieceImage; //image representing a player's piece
	private BufferedImage selectedImage; //image representing a player's piece
	private BufferedImage viableMoveImage; //image representing a viable move
	private GameField selectedTile; //image representing a selected tile

	//constructors here
	/**
	 * Default constructor for the Checkers AbstractBoard View
	 * 
	 * @param board The backend representation of the board
	 */
	protected BoardView(GameBoard board)
	{
		//variables passed in
		this.board = board;

		//variables not passed in		
		wallpaper = (BufferedImage) AssetsLoader.load("src/assets/board.png", "image");
		selectedImage = (BufferedImage) AssetsLoader.load("src/assets/pawn_selected.png", "image");
		viableMoveImage = (BufferedImage) AssetsLoader.load("src/assets/area_checked.png", "image");
		addMouseListener(new MouseGridListener()); //add the mouseListener so the user can interact with the checkers
		selectedTile = null; //this is a placeholder value, since a turn is taken in two steps: selecting, and then placing
		board.addObserver(this); //add this object as an observer for the board, so that this object can update accordingly
	}

	//gets here
	public GameBoard getBoard()
	{
		return board;
	}

	//sets here

	//methods here
	/**
	 * Overrides update in AbstractView. Updates this graphical component.
	 * 
	 * @param arg0 The object that is being observed
	 * @param arg The message passed from the observed object
	 */
	@Override
	public void update(Observable arg0, Object arg)
	{
		super.update(arg0, arg); //call the superclass method with the parameters, this should filter out anything relevant to all the games, such as the message box updating

		paint(getGraphics()); //then redraw this board
	}
	
	/**
	 * Draws a selected piece, also does some checks that there is a piece and that the piece belongs to the correct person.
	 * 
	 * If either of the checks fails, simply updates the message box to reflect this instead.
	 * 
	 * @param xPos The x position of the tile to paint as selected
	 * @param yPos The y position of the tile to paint as selected
	 */
	private void drawSelected(int xPos, int yPos) 
	{
		if (board.getTiles()[xPos][yPos].getPawn() == null) { //this means there's no piece in the tile
			update(board,"msg-"+"Please select a piece"); //so we tell the user as such
			selectedTile = null; //reset the selected tile so the user can select another tile
			return; //and return from the method since we don't need to do any more here
		}
		
		if (board.getTiles()[xPos][yPos].getPawn().getOwner() != board.active_player_getter()) { //this means that the active player is not the owner of the piece
			update(board,"msg-"+"You don't own that piece"); //so we inform the user
			selectedTile = null; //reset the selected tile
			return; //and return from the method
		}
		
		int wid = xStart + (xPos * tileWidth); //if we get here it means the selection is valid, so we convert the x and y to the pixel values of x and y
		int hig = yStart + (yPos * tileHeight);
		
		if (!(yPos%2 == 0)) { //if it's on an odd row, (1,3,5)
			wid += (tileWidth/2); //then we shift it over half a space
		}
		
		getGraphics().drawImage(selectedImage, wid, hig, null); //and paint the selected tile image over the piece image that should already be in place
		drawViableMoves(xPos,yPos); //then from here we can paint the viable moves of the selection
	}
	
	/**
	 * Uses the drawViableMove method to paint all the available moves for the user
	 * 
	 * @param x the x position of the source tile
	 * @param y the y position of the source tile
	 */
	private void drawViableMoves(int x, int y)
	{
		ArrayList<GameField[]> moves = board.findMoves(board.getTiles()[x][y]); //get all the possible moves that the source tile can make
		
		if (moves.isEmpty()) { // if there are no moves, we simply return, since there's nothing more we can do
			return;
		}
		
		for (int i = 0; i < moves.size(); i++) { //iterate through all the possible moves
			drawViableMove(moves.get(i)[1].getX(), moves.get(i)[1].getY()); //and then paint them using the drawViableMove method
		}
	}
	
	/**
	 * Draws a move representation, on the given tile number, by converting the tile number to the pixel values for width and height
	 * 
	 * @param xPos the x position of the tile to paint as a viable move
	 * @param yPos the y position of the tile to paint as a viable move
	 */
	private void drawViableMove(int xPos, int yPos)
	{
		int wid = xStart + (xPos * tileWidth); //convert the tile numbers to pixel values
		int hig = yStart + (yPos * tileHeight);
		
		if (!(yPos%2 == 0)) { //if it's on an even row
			wid += (tileWidth/2); //then we shift it over half a space
		}
		
		getGraphics().drawImage(viableMoveImage, wid, hig, null); //performs the actual drawing, using the pixel values
	}
	
	/**
	 * Overrides the paint method of JComponent, to paint the necessities needed for the Checkers AbstractBoard
	 * 
	 * @param g The graphics object used to paint the images required
	 */
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(wallpaper, 0, 0, null); //paint the wallpaper image at the top left pixel

		//paint pieces start
		int wid = xStart;
		int hig = yStart;
		boolean ev = true; //placeholder value denoting whether currently on a even row (0,2 etc)

		int y = 0;
		int x = 0;

		for (y = 0; y < 17; y++) { //iterate through the board
			for (x = 0; x < 13; x++) {
				if(board.getTiles()[x][y].getPawn() != null) { //if there is a piece in the tile
					switch(board.player_list_getter().indexOf(board.getTiles()[x][y].getPawn().getOwner())){ //get the owner
					case 0: pieceImage = (BufferedImage) AssetsLoader.load("src/assets/pawn_blue.png", "image");break; //get the image for the colour of piece to use
					case 1: pieceImage = (BufferedImage) AssetsLoader.load("src/assets/pawn_red.png", "image");break;
					case 2: pieceImage = (BufferedImage) AssetsLoader.load("src/assets/pawn_yellow.png", "image");break;
					case 3: pieceImage = (BufferedImage) AssetsLoader.load("src/assets/pawn_green.png", "image");break;
					case 4: pieceImage = (BufferedImage) AssetsLoader.load("src/assets/pawn_white.png", "image");break;
					case 5: pieceImage = (BufferedImage) AssetsLoader.load("src/assets/pawn_black.png", "image");break;
					default: break;
					}
					g.drawImage(pieceImage,wid,hig,null); //and then paint the piece
				}
				wid += tileWidth; //add to the pixel value for the width on which we paint pieces
			}
			hig += tileHeight; //and add to the height value for where we paint
			wid = xStart; //then reset the width value since we exited the inner loop
			if (ev) { //if we're on an even row, then we need to shift the tile over half the width of a tile
				wid += (tileWidth/2);
				ev = false; //and then reset it for the next row
			}
			else { //if we're not on an even row then we only need to reset the variable
				ev = true;
			}
		}
		//paint pieces end
		show_msg(getGraphics()); //now that we've repainted, we redraw the message in case it's also changed
	}
	
	/**
	 * Shows the available moves as a hint to the player. Available moves should show as a green highlight.
	 */
	protected void showAvailableMoves() 
	{
		for (int y = 0;y<17;y++) { //iterates through the whole board
			for (int x = 0;x<13;x++) { //this works by using the array of integers to decide which tiles are accessible, and then adding that tile to the array of tiles
				GamePawn piece = board.getTiles()[x][y].getPawn(); //gets the piece in the tile we're currently looking in
				if (piece != null && piece.getOwner() == board.active_player_getter()) { //if the piece belongs to the user that asked for the hint
					drawViableMoves(x, y); //then we call the viable moves method, which draws all the viable moves for that piece
				}
			}
		}
	}

	/**
	 * Mouse listener that performs the tasks needed for the user to interact with the graphical interface
	 */
	private class MouseGridListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {			
			if (board.player_list_getter().get(0).isRobot() && board.player_list_getter().get(1).isRobot()) { //if both player_list are AIs, then we want to call next player, since we can't call it before the board has been drawn
				board.nextPlayer();
				return;
			}
			
			if (board.local_player_getter() != null && board.active_player_getter() != board.local_player_getter()) { //if the local player has been set, and the active player isn't the player who owns this board, we want to wait for a move
				board.w8_4_move();
				return;
			}
			
			int xPos, yPos;

			if (!(e.getX() > xStart && e.getX() < 525 && e.getY() > yStart && e.getY() < 544)) { //user clicked outside of the checkers area
				return; //so we don't need to do anything else and we can return
			}

			xPos = ((e.getX()-xStart)/tileWidth); //this division converts the position in pixels to the tile numbers needed to interact with the backend
			yPos = ((e.getY()-yStart)/tileHeight);			
						
			if (!(yPos%2 == 0)) { //if it's on an odd row, (1,3,5)
				xPos = (((e.getX()-xStart)+(tileWidth/2))/tileWidth)-1; //+13 to the x position, so that the tile is shifted over half a tile's worth of x, the -1 puts it back in bounds for checking against arrays etc
			}

			if (selectedTile == null) { //if the player hasn't selected a tile
				selectedTile = board.getTiles()[xPos][yPos]; //then we select a tile
				drawSelected(xPos,yPos); //and paint that the user selected that tile
			}
			else {
				board.move(selectedTile, board.getTiles()[xPos][yPos]); //otherwise, the user has a selected tile, so we move the piece to the new tile
				selectedTile = null; //and set the placeholder for the selected tile to null so the player can select a tile next turn
			}
		}
	}
}