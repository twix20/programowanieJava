function findTicTacToeValueMarkNextFieldStrategy(currentPlayer, board) {

	var BoardPosition = Java.type('Lab10.TicTacToe.Core.BoardPosition');

	var currentPlayerValue = currentPlayer.getValue();
	var boardSize = board.getSize();

	var canMarkNext = false;
	for(var t = 0; t < 2; t++){
		for(var i = 0; i < boardSize; i++) {
			for(var j = 0; j < boardSize; j++) {
				
				var x = j;
				var y = i;

				if(canMarkNext && !board.isFieldTaken(x, y)) {
					return new BoardPosition(x, y);
				}



				var pos = board.getPos(x, y);

				print(pos.getValue());
				print(currentPlayerValue);
				print(pos.getValue() == currentPlayerValue);

				if(pos.getValue() == currentPlayerValue){
					canMarkNext = true;
					continue;
				}
			}	
		}
	}


	//Player hasnt marked any field yet, return first not marked
	for(var i = 0; i < boardSize; i++) {
		for(var j = 0; j < boardSize; j++) {
			var x = j;
			var y = i;

			if(!board.isFieldTaken(x, y)) {
				return new BoardPosition(x, y);
			}
		}	
	}

	return undefined;
}