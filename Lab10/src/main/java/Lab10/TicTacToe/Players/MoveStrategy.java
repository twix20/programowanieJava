package Lab10.TicTacToe.Players;

import Lab10.TicTacToe.Core.Board;
import Lab10.TicTacToe.Core.BoardPosition;

public interface MoveStrategy {
	
	public BoardPosition getNextBoardPositionToMark(Board board);
	
	public String getName();

}
