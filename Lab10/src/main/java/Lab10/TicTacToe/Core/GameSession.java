package Lab10.TicTacToe.Core;

import java.util.stream.Collectors;
import java.util.*;

import javax.swing.event.EventListenerList;

import Lab10.TicTacToe.Core.GameEvent.GameEventType;
import Lab10.TicTacToe.Players.AiPlayer;
import Lab10.TicTacToe.Players.MoveStrategy;

public class GameSession {
	
	private GameSessionStatistics statistics;
	private Board board;
	public Player[] players;
	
	private EventListenerList gameEventListenerList;
	
	private int currentPlayerIndex;
	
	public GameSession(int boardSize, int toWinInARow)
	{
		this.board = new Board(boardSize, toWinInARow);
		gameEventListenerList = new EventListenerList();
	}
	
	public void start(Player...players) {
		this.statistics = new GameSessionStatistics();
		
		this.players = new Player[2];
		this.players[0] = players[0];
		this.players[1] = players[1];
		
		currentPlayerIndex = 0;
		
		fireGameEvent(new GameEvent(GameEventType.GameStarted, this.players));
		
		currentPlayerIndex = 1;
		changeTurn();
	}
	
	public boolean selectField(int x, int y) {
		Player p = getCurrentPlayer();
		boolean succeed = board.tryMarkField(x, y, p.getValue());
		
		if(succeed) {
			
			fireGameEvent(new GameEvent(GameEventType.PlayerSuccessfullyPlacedMark, new BoardPosition(x, y)));
			
			if(board.isPlayerWinner(p))
			{
				fireGameEvent(new GameEvent(GameEventType.PlayerWon, p));
				softGameRestart();
			}
			else if(board.areAllFieldsMarked()) {
				fireGameEvent(new GameEvent(GameEventType.GameTied, p));
				softGameRestart();
			} else {
				changeTurn();
			}
		}
		else {
			fireGameEvent(new GameEvent(GameEventType.PlayerUnSuccessfullyPlacedMark, new BoardPosition(x, y)));
		}
		
		return succeed;
	}
	
	public void changeAiStrategies(MoveStrategy strategy) {
		for(Player p : players) {
			if(p instanceof AiPlayer) {
				((AiPlayer)p).setMoveStrategy(strategy);
			}
		}
		
		fireGameEvent(new GameEvent(GameEventType.AiStrategyChanged, strategy));
	}
	
	private void softGameRestart() {
		board.restartBoard();
		changeTurn();
		
		fireGameEvent(new GameEvent(GameEventType.GameStarted, this.players));
	}
	
	private void changeTurn() {
		currentPlayerIndex = (currentPlayerIndex + 1) % 2; 
		fireGameEvent(new GameEvent(GameEventType.TurnChanged, getCurrentPlayer()));
		
		//If AI player next, mark field
		if(getCurrentPlayer().getClass() == AiPlayer.class) {
			AiPlayer aiPlayer = (AiPlayer)getCurrentPlayer();
			BoardPosition nextMove = aiPlayer.getNextBoardPositionToMark(getEnemyPlayer(), board);
			
			selectField(nextMove.getX(), nextMove.getY());
		}
	}
	
	private void fireGameEvent(GameEvent event) {
		
	    Object[] listeners = gameEventListenerList.getListenerList();
	    for (int i = 0; i < listeners.length; i = i + 2) {
	      if (listeners[i] == GameEventListener.class) {
	        ((GameEventListener) listeners[i+1]).EventOcured(event);
	      }
	    }
	}
	
	public void addGameListener(GameEventListener listener) {
		gameEventListenerList.add(GameEventListener.class, listener);
	}
	
	public void removeGameListener(GameEventListener listener) {
		gameEventListenerList.remove(GameEventListener.class, listener);
	}
	
	public Player getCurrentPlayer() {
		return this.players[currentPlayerIndex];
	}
	
	public Player getEnemyPlayer() {
		return this.players[(currentPlayerIndex + 1) % 2];
	}

	public Board getBoard() {
		return board;
	}
	
	public GameSessionStatistics getStatistics() {
		return statistics;
	}

	public void forceInit() {
		fireGameEvent(new GameEvent(GameEventType.Init, this));
	}
}
