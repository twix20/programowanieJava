package Lab10.TicTacToe.Players;

import Lab10.TicTacToe.Core.Board;
import Lab10.TicTacToe.Core.BoardPosition;
import Lab10.TicTacToe.Core.Player;
import Lab10.TicTacToe.Core.TicTacToeValue;
import Lab10.TicTacToe.Players.MoveStrategies.MinMaxMoveStrategy;
import Lab10.TicTacToe.Players.MoveStrategies.RandomMoveStrategy;

public class AiPlayer extends Player {

	private MoveStrategy strategy;
	
	public AiPlayer(String name, TicTacToeValue value) {
		super(name, value);
		
		//setMoveStrategy(new RandomMoveStrategy());
		setMoveStrategy(new MinMaxMoveStrategy());
	}
	
	public void setMoveStrategy(MoveStrategy strategy) {
		this.strategy = strategy;
	}
	
	public MoveStrategy getStrategy() {
		return strategy;
	}


	public BoardPosition getNextBoardPositionToMark(Player enemyPlayer, Board board) {
		return strategy.getNextBoardPositionToMark(this, enemyPlayer, board);
	}
}
