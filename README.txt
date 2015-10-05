README for CHESS:

Object oriented design:

Game class runs entire framework and has some extra buttons to create new game, resign, and quit the frame.

Chess class that creates mechanics of chess game and initiates the game

Player class that contains basic information about the player, i.e color of player.

ChessBoard class that builds swing UI and runs the chess mechanics
Board is created using JPanels and JLabels and actual chessboard is created with a 2D array of Squares.


Square class that contains basic information about a square on a chessboard.
Squares contain nothing or a piece, I can retrieve the pieces from squares and move them to different squares.


Piece class that implements basic methods to retrieve info about pieces

Pawn through King pieces extend Piece class and override certain methods specific to certain pieces, such as isValid. Use array lists to store all valid moves and then check if specific move is contained within all possible moves to instantiate valid movement. Each piece is its own object, I can create and remove pieces.
