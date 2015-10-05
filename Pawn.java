import java.util.ArrayList;


public class Pawn extends Piece {
	
	String pieceType;
	boolean firstMove = false;
	
	public Pawn(Player player, String type) {
		super(player, type);
		pieceType = type;
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid(Square[][] board, int prevX, int prevY, int finX,
			int finY) {
		
		int targetRow = finX;
		int targetCol = finY;
		ArrayList<Square> moves = getPotentialMoves(board, prevX, prevY);
		for (Square move : moves) {
			if (targetRow == move.getRowIndex()
					&& targetCol == move.getColIndex()) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public ArrayList<Square> getPotentialMoves(Square[][] board, int prevX, int prevY) {
		ArrayList<Square> moves = new ArrayList<Square>();
		Square startSquare = board[prevX][prevY];
		int incrX = prevX + 1;
		int incrY = prevY + 1;
		int dIncX = prevX + 2; // double decrement
		int decrX = prevX - 1;
		int decrY = prevY - 1;
		int dDecX = prevX - 2; // double decrement
		
		if (prevX + 1 > 7) {
			incrX = prevX;
		}
		if (prevX + 2 > 7) {
			dIncX = prevX;
		}
		if (prevY + 1 > 7) {
			incrY = prevY;
		}
		if (prevX - 1 < 0) {
			decrX = prevX;
		}
		if (prevX - 2 < 0) {
			dDecX = prevX;
		}
		if (prevY - 1 < 0) {
			decrY = prevY;
		}
		
		if (startSquare.getPiece().getPlayer().isWhite) {
			if (board[decrX][prevY].getPiece() == null) {
				moves.add(board[decrX][prevY]);
			}
			// White pawn hasn't moved yet
			if (!firstMove) {
				if (board[dDecX][prevY].getPiece() == null) {
					moves.add(board[dDecX][prevY]);
				}
			}
			if (board[decrX][incrY].getPiece() != null) {
				if (board[decrX][incrY].getPiece().getPlayer().isBlack) {
					moves.add(board[decrX][incrY]);
				}
			}
			if (board[decrX][decrY].getPiece() != null) {
				if (board[decrX][decrY].getPiece().getPlayer().isBlack) {
					moves.add(board[decrX][decrY]);
				}
			}
		} else {
			if (board[incrX][prevY].getPiece() == null) {
				moves.add(board[incrX][prevY]);
			}
			// Black pawn hasn't moved yet
			if (!firstMove) {
				if (board[dIncX][prevY].getPiece() == null) {
					moves.add(board[dIncX][prevY]);
				}
			}

			if (board[incrX][incrY].getPiece() != null) {
				if (board[incrX][incrY].getPiece().getPlayer().isWhite) {
					moves.add(board[incrX][incrY]);
				}
			}
			if (board[incrX][decrY].getPiece() != null) {
				if (board[incrX][decrY].getPiece().getPlayer().isWhite) {
					moves.add(board[incrX][decrY]);
				}
			}
		}
		return moves;
	}
	
	public String getPieceType() {
		return pieceType;
	}
	
	public void setMovedOnce() {
		firstMove = true;
	}
	
	public boolean usedMove() {
		return firstMove;
	}
}
