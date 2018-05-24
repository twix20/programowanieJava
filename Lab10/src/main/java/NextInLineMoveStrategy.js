function nextInLineMoveStrategy(board) {
	
	var BoardPosition = Java.type('Lab10.TicTacToe.Core.BoardPosition');

	var boardSize = board.getSize();
	for(var i = 0; i < boardSize; i++){
		for(var j = 0; j < boardSize; j++){

			var x = j;
			var y = i;

			var isTaken = board.isFieldTaken(x, y);
			if(!isTaken)
				return new BoardPosition(x, y);
		}	
	}

	return undefined;
}