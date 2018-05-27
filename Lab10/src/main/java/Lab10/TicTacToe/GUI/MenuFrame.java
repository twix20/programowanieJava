package Lab10.TicTacToe.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Lab10.TicTacToe.Core.Player;
import Lab10.TicTacToe.Core.TicTacToeValue;
import Lab10.TicTacToe.Players.AiPlayer;
import Lab10.TicTacToe.Players.HumanPlayer;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class MenuFrame extends JFrame {

	private JPanel contentPane;
	private JSpinner spinnerBoardSize;
	private JSpinner spinnerToWinInARow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuFrame frame = new MenuFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 501, 362);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnPlayerVsPlayer = new JButton("Player vs Player");
		btnPlayerVsPlayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Player player1 = new HumanPlayer("First Player", TicTacToeValue.O);
				Player player2 = new HumanPlayer("Second Player", TicTacToeValue.X);
				
				StartGameFrame(player1, player2);
			}
		});
		btnPlayerVsPlayer.setBounds(10, 155, 465, 73);
		contentPane.add(btnPlayerVsPlayer);
		
		JButton btnPlayerVsAi = new JButton("Player vs AI");
		btnPlayerVsAi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Player player1 = new HumanPlayer("First Player", TicTacToeValue.O);
				Player player2 = new AiPlayer("AI Player", TicTacToeValue.X);
				
				StartGameFrame(player1, player2);
			}
		});
		btnPlayerVsAi.setBounds(10, 239, 465, 73);
		contentPane.add(btnPlayerVsAi);
		
		JLabel lblBoardSize = new JLabel("Board Size");
		lblBoardSize.setBounds(10, 130, 71, 14);
		contentPane.add(lblBoardSize);
		
		spinnerBoardSize = new JSpinner();
		spinnerBoardSize.setModel(new SpinnerNumberModel(3, 3, 25, 1));
		spinnerBoardSize.setBounds(114, 124, 29, 20);
		contentPane.add(spinnerBoardSize);
		
		JLabel lblToWinIn = new JLabel("To win in a row");
		lblToWinIn.setBounds(10, 87, 99, 14);
		contentPane.add(lblToWinIn);
		
		spinnerToWinInARow = new JSpinner();
		spinnerToWinInARow.setModel(new SpinnerNumberModel(3, 3, 5, 1));
		spinnerToWinInARow.setBounds(114, 84, 29, 20);
		contentPane.add(spinnerToWinInARow);
	}
	
	private void StartGameFrame(Player... players) {
		
		int boardSize = (int)spinnerBoardSize.getValue();
		int toWinInARow = (int)spinnerToWinInARow.getValue();
		
		GameFrame gameFrame = new GameFrame(boardSize, toWinInARow, players);
		gameFrame.frmTicTacToe.pack();
		gameFrame.frmTicTacToe.setVisible(true);
		
		this.setVisible(false);
		this.dispose();
	}
}
