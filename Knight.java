import java.util.ArrayList;


public class Knight extends Piece{

	String pieceType;

	public Knight(Player player, String type) {
		super(player, type);
		pieceType = type;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid(Square[][] board, int prevX, int prevY, int finX,
			int finY) {

		Square targetSquare = board[finX][finY];
		ArrayList<Square> moves = getPotentialMoves(board, prevX, prevY);
		for (Square move : moves) {
			if (targetSquare == move) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public ArrayList<Square> getPotentialMoves(Square[][] board, int prevX, int prevY) {
		ArrayList<Square> moves = new ArrayList<Square>();
		Square startSquare = board[prevX][prevY];
		Player movingPlayer = startSquare.getPiece().getPlayer();
		
		int[][] offsets = { { -2, 1 }, { -1, 2 }, { 1, 2 }, { 2, 1 },
				{ 2, -1 }, { 1, -2 }, { -1, -2 }, { -2, -1 } };		
		
		for (int[] offset : offsets) {
			int offsetX = offset[0];
			int offsetY = offset[1];
			if (offsetX + prevX > 7 || offsetX + prevX < 0) {
				continue;
			}
			if (offsetY + prevY > 7 || offsetY + prevY < 0) {
				continue;
			}
			Square potentialMove = board[offsetX + prevX][offsetY + prevY];
			if (potentialMove.getPiece() == null) {
				moves.add(potentialMove);
			} else {
				if (movingPlayer.isWhite == potentialMove.getPiece().getPlayer().isBlack) {
					moves.add(potentialMove);
				}
			}
		}
		return moves;
	}
	
	public String getPieceType() {
		return pieceType;
	}
}
