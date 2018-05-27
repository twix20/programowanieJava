package Lab10.TicTacToe.GUI;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import Lab10.TicTacToe.Core.BoardPosition;
import Lab10.TicTacToe.Core.GameEvent;
import Lab10.TicTacToe.Core.GameEventListener;
import Lab10.TicTacToe.Core.GameSession;
import Lab10.TicTacToe.Core.Player;
import Lab10.TicTacToe.Players.AiPlayer;
import Lab10.TicTacToe.Players.MoveStrategies.FindTicTacToeValueMarkNextFieldStrategy;
import Lab10.TicTacToe.Players.MoveStrategies.MinMaxMoveStrategy;
import Lab10.TicTacToe.Players.MoveStrategies.NextInLineMoveStrategy;
import Lab10.TicTacToe.Players.MoveStrategies.RandomMoveStrategy;
import Lab10.TicTacToe.Utils.Utils;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameStatePanel extends JPanel {
	private GameSession game;
	private JTextField textFieldTies;
	private JTable table;
	
	/**
	 * Create the panel.
	 */
	public GameStatePanel(GameSession game) {
		setMaximumSize(new Dimension(300, 32767));
		this.game = game;
		
		setBorder(new TitledBorder(null, "Statistics", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setLayout(null);
		
		JLabel lblTies = new JLabel("Ties");
		lblTies.setBounds(10, 118, 46, 14);
		add(lblTies);
		
		textFieldTies = new JTextField();
		textFieldTies.setEnabled(false);
		textFieldTies.setEditable(false);
		textFieldTies.setBounds(147, 115, 86, 20);
		add(textFieldTies);
		textFieldTies.setColumns(10);
		
		
		textFieldTies.setText(0 + "");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 525, 91);
		add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				 "Player Name", "TicTacToe Value", "Wins", "Is Turn", "Strategy"
			}
		));
		scrollPane.setViewportView(table);
		
		JButton btnEasy = new JButton("Easy");
		btnEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.changeAiStrategies(new NextInLineMoveStrategy());
			}
		});
		btnEasy.setBounds(10, 165, 161, 23);
		add(btnEasy);
		
		JButton btnMedium = new JButton("Medium");
		btnMedium.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.changeAiStrategies(new RandomMoveStrategy());
			}
		});
		btnMedium.setBounds(10, 199, 161, 23);
		add(btnMedium);
		
		JButton btnHard = new JButton("Hard");
		btnHard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				game.changeAiStrategies(new MinMaxMoveStrategy());
			}
		});
		btnHard.setBounds(10, 233, 161, 23);
		add(btnHard);
		
		registerListener();
	}
	
	private void registerListener() {
		game.addGameListener(new GameEventListener() {
			@Override
			public void EventOcured(GameEvent event) {
				
				int i;
				DefaultTableModel model;
				
				switch (event.type) {
				
				case Init:
					for(i = 0; i < game.players.length; i++) {
						Player p = game.players[i];
						
						model = (DefaultTableModel) table.getModel();
						
						String strategyName = p instanceof AiPlayer ? ((AiPlayer)p).getStrategy().getName() : "Manual" ;
						
						model.addRow(new Object[]{
								p.getName(), 
								p.getValue(), 
								"0", 
								game.getCurrentPlayer() == p,
								strategyName
						});
					}
					handlePlayerChangeTurn();
					break;
				
				case GameStarted:
					handlePlayerChangeTurn();
					break;
				
				case PlayerWon:
					Player playerWon = (Player)event.getSource();
					
					model = (DefaultTableModel) table.getModel();
					
					i = getPlayerRowIndexByName(playerWon.getName());
					int incremented = Integer.parseInt(model.getValueAt(i, 2).toString()) + 1 ;
					model.setValueAt(incremented, i, 2);
					break;
					
				case GameTied:
					int incrementedTies = Integer.parseInt(textFieldTies.getText()) + 1;
					textFieldTies.setText(Integer.toString(incrementedTies));
					break;
					
				case TurnChanged:
					handlePlayerChangeTurn();
					break;
				case AiStrategyChanged:
					
					for(i = 0; i < game.players.length; i++) {
						Player p = game.players[i];
						model = (DefaultTableModel) table.getModel();
						
						String strategyName = p instanceof AiPlayer ? ((AiPlayer)p).getStrategy().getName() : "Manual";

						model.setValueAt(strategyName, i, 4);
					}
					break;
					
				default:
					break;
				}
			}
		});
	}
	
	private int getPlayerRowIndexByName(String name) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		for(int i = 0; i < model.getRowCount(); i++) {
			if(model.getValueAt(i, 0).toString().equals(name))
				return i;
		}
		
		return -1;
	}
	
	private void handlePlayerChangeTurn() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		int currenTurnPlayerIndex = getPlayerRowIndexByName(game.getCurrentPlayer().getName()); 
		
		for(int i = 0; i < model.getRowCount(); i++) {
			model.setValueAt(i == currenTurnPlayerIndex, i, 3);
		}
	}
}
