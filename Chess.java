import java.util.ArrayList;

public class Chess {

	private Square[][] board;
	private Player WHITE;
	private Player BLACK;
	private Piece targetPiece;
	private boolean winner;
	private ArrayList<Piece> takenPieces;
	private int currentKingX = 0;
	private int currentKingY = 0;

	public Chess() {
		WHITE = new Player(true);
		BLACK = new Player(false);
		WHITE.changeTurn();
		takenPieces = new ArrayList<Piece>();
		initialize();
	}

	public void addPieces(Square[][] chessboard) {
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				if (row == 0) {
					if (col == 0 || col == 7) {
						board[row][col] = new Square(new Rook(BLACK, "Rook"));
					} else if (col == 1 || col == 6) {
						board[row][col] = new Square(new Knight(BLACK, "Knight"));
					} else if (col == 2 || col == 5) {
						board[row][col] = new Square(new Bishop(BLACK, "Bishop"));
					} else if (col == 3) {
						board[row][col] = new Square(new Queen(BLACK, "Queen"));
					} else if (col == 4) {
						board[row][col] = new Square(new King(BLACK, "King"));
					}
				} else if (row == 1) {
					board[row][col] = new Square(new Pawn(BLACK, "Pawn"));
				} else if (row == 6) {
					board[row][col] = new Square(new Pawn(WHITE, "Pawn"));
				} else if (row == 7) {
					if (col == 0 || col == 7) {
						board[row][col] = new Square(new Rook(WHITE, "Rook"));
					} else if (col == 1 || col == 6) {
						board[row][col] = new Square(new Knight(WHITE, "Knight"));
					} else if (col == 2 || col == 5) {
						board[row][col] = new Square(new Bishop(WHITE, "Bishop"));
					} else if (col == 3) {
						board[row][col] = new Square(new Queen(WHITE, "Queen"));
					} else if (col == 4) {
						board[row][col] = new Square(new King(WHITE, "King"));
					}
				} else {
					board[row][col] = new Square(null);
				}
			}
		}
	}

	public Square[][] move(Player player, int prevX, int prevY, int finX, int finY) {
		targetPiece = board[prevX][prevY].getPiece();

		Square startSquare = board[prevX][prevY];
		Square targetSquare = board[finX][finY];

		Piece potentialOpponentPiece = targetSquare.getPiece();
		if (potentialOpponentPiece != null) {
			targetSquare.removePiece();
			takenPieces.add(potentialOpponentPiece);
			targetSquare.placePiece(targetPiece);
		} else {
			targetSquare.placePiece(targetPiece);
		}
		startSquare.removePiece();
		return board;
	}
	
	public void initialize() {
		board = new Square[8][8];
		addPieces(board);
	}
	
	public Square[][] getBoard() {
		return board;
	}
	
	public void switchTurns() {
		WHITE.changeTurn();
		BLACK.changeTurn();
	}
	
	public void playerWins() {
		if (winner) {
			System.out.println("WHITE WINS!");
		} if (!winner) {
			System.out.println("BLACK WINS!");
		}
	}
	
	public boolean isKingChecked(Square[][] board, Player player) {
		findKing(board, player);
		Piece currentKing = board[currentKingX][currentKingY].getPiece();
		System.out.println("(" + currentKingX + ", " + currentKingY + ")");
		if (currentKing == null) {
			System.out.println("KING IS NULL");
			return true;
		}
		if (!"King".equals(currentKing.getType())) {
			return false;
		}
		Player currentPlayer = currentKing.getPlayer();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getPiece() == null) {
					continue;
				} else if (currentPlayer.isWhite == board[i][j].getPiece()
						.getPlayer().isBlack) {
					Piece piece = board[i][j].getPiece();
					if (piece.isValid(board, i, j, currentKingX, currentKingY)) {
						System.out.println(piece.getType() + " KING STILL IN CHECK!");
						return true;
					}
				}
			}
		}
		System.out.println("KING NOT IN CHECK ANYMORE");
		return false;
	}
	
	private void findKing(Square[][] board, Player player) {
		if (player.isBlack) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (board[i][j].getPiece() == null) {
						continue;
					}
					if (board[i][j].getPiece().getPlayer().isBlack) {
						if ("King".equals(board[i][j].getPiece().getType())) {
							currentKingX = i;
							currentKingY = j;
						}
					}
				}
			}
		} else {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (board[i][j].getPiece() == null) {
						continue;
					}
					if (board[i][j].getPiece().getPlayer().isWhite) {
						if ("King".equals(board[i][j].getPiece().getType())) {
							currentKingX = i;
							currentKingY = j;
						}
					}
				}
			}
		}
		
	}


	public Square[][] getDummyBoard(int startRow,
			int startCol, int finalRow, int finalCol, Square[][] gameBoard) {
		Square[][] board = new Square[8][8];
		Player WHITE = new Player(true);
		Player BLACK = new Player(false);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (gameBoard[i][j].getPiece() == null) {
					board[i][j] = new Square(null);
				} else if (gameBoard[i][j].getPiece().getPlayer().isWhite) {
					if ("Pawn".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new Pawn(WHITE, "Pawn"));
					} else if ("Rook".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new Rook(WHITE, "Rook"));
					} else if ("Knight".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new Knight(WHITE, "Knight"));
					} else if ("Bishop".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new Bishop(WHITE, "Bishop"));
					} else if ("Queen".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new Queen(WHITE, "Queen"));
					} else if ("King".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new King(WHITE, "King"));
					}
				} else {
					if ("Pawn".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new Pawn(BLACK, "Pawn"));
					} else if ("Rook".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new Rook(BLACK, "Rook"));
					} else if ("Knight".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new Knight(BLACK, "Knight"));
					} else if ("Bishop".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new Bishop(BLACK, "Bishop"));
					} else if ("Queen".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new Queen(BLACK, "Queen"));
					} else if ("King".equals(gameBoard[i][j].getPiece().getType())) {
						board[i][j] = new Square(new King(BLACK, "King"));
					}
				}
			}
		}
		
		Piece targetPiece = board[startRow][startCol].getPiece();

		Square startSquare = board[startRow][startCol];
		Square targetSquare = board[finalRow][finalCol];

		Piece potentialOpponentPiece = targetSquare.getPiece();
		if (potentialOpponentPiece != null) {
			targetSquare.removePiece();
			targetSquare.placePiece(targetPiece);
		} else {
			targetSquare.placePiece(targetPiece);
		}
		startSquare.removePiece();
		return board;
	}
	
	
	public boolean checkCheckMate(Player player, Square[][] board) {
		// Get all pieces of current color pieces
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getPiece() == null) {
					continue;
				}
				if (board[i][j].getPiece().getPlayer().isWhite == player.isWhite) {
					for (int a = 0; a < 8; a++) {
						for (int b = 0; b < 8; b++) {
							if (i == a && j == b) {
								continue;
							}
							if (board[i][j].getPiece().isValid(board, i, j, a, b)) {
								if (!isKingChecked(getDummyBoard(i, j, a, b, board), player)) {
									return false;
								}
							}

						}
					}
				}
			}
		}
		return true;
	}
	
	
	
	public void setWinner(String color) {
		if ("white".equals(color)) {
			winner = true;
		} else {
			winner = false;
		}
	}
}
