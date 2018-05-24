package Lab10.TicTacToe.Core;

import java.util.EventObject;

public class GameEvent extends EventObject {

	public GameEventType type;
	
	public GameEvent(GameEventType type, Object arg0) {
		super(arg0);
		
		this.type = type;
	}

	
	public enum GameEventType
	{
		Init,
		GameStarted,
		GameTied,
		PlayerSuccessfullyPlacedMark,
		PlayerUnSuccessfullyPlacedMark,
		PlayerWon,
		TurnChanged,
		AiStrategyChanged
	}
}
