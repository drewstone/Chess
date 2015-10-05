public class Player {


	private boolean turn = false;
	boolean isWhite;
	boolean isBlack;
	
	// True = white, False = black
	public Player(boolean color) {
		if (color) {
			isWhite = true;
			isBlack = false;
		} else {
			isBlack = true;
			isWhite = false;
		}
	}
	
	public boolean isTurn() {
		return turn;
	}
	
	public void changeTurn() {
		turn = !turn;
	}
	
	public boolean getColor() {
		return isWhite;
	}
}
