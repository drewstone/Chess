import java.util.ArrayList;

public class Queen extends Piece {

	String pieceType;

	public Queen(Player player, String type) {
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
	public ArrayList<Square> getPotentialMoves(Square[][] board, int prevX,
			int prevY) {
		ArrayList<Square> moves = new ArrayList<Square>();
		Square startSquare = board[prevX][prevY];
		// Loop through vertical and horizontal axes
		for (int j = 0; j < 8; j++) {
			int incrX = prevX + j;
			if (incrX > 7) {
				incrX = 7;
			}
			Piece potentialPiece = board[incrX][prevY].getPiece();
			if ((prevX + j > 7) || startSquare == board[prevX + j][prevY]) {
				continue;
			}
			if (potentialPiece == null) {
				moves.add(board[prevX + j][prevY]);
			} else {
				if (potentialPiece.getPlayer() == getPlayer()) {
					// Same color piece
					break;
				} else {
					// Enemy Piece
					moves.add(board[prevX + j][prevY]);
					break;
				}
			}
		}
		for (int j = 0; j < 8; j++) {
			int decrX = prevX - j;
			if (decrX < 0) {
				decrX = 0;
			}
			Piece potentialPiece = board[decrX][prevY].getPiece();
			if ((prevX - j < 0) || startSquare == board[prevX - j][prevY]) {
				continue;
			}
			if (potentialPiece == null) {
				moves.add(board[prevX - j][prevY]);
			} else {
				if (potentialPiece.getPlayer() == getPlayer()) {
					// Same color piece
					break;
				} else {
					// Enemy Piece
					moves.add(board[prevX - j][prevY]);
					break;
				}
			}
		}
		for (int j = 0; j < 8; j++) {
			int incrY = prevY + j;
			if (incrY > 7) {
				incrY = 7;
			}
			Piece potentialPiece = board[prevX][incrY].getPiece();
			if ((prevY + j > 7) || startSquare == board[prevX][prevY + j]) {
				continue;
			}
			if (potentialPiece == null) {
				moves.add(board[prevX][prevY + j]);
			} else {
				if (potentialPiece.getPlayer() == getPlayer()) {
					// Same color piece
					break;
				} else {
					// Enemy Piece
					moves.add(board[prevX][prevY + j]);
					break;
				}
			}
		}
		for (int j = 0; j < 8; j++) {
			int decrY = prevY - j;
			if (decrY < 0) {
				decrY = 0;
			}
			Piece potentialPiece = board[prevX][decrY].getPiece();
			if ((prevY - j < 0) || startSquare == board[prevX][prevY - j]) {
				continue;
			}
			if (potentialPiece == null) {
				moves.add(board[prevX][prevY - j]);
			} else {
				if (potentialPiece.getPlayer() == getPlayer()) {
					// Same color piece
					break;
				} else {
					// Enemy Piece
					moves.add(board[prevX][prevY - j]);
					break;
				}
			}
		}

		// Loop through each diagonal until we either fall off board or find
		// another piece in the way
		for (int j = 0; j < 8; j++) {
			int incrX = prevX + j;
			int incrY = prevY + j;
			if (incrX > 7) {
				incrX = 7;
			}
			if (incrY > 7) {
				incrY = 7;
			}
			Piece potentialPiece = board[incrX][incrY].getPiece();
			if ((prevX + j > 7) || (prevY + j > 7)
					|| startSquare == board[prevX + j][prevY + j]) {
				continue;
			}
			if (potentialPiece == null) {
				moves.add(board[prevX + j][prevY + j]);
			} else {
				if (potentialPiece.getPlayer() == getPlayer()) {
					// Same color piece
					break;
				} else {
					// Enemy piece
					moves.add(board[prevX + j][prevY + j]);
					break;
				}
			}
		}
		for (int j = 0; j < 8; j++) {
			int incrX = prevX + j;
			int decrY = prevY - j;
			if (incrX > 7) {
				incrX = 7;
			}
			if (decrY < 0) {
				decrY = 0;
			}
			Piece potentialPiece = board[incrX][decrY].getPiece();
			if ((prevX + j > 7) || (prevY - j < 0)
					|| startSquare == board[prevX + j][prevY - j]) {
				continue;
			}
			if (potentialPiece == null) {
				moves.add(board[prevX + j][prevY - j]);
			} else {
				if (potentialPiece.getPlayer() == getPlayer()) {
					// Same color piece
					break;
				} else {
					// Enemy piece
					moves.add(board[prevX + j][prevY - j]);
					break;
				}
			}
		}
		for (int j = 0; j < 8; j++) {
			int decrX = prevX - j;
			int incrY = prevY + j;
			if (decrX < 0) {
				decrX = 0;
			}
			if (incrY > 7) {
				incrY = 7;
			}
			Piece potentialPiece = board[decrX][incrY].getPiece();
			if ((prevX - j < 0) || (prevY + j > 7)
					|| startSquare == board[prevX - j][prevY + j]) {
				continue;
			}
			if (potentialPiece == null) {
				moves.add(board[prevX - j][prevY + j]);
			} else {
				if (potentialPiece.getPlayer() == getPlayer()) {
					// Same color piece
					break;
				} else {
					// Enemy piece
					moves.add(board[prevX - j][prevY + j]);
					break;
				}
			}
		}
		for (int j = 0; j < 8; j++) {
			int decrX = prevX - j;
			int decrY = prevY - j;
			if (decrX < 0 || decrY < 0) {
				decrX = 0;
				decrY = 0;
			}
			Piece potentialPiece = board[decrX][decrY].getPiece();
			if ((prevX - j < 0) || (prevY - j < 0)
					|| startSquare == board[prevX - j][prevY - j]) {
				continue;
			}
			if (potentialPiece == null) {
				moves.add(board[prevX - j][prevY - j]);
			} else {
				if (potentialPiece.getPlayer() == getPlayer()) {
					// Same color piece
					break;
				} else {
					// Enemy piece
					moves.add(board[prevX - j][prevY - j]);
					break;
				}
			}
		}
		return moves;
	}

	public String getPieceType() {
		return pieceType;
	}
}
