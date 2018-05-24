package Lab10.TicTacToe.GUI;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BiConsumer;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import Lab10.TicTacToe.Core.BoardPosition;
import Lab10.TicTacToe.Core.GameEvent;
import Lab10.TicTacToe.Core.GameEventListener;
import Lab10.TicTacToe.Core.GameSession;
import Lab10.TicTacToe.Core.Player;
import Lab10.TicTacToe.Utils.Utils;

public class GameBoardPanel extends JPanel {

	private final int buttonWidth = 45;
	private GameSession game;
	private JButton[][] _buttons;
	
	/**
	 * Create the panel.
	 */
	public GameBoardPanel(GameSession game, BiConsumer<Integer, Integer> posCallback) {
		this.game = game;
		
		setLayout(null);
		
		setBorder(new TitledBorder(null, "Game Board", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		
		init(posCallback);
		registerListener();
	}
	
	public void playerSetPosition(int x, int y, Player player) {
		_buttons[x][y].setText(player.getValue().toString());
	}
	
	private void registerListener() {
		game.addGameListener(new GameEventListener() {
			@Override
			public void EventOcured(GameEvent event) {
				switch (event.type) {
				case PlayerSuccessfullyPlacedMark:
					BoardPosition pos = (BoardPosition)event.getSource();
					playerSetPosition(pos.getX(), pos.getY(), game.getCurrentPlayer());
					break;
					
				case PlayerUnSuccessfullyPlacedMark:
					break;
				case PlayerWon:
					restartBoard();
					break;
				case GameTied:
					restartBoard();
					break;
				default:
					break;
				}
			}
		});
	}
	
	private void init(BiConsumer<Integer, Integer> posCallback) {
		
		final int boardSize = game.getBoard().getSize();
		
		_buttons = new JButton[boardSize][boardSize];
		
		Insets buttonMargin = new Insets(0,0,0,0);
		
		for(int i = 0; i < boardSize; i++) {
			for(int j = 0; j < boardSize; j++) {
				
				JButton btn = new JButton("");
				btn.setMargin(buttonMargin);
				
				btn.setBounds(20 + i * buttonWidth, 25 + j * buttonWidth, buttonWidth, buttonWidth);
				btn.setSize(buttonWidth, buttonWidth);
				btn.addActionListener(new PositionActionListener(i, j, posCallback));
				
				_buttons[i][j] = btn;
				add(btn);
			}
		}
	}
	
	public void restartBoard() {
		for(JButton[] btnRow : _buttons) {
			for(JButton btn : btnRow) {
				btn.setText("");
			}
		}
	}
	
	private class PositionActionListener implements ActionListener {
	    private BiConsumer<Integer, Integer> cb;
	    private int x, y;

	    public PositionActionListener(int i, int j, BiConsumer<Integer, Integer> posCallback) {
	        this.cb = posCallback;
	        x = i;
	        y = j;
	    }

	    public void actionPerformed(ActionEvent e) {
	        cb.accept(x, y);
	    }
	}
}

