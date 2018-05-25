package Lab10.TicTacToe.Players.MoveStrategies;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptException;

import Lab10.TicTacToe.Core.Board;
import Lab10.TicTacToe.Core.BoardPosition;
import Lab10.TicTacToe.Core.Player;
import Lab10.TicTacToe.Nashorn.NashornHelper;
import Lab10.TicTacToe.Players.MoveStrategy;

public abstract class NashornMoveStrategy implements MoveStrategy{

	private String scriptPath, funcName;
	
	public NashornMoveStrategy(String scriptPath, String funcName) {
		this.scriptPath = scriptPath;
		this.funcName = funcName;
	}

	@Override
	public BoardPosition getNextBoardPositionToMark(Player currentPlayer, Board board) {
		try {
						
			NashornHelper.engine.eval(new FileReader(scriptPath));
			Invocable invocable = (Invocable)NashornHelper.engine;
			BoardPosition result = (BoardPosition)invocable.invokeFunction(funcName, currentPlayer, board);
			
			return result;
		} catch (NoSuchMethodException | ScriptException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	
}
