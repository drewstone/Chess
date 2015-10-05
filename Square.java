public class Square {

	private Piece piece;
	int row;
	int col;
	String pieceType;
	
	public Square(Piece piece) {
		this.piece = piece;
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void placePiece(Piece piece) {
		this.piece = piece;
	}
	
	public void removePiece() {
		piece = null;
	}
	
	public void takePiece(Piece piece) {
		this.piece = piece;
	}
	
	public void addRowIndex(int row) {
		this.row = row;
	}
	
	public int getRowIndex() {
		return row;
	}

	
	public void addColIndex(int col) {
		this.col = col;
	}
	
	public int getColIndex() {
		return col;
	}
	
	public String toString() {
		return ("(" + row + ", " + col + ")");
	}
}
