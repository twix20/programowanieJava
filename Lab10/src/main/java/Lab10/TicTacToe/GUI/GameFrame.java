package Lab10.TicTacToe.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.function.BiConsumer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Lab10.TicTacToe.Core.BoardPosition;
import Lab10.TicTacToe.Core.GameEvent;
import Lab10.TicTacToe.Core.GameEvent.GameEventType;
import Lab10.TicTacToe.Core.GameEventListener;
import Lab10.TicTacToe.Core.GameSession;
import Lab10.TicTacToe.Core.Player;
import Lab10.TicTacToe.Core.TicTacToeValue;
import Lab10.TicTacToe.Players.AiPlayer;
import Lab10.TicTacToe.Players.HumanPlayer;
import Lab10.TicTacToe.Players.MoveStrategy;
import Lab10.TicTacToe.Players.MoveStrategies.RandomMoveStrategy;
import Lab10.TicTacToe.Utils.Utils;

import java.awt.Dimension;

public class GameFrame {
	public JFrame frmTicTacToe;
	private GameBoardPanel gameBoardPanel;
	private GameStatePanel gameStatePanel;
	
	private GameSession game;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Player player1 = new HumanPlayer("A", TicTacToeValue.X);
					AiPlayer player2 = new AiPlayer("B", TicTacToeValue.O);
					
					GameFrame window = new GameFrame(5, 5, player1, player2);
					window.frmTicTacToe.setSize(1200, 500);
					window.frmTicTacToe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GameFrame(int boardSize, int toWinInARow, Player... players) {
		game = new GameSession(boardSize, toWinInARow);
		
		initialize(players);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Player... players) {
		gameBoardPanel = new GameBoardPanel(game, PositionClickedCallback);
		gameStatePanel = new GameStatePanel(game);

		frmTicTacToe = new JFrame();
		frmTicTacToe.getContentPane().setLayout(new GridLayout(0,2));
		frmTicTacToe.setPreferredSize(new Dimension(400, 400));
		frmTicTacToe.setTitle("Tic Tac Toe");
		//frmTicTacToe.setBounds(100, 100, 573, 400);
		frmTicTacToe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frmTicTacToe.getContentPane().add(gameBoardPanel, BorderLayout.WEST);
		gameBoardPanel.setLayout(null);
		frmTicTacToe.getContentPane().add(gameStatePanel, BorderLayout.EAST);
		gameStatePanel.setLayout(null);
		
		registerGuiEvents();
		
		game.start(players);
		game.forceInit();
	}
	
	private void registerGuiEvents() {
		
		Frame frame = this.frmTicTacToe;
		
		game.addGameListener(new GameEventListener() {
			@Override
			public void EventOcured(GameEvent event) {
				
				switch (event.type) {
				case PlayerSuccessfullyPlacedMark:
					break;
					
				case PlayerUnSuccessfullyPlacedMark:
					Utils.Log("PlayerUnSuccessfullyPlacedMark");
					break;
					
				case PlayerWon:
					Player playerWon = (Player)event.getSource();
					Utils.Log(playerWon.getName() + " won");
					
					JOptionPane.showMessageDialog(frame, "Congrats " + playerWon.getName() + "\nYou won!");
					break;
				case GameTied:
					JOptionPane.showMessageDialog(frame, "Game Tied! \nTry again!");
					break;
				case AiStrategyChanged:
					MoveStrategy strat = (MoveStrategy)event.getSource();
					JOptionPane.showMessageDialog(frame, "Ai Strategy changed to " + strat.getName());
					break;
				default:
					break;
				}
			}
		});
		
	}
	
	private BiConsumer<Integer, Integer> PositionClickedCallback = (x, y) -> {
		game.selectField(x, y);
	};
}
