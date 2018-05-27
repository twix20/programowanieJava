function randomMoveStrategy(currentPlayer, enemyPlayer, board) {
	
	var BoardPosition = Java.type('Lab10.TicTacToe.Core.BoardPosition');

	var boardSize = board.getSize();
	
	while(true) {
		var x = getRandomInt(0, boardSize - 1);
		var y = getRandomInt(0, boardSize - 1);
		
		var isTaken = board.isFieldTaken(x, y);
		if(!isTaken)
			return new BoardPosition(x, y);
	}
	
	return undefined;
}

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}