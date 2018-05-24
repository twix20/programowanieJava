function randomMoveStrategy(board) {

	const boardSize = board.getSize();
	
	while(true) {
		const x = getRandomInt(0, boardSize - 1);
		const y = getRandomInt(0, boardSize - 1);
		
		const isTaken = board.isFieldTaken(x, y);
		if(!isTaken)
			return new BoardPosition(x, y);
	}
	
	return undefined;
}

function getRandomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
}