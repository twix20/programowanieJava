package Lab4.SpaceGame.GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.SliderUI;
import javax.swing.table.DefaultTableModel;

import Lab4.SpaceGame.Client.ClientRemote;
import Lab4.SpaceGame.Core.CaptainCommand;
import Lab4.SpaceGame.Core.Player;
import Lab4.SpaceGame.Core.Player.Role;
import Lab4.SpaceGame.Core.SpaceshipMeasurements;
import Lab4.SpaceGame.Core.Utils;
import Lab4.SpaceGame.Core.CaptainCommands.EngineThrustCommend;
import Lab4.SpaceGame.Core.CaptainCommands.StearingWheelAngleCommand;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.GameEvent.EventType;
import Lab4.SpaceGame.Server.ServerRemote;

public class CaptainFrame extends JFrame {
	
	ServerRemote look_up;
	ClientRemote client;

	private JPanel contentPane;
	private JTextField txtPlayersConnected;
	private JLabel lblSpaceShipStatus;
	private JTable tableSpaceship;
	private JButton btnStartGame;
	private JButton btnSetEngineThrust;
	private JSpinner spinnerEngineThrust;
	private JSpinner spinnerSteeringWheelAngle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CaptainFrame frame = new CaptainFrame();
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
	public CaptainFrame() {
		setTitle("Captain Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 531, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPlayersConnected = new JLabel("Players connected");
		lblPlayersConnected.setBounds(10, 11, 121, 14);
		contentPane.add(lblPlayersConnected);
		
		txtPlayersConnected = new JTextField();
		txtPlayersConnected.setEditable(false);
		txtPlayersConnected.setBounds(121, 8, 86, 20);
		contentPane.add(txtPlayersConnected);
		txtPlayersConnected.setColumns(10);
		
		lblSpaceShipStatus = new JLabel("Space Ship Status");
		lblSpaceShipStatus.setBounds(10, 62, 121, 14);
		contentPane.add(lblSpaceShipStatus);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 87, 495, 135);
		contentPane.add(scrollPane);
		
		tableSpaceship = new JTable();
		scrollPane.setViewportView(tableSpaceship);
		
		btnStartGame = new JButton("Start The Game");
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					GameEvent success = look_up.startGame();
					if(success == null) {
						Utils.log("Cant start the game right now");
						return;
					}
					
					btnStartGame.setVisible(false);
					
					updateGui();
					updateTableSpaceship();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnStartGame.setBounds(10, 390, 159, 23);
		contentPane.add(btnStartGame);
		
		btnSetEngineThrust = new JButton("Set Engine Thrust");
		btnSetEngineThrust.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int desiredValue = (Integer)spinnerEngineThrust.getValue();
				
				try {
	
					CaptainCommand cmd = new EngineThrustCommend(desiredValue);
					
					look_up.captainSendsCommend(cmd);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
		btnSetEngineThrust.setBounds(10, 244, 187, 23);
		contentPane.add(btnSetEngineThrust);
		
		spinnerEngineThrust = new JSpinner();
		spinnerEngineThrust.setModel(new SpinnerNumberModel(0, 0, 150, 1));
		spinnerEngineThrust.setBounds(207, 245, 94, 20);
		contentPane.add(spinnerEngineThrust);
		
		JButton btnSetSteeringWheelAngle = new JButton("Set Steering Wheel Angle");
		btnSetSteeringWheelAngle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int desiredValue = (Integer)spinnerSteeringWheelAngle.getValue();
				try {
				
					CaptainCommand<Integer> cmd = new StearingWheelAngleCommand(desiredValue);
				
					look_up.captainSendsCommend(cmd);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSetSteeringWheelAngle.setBounds(10, 278, 187, 23);
		contentPane.add(btnSetSteeringWheelAngle);
		
		spinnerSteeringWheelAngle = new JSpinner();
		spinnerSteeringWheelAngle.setModel(new SpinnerNumberModel(0, -180, 180, 1));
		spinnerSteeringWheelAngle.setBounds(207, 276, 94, 20);
		contentPane.add(spinnerSteeringWheelAngle);
	}
	
	public CaptainFrame(ServerRemote look_up, ClientRemote client) throws RemoteException {
		this();
		
		this.look_up = look_up;
		this.client = client;
	
		updateGui();
	}
	
	public void handleGameEvent(GameEvent event) throws RemoteException {
		if(event == null) return;
		
		System.out.println(event.getLogType());
		
		switch(event.getLogType()){
			case EVENT_GAME_STARTED:
				btnStartGame.setVisible(false);
				break;
			case EVENT_GAME_ENDED:
				break;
				
			case EVENT_CAPTAIN_SENDS_COMMEND:
				break;
				
			case EVENT_GAME_MEASURMENT_PROPERTY_CHANGED:
				try {
					updateTableSpaceship();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case EVENT_NEW_PLAYER_JOINED:
				int currentPlayersFromRemote = look_up.getGameSession().getPlayers().size();
				txtPlayersConnected.setText(Integer.toString(currentPlayersFromRemote));
				break;
		}
	}
	
	private void updateGui() throws RemoteException {
		int plyersConnected = look_up.getGameSession().getPlayers().keySet().size();
	

		txtPlayersConnected.setText(Integer.toString(plyersConnected));
		
		repaint();
	}
	
	
	private void updateTableSpaceship() throws RemoteException {
		SpaceshipMeasurements currentMeasurments = look_up.getGameSession().getMeasurements();
		
		String[] columnNames = {"Measurment Name", "Value"};
		Object[][] data = {
			new String[] {"Engine Thrust", Integer.toString(currentMeasurments.getEngineThrust())},
			new String[] {"Steering Wheel Angle", Integer.toString(currentMeasurments.getSteeringWheelAngle())},
				
		};
		DefaultTableModel model = new DefaultTableModel(data, columnNames);
		
		tableSpaceship.setModel(model);
	}
}
