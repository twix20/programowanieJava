package Lab10.TicTacToe.Players;

import Lab10.TicTacToe.Core.Board;
import Lab10.TicTacToe.Core.BoardPosition;
import Lab10.TicTacToe.Core.Player;
import Lab10.TicTacToe.Core.TicTacToeValue;

public class AiPlayer extends Player {

	private MoveStrategy strategy;
	
	public AiPlayer(String name, TicTacToeValue value) {
		super(name, value);
		// TODO Auto-generated constructor stub
	}
	
	public void setMoveStrategy(MoveStrategy strategy) {
		this.strategy = strategy;
	}
	
	public MoveStrategy getStrategy() {
		return strategy;
	}


	public BoardPosition getNextBoardPositionToMark(Board board) {
		return strategy.getNextBoardPositionToMark(board);
	}
}
