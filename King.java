import java.util.ArrayList;

public class King extends Piece {

	boolean castle;

	public King(Player player, String type) {
		super(player, type);
		castle = true;
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
		Player movingPlayer = startSquare.getPiece().getPlayer();
		int lowerRowBound = prevX - 1;
		int higherRowBound = prevX + 1;
		int lowerColBound = prevY - 1;
		int higherColBound = prevY + 1;

		if (lowerRowBound < 0) {
			lowerRowBound = 0;
		} else if (lowerColBound < 0) {
			lowerColBound = 0;
		} else if (higherRowBound > 7) {
			higherRowBound = 7;
		} else if (higherColBound > 7) {
			higherColBound = 7;
		}

		for (int row = lowerRowBound; row <= higherRowBound; row++) {
			for (int col = lowerColBound; col <= higherColBound; col++) {
				Square targetSquare = board[row][col];
				if (targetSquare == startSquare) {
					continue;
				}
				if (targetSquare.getPiece() == null) {
					moves.add(targetSquare);
				} else {
					if (targetSquare.getPiece().getPlayer().isBlack == movingPlayer.isWhite) {
						moves.add(targetSquare);
					}
				}
			}
		}
		return moves;
	}
}
