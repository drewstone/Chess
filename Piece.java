import java.util.ArrayList;

public abstract class Piece {

	private Player player;
	boolean firstMove = false;
	private String pieceType;

	public Piece(Player player, String piece) {
		this.player = player;
		pieceType = piece;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public boolean getPlayerColor() {
		return (("white").equals(player.getColor()));
	}
	
	public void firstMoveUsed() {
		firstMove = true;
	}
	
	public boolean hasMoved() {
		return firstMove;
	}
	
	public boolean getPieceColor() {
		return player.isWhite;
	}
	
	public String getType() {
		return pieceType;
	}

	public abstract boolean isValid(Square[][] board, int prevX, int prevY,
			int finX, int finY);
	
	public abstract ArrayList<Square> getPotentialMoves(Square[][] board, int prevX, int prevY);

}
