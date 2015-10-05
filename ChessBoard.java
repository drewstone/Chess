import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


@SuppressWarnings("serial")
public class ChessBoard extends JLayeredPane {

	private static int BOARD_WIDTH = 600;
	private static int BOARD_HEIGHT = 600;
	private Dimension size = new Dimension(BOARD_WIDTH,BOARD_HEIGHT);
	private JPanel chessBoard;
	private JPanel[][] chessBoardGrid = new JPanel[8][8];
	private Chess chessGame;
	private Square[][] gameBoard;
	boolean playerTurn = true;
    int currentKingX = 0;
    int currentKingY = 0;
    private boolean inCheck = false;
    private MouseControl adapter;
    int numMoves = 0;
	
	public ChessBoard() {
		chessGame = new Chess();
		chessBoard = new JPanel(new GridLayout(8,8));
		chessBoard.setPreferredSize(size);
		chessBoard.setBounds(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessBoardGrid[i][j] = new JPanel(new GridBagLayout());
				chessBoard.add(chessBoardGrid[i][j]);

				if (i % 2 == 0) {
					if (j % 2 == 0) {
						chessBoardGrid[i][j].setBackground(Color.GRAY);
					} else {
						chessBoardGrid[i][j].setBackground(Color.WHITE);
					}
				} else {
					if (j % 2 == 0) {
						chessBoardGrid[i][j].setBackground(Color.WHITE);
					} else {
						chessBoardGrid[i][j].setBackground(Color.GRAY);
					}				}
			}
		}
		
		adapter = new MouseControl();
		addMouseListener(adapter);
		addMouseMotionListener(adapter);
		addPieces();
		add(chessBoard);
		setVisible(true);
	}
	

	public void addPieces() {

		
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				JPanel chessSquare = (JPanel) chessBoardGrid[row][col];
				if (row == 0) {
					if (col == 0 || col == 7) {
						JLabel blackR = new JLabel(new ImageIcon("Black Rook.png"));
						chessSquare.add(blackR);
					} else if (col == 1 || col == 6) {
						JLabel blackKn = new JLabel(new ImageIcon("Black Knight.png"));
						chessSquare.add(blackKn);
					} else if (col == 2 || col == 5) {
						JLabel blackB = new JLabel(new ImageIcon("Black Bishop.png"));
						chessSquare.add(blackB);
					} else if (col == 3) {
						JLabel blackQ = new JLabel(new ImageIcon("Black Queen.png"));
						chessSquare.add(blackQ);
					} else if (col == 4) {
						JLabel blackK = new JLabel(new ImageIcon("Black King.png"));
						chessSquare.add(blackK);
					}
				} else if (row == 1) {
					JLabel blackP = new JLabel(new ImageIcon("Black Pawn.png"));
					chessSquare.add(blackP);
				} else if (row == 6) {
					JLabel whiteP = new JLabel(new ImageIcon("White Pawn.png"));
					chessSquare.add(whiteP);
				} else if (row == 7) {
					if (col == 0 || col == 7) {
						JLabel whiteR = new JLabel(new ImageIcon("White Rook.png"));
						chessSquare.add(whiteR);
					} else if (col == 1 || col == 6) {
						JLabel whiteKn = new JLabel(new ImageIcon("White Knight.png"));
						chessSquare.add(whiteKn);
					} else if (col == 2 || col == 5) {
						JLabel whiteB = new JLabel(new ImageIcon("White Bishop.png"));
						chessSquare.add(whiteB);
					} else if (col == 3) {
						JLabel whiteQ = new JLabel(new ImageIcon("White Queen.png"));
						chessSquare.add(whiteQ);
					} else if (col == 4) {
						JLabel whiteK = new JLabel(new ImageIcon("White King.png"));
						chessSquare.add(whiteK);
					}
				}
			}
		}
	}

	public void resign() {
		if (playerTurn) {
			chessGame.setWinner("black");
			JOptionPane.showMessageDialog(this, "BLACK WINS!!");
		} else {
			chessGame.setWinner("white");
			JOptionPane.showMessageDialog(this, "WHITE WINS!!");
		}
	}

	private class MouseControl extends MouseAdapter {
		private JLabel targetPiece = null;
        private JPanel targetPanel = null;
        private int adjX;
        private int adjY;
        private Square startSquare;
        private int startRow;
        private int startCol;
        private Piece currentPiece;


        
		public Square findDestination(JPanel targetPanel) {
			gameBoard = chessGame.getBoard();

        	for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						if (chessBoardGrid[i][j] == targetPanel) {
							Square targetSquare = gameBoard[i][j];
							targetSquare.addRowIndex(i);
							targetSquare.addColIndex(j);
							return targetSquare;
						}
					}
        	}
        	return null;
        }

        @Override
		public void mousePressed(MouseEvent e) {
			try {
				targetPanel = (JPanel) chessBoard.getComponentAt(e.getPoint());
				Component component = targetPanel.getComponent(0);
				startSquare = findDestination(targetPanel);
				currentPiece = startSquare.getPiece();
				startRow = startSquare.getRowIndex();
				startCol = startSquare.getColIndex();
				playerTurn = currentPiece.getPlayer().isWhite;
				if (!startSquare.getPiece().getPlayer().isTurn()) {
					System.out.println("Its not my turn!");
					return;
				}
				//System.out.println("Starting Space: ("
					//	+ startSquare.getRowIndex() + ", "
					//	+ startSquare.getColIndex() + ")");
				if (startSquare.getPiece().getPlayer().isTurn()) {
					// Check if component is a chess piece and change its location
					if (component instanceof JLabel) {

						targetPiece = (JLabel) component;
						adjX = targetPiece.getWidth() / 2;
						adjY = targetPiece.getHeight() / 2;
						targetPiece.setLocation(e.getX() - adjX, e.getY()
								- adjY);
						add(targetPiece, JLayeredPane.DRAG_LAYER);
						repaint();
					}
				}
			} catch (Exception exception) {

			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (targetPiece == null) {
				return;
			}
			targetPiece.setLocation(e.getX() - adjX, e.getY() - adjY);
			repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (targetPiece == null) {
				return;
			}
			// remove(targetPiece);
			JPanel finalMovePanel = (JPanel) chessBoard.getComponentAt(e
					.getPoint());
			findDestination(finalMovePanel);
			if (targetPanel == finalMovePanel) {
				targetPanel.add(targetPiece);
				targetPanel.revalidate();
				System.out.println("*** RETURN TO SAME SPOT - MOVE ***");
				remove(targetPiece);
				repaint();
				return;
			} else {
				if (finalMovePanel == null) {
					targetPanel.add(targetPiece);
					targetPanel.revalidate();
					System.out
							.println("*** MOVED TO NULL SPOT - OFF BOARD MOVE ***");
					remove(targetPiece);
					repaint();
					return;
				} else {
					// System.out.println("Ending Space: (" + targetSquare.row +
					// ", " + targetSquare.col + ")");
					int row = 9;
					int col = 9;
					for (int roww = 0; roww < 8; roww++) {
						for (int coll = 0; coll < 8; coll++) {
							if (chessBoardGrid[roww][coll] == finalMovePanel) {
								row = roww;
								col = coll;
								if (currentPiece.isValid(gameBoard, startRow,
										startCol, roww, coll)) {
									if ("King".equals(currentPiece.getType())) {
										if (inCheck) {
											if (isKingChecked(getDummyBoard(startRow,
													startCol, roww, coll))) {
												targetPanel.add(targetPiece);
												targetPanel.revalidate();
												remove(targetPiece);
												repaint();
												return;
											}
											inCheck = false;
											System.out.println("KING NOT IN CHECK ANYMORE");
										}
									}
									if (isKingChecked(getDummyBoard(startRow,
											startCol, roww, coll))) {
										targetPanel.add(targetPiece);
										targetPanel.revalidate();
										remove(targetPiece);
										repaint();
										return;
									}
									if ("Pawn".equals(currentPiece.getType())) {
										Pawn tempPawn = (Pawn) currentPiece;
										if (!tempPawn.firstMove) {
											tempPawn.setMovedOnce();
										}
									}
									if (finalMovePanel.getComponentCount() != 0) {
										finalMovePanel.removeAll();
									}

									finalMovePanel.add(targetPiece);
									finalMovePanel.revalidate();
									playerTurn = !playerTurn;
									gameBoard = chessGame.move(null, startRow,
											startCol, roww, coll);
									if (kingInCheck(gameBoard, roww, coll)) {
										inCheck = true;
									}
									numMoves++;
								} else {
									targetPanel.add(targetPiece);
									targetPanel.revalidate();
									remove(targetPiece);
									repaint();
									return;
								}
							}
						}
					}
					// Target location is not on chess board
					if (row == 9 || col == 9) {
						targetPanel.add(targetPiece);
						targetPanel.revalidate();
						remove(targetPiece);
						repaint();
						return;
					}
				}
			}
			chessGame.switchTurns();
			repaint();
			targetPiece = null;
		}
	}

	public void reset() {
		setVisible(false);
	}

	// Finds king and then calls method that checks if king is in check from the last move
	public boolean kingInCheck(Square[][] board, int currentRow, int currentCol) {
		
		Player currentPlayer = board[currentRow][currentCol].getPiece().getPlayer();
		int row = 0;
		int col = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getPiece() == null) {
					continue;
				} else if (board[i][j].getPiece().getPlayer().isWhite == currentPlayer.isWhite) {
					continue;
				} else if ("King".equals(board[i][j].getPiece().getType())) {
					row = i;
					col = j;
				}
			}
		}
		currentKingX = row;
		currentKingY = col;
		return checkCheck(board, currentRow, currentCol, row, col);
	}
	
	// Checks if last move created check
	public boolean checkCheck(Square[][] board, int startRow, int startCol, int kingX, int kingY) {

		boolean placeHolder = false;
		Player current = board[startRow][startCol].getPiece().getPlayer();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getPiece() == null) {
					continue;
				} else if (current.isWhite == board[i][j].getPiece()
						.getPlayer().isWhite) {
					Piece piece = board[i][j].getPiece();
					if (piece.isValid(board, i, j, kingX, kingY)) {
						System.out.println("KING IN CHECK!");
						placeHolder = true;
					}
				} else if (current.isWhite == board[i][j].getPiece()
						.getPlayer().isBlack) {
					if (numMoves >= 4) {
						if (chessGame.checkCheckMate(board[i][j].getPiece()
								.getPlayer(), gameBoard)) {
							remove(chessBoard);
							adapter = null;
							if (playerTurn) {
								chessGame.setWinner("black");
								JOptionPane.showMessageDialog(this,
										"BLACK WINS!!");
							} else {
								chessGame.setWinner("white");
								JOptionPane.showMessageDialog(this,
										"WHITE WINS!!");
							}
						}
					}
				}
			}
		}
		return placeHolder;
	}
	
	public boolean isKingChecked(Square[][] board) {
		findKing(board);
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
	
	private void findKing(Square[][] board) {
		if (!playerTurn) {
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
			int startCol, int finalRow, int finalCol) {
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
}