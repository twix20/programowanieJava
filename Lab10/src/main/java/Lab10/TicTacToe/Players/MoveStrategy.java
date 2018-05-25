package Lab10.TicTacToe.Players;

import Lab10.TicTacToe.Core.Board;
import Lab10.TicTacToe.Core.BoardPosition;
import Lab10.TicTacToe.Core.Player;

public interface MoveStrategy {
	
	public BoardPosition getNextBoardPositionToMark(Player currentPlayer, Board board);
	
	public String getName();

}
