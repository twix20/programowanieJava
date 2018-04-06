package Lab4.SpaceGame.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Lab4.SpaceGame.Client.ClientRemote;
import Lab4.SpaceGame.Core.CaptainCommend;
import Lab4.SpaceGame.Core.Player;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.GameEvent.EventType;
import Lab4.SpaceGame.Server.ServerRemote;

public class PlayerPanelBean extends JPanel {
	
	private ServerRemote look_up;
	private ClientRemote client;
	private CaptainCommend lastCaptainCommend;

	private JTextField txtPlayerName;
	private JTextField txtPlayerRole;
	private JTextArea txtAreaLog;
	
	private int engineThrustOldValue;
	private int steerWheelAngleOldValue;
	
	JPanel panelSteersman;
	JPanel panelMechanic;
	
	//Wlasciwosci proste
	private boolean mechanic;
	private boolean steersman;
	
	//Wlasciwosci zlozone
	private PropertyChangeSupport propertyChanges = new PropertyChangeSupport(this);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.add(new PlayerPanelBean());
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
	public PlayerPanelBean() {
	
		setBounds(100, 100, 548, 425);
		setLayout(null);

		panelMechanic = new JPanel();
		panelMechanic.setBounds(10, 249, 247, 158);
		add(panelMechanic);
		panelMechanic.setLayout(null);

		JLabel lblMechanicTools = new JLabel("Mechanic tools");
		lblMechanicTools.setBounds(0, 0, 104, 14);
		panelMechanic.add(lblMechanicTools);
		
		JLabel lblEngingeThrust = new JLabel("Enginge Thrust");
		lblEngingeThrust.setBounds(10, 25, 94, 14);
		panelMechanic.add(lblEngingeThrust);
		
		JSpinner spinnerEngingeThrust = new JSpinner();
		spinnerEngingeThrust.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int newValue = (Integer)spinnerEngingeThrust.getValue();
				
				propertyChanges.firePropertyChange("EngineThrust", engineThrustOldValue, newValue);
				engineThrustOldValue = newValue;
			}
		});
		spinnerEngingeThrust.setModel(new SpinnerNumberModel(0, 0, 150, 1));
		spinnerEngingeThrust.setBounds(20, 50, 207, 20);
		panelMechanic.add(spinnerEngingeThrust);

		JPanel panelPlayer = new JPanel();
		panelPlayer.setBounds(10, 11, 524, 227);
		add(panelPlayer);
		panelPlayer.setLayout(null);

		JLabel lblPlayerInfo = new JLabel("Player info");
		lblPlayerInfo.setBounds(0, 0, 97, 14);
		panelPlayer.add(lblPlayerInfo);

		JLabel lblName = new JLabel("Name");
		lblName.setBounds(10, 28, 46, 14);
		panelPlayer.add(lblName);

		txtPlayerName = new JTextField();
		txtPlayerName.setEditable(false);
		txtPlayerName.setBounds(49, 25, 135, 20);
		panelPlayer.add(txtPlayerName);
		txtPlayerName.setColumns(10);

		JLabel lblRole = new JLabel("Role");
		lblRole.setBounds(194, 28, 46, 14);
		panelPlayer.add(lblRole);

		txtPlayerRole = new JTextField();
		txtPlayerRole.setEditable(false);
		txtPlayerRole.setBounds(221, 25, 135, 20);
		panelPlayer.add(txtPlayerRole);
		txtPlayerRole.setColumns(10);

		JLabel lblLog = new JLabel("Game Log");
		lblLog.setBounds(10, 62, 120, 14);
		panelPlayer.add(lblLog);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 78, 504, 138);
		panelPlayer.add(scrollPane);

		txtAreaLog = new JTextArea();
		scrollPane.setViewportView(txtAreaLog);
		txtAreaLog.setEditable(false);

		panelSteersman = new JPanel();
		panelSteersman.setBounds(267, 249, 267, 158);
		add(panelSteersman);
		panelSteersman.setLayout(null);

		JLabel lblSteersmanTools = new JLabel("Steersman tools");
		lblSteersmanTools.setBounds(0, 0, 94, 14);
		panelSteersman.add(lblSteersmanTools);
		
		JLabel lblSteerWheelAngle = new JLabel("Steer Wheel Angle");
		lblSteerWheelAngle.setBounds(10, 25, 135, 14);
		panelSteersman.add(lblSteerWheelAngle);
		
		JLabel lblSteerWheelAngleValue = new JLabel("0");
		lblSteerWheelAngleValue.setBounds(124, 25, 46, 14);
		panelSteersman.add(lblSteerWheelAngleValue);
		
		JSlider sliderSteerWheelAngle = new JSlider();
		sliderSteerWheelAngle.setMaximum(180);
		sliderSteerWheelAngle.setMinimum(-180);
		sliderSteerWheelAngle.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int newValue  = sliderSteerWheelAngle.getValue();
				lblSteerWheelAngleValue.setText(Integer.toString(newValue));
				
				propertyChanges.firePropertyChange("AteerWheelAngle", steerWheelAngleOldValue, newValue);
				steerWheelAngleOldValue = newValue;
				
			}
		});
		steerWheelAngleOldValue = sliderSteerWheelAngle.getValue();
		sliderSteerWheelAngle.setBounds(10, 49, 247, 26);
		panelSteersman.add(sliderSteerWheelAngle);
		
		setMechanic(false);
		setSteersman(false);
	}
	
	public void appendLogMessage(String msg) {
		this.txtAreaLog.append(msg + '\n');
	}
	
	private void entirePanelEnabled(JPanel p, boolean enabled) {
		for(Component component : getComponents(p)) {
		    component.setEnabled(enabled);
		}
	}
	
    private Component[] getComponents(Component container) {
        ArrayList<Component> list = null;

        try {
            list = new ArrayList<Component>(Arrays.asList(
                  ((Container) container).getComponents()));
            for (int index = 0; index < list.size(); index++) {
                for (Component currentComponent : getComponents(list.get(index))) {
                    list.add(currentComponent);
                }
            }
        } catch (ClassCastException e) {
            list = new ArrayList<Component>();
        }

        return list.toArray(new Component[list.size()]);
    }
    
	public void handleGameEvent(GameEvent event) {
		if(event == null) return;
		
		System.out.println(event.getLogType());
		
		switch(event.getLogType()){
			case EVENT_GAME_STARTED:
				appendLogMessage("Game started");
				break;
			case EVENT_GAME_ENDED:
				appendLogMessage("Game ended");
				break;
			case EVENT_CAPTAIN_SENDS_COMMEND:

				lastCaptainCommend = (CaptainCommend)event.getEventData();

				appendLogMessage("Captain sends commend: " + lastCaptainCommend.getMessage());
				break;
			case EVENT_GAME_MEASURMENT_PROPERTY_CHANGED:
				
				break;
		}
	}
	
	public CaptainCommend getLastCaptainCommend() {
		return lastCaptainCommend;
	}

    //Proste
	public boolean getMechanic() {
		return mechanic;
	}

	public void setMechanic(boolean mechanic) {
		this.mechanic = mechanic;
		
		entirePanelEnabled(panelMechanic, this.mechanic);
	}

	public boolean getSteersman() {
		return steersman;
	}

	public void setSteersman(boolean isSteersman) {
		this.steersman = isSteersman;
		
		entirePanelEnabled(panelSteersman, this.steersman);
	}
	
	//Wiazane
	public void addPropertyChangeListener(PropertyChangeListener p) {
		propertyChanges.addPropertyChangeListener(p);
	}

	public void removePropertyChangeListener(PropertyChangeListener p) {
		propertyChanges.removePropertyChangeListener(p);
	}

	public ClientRemote getClient() {
		return client;
	}

	public void setClient(ClientRemote client) throws RemoteException {
		this.client = client;
		
		this.txtPlayerName.setText(this.getClient().getPlayer().getName());
		this.txtPlayerRole.setText(this.getClient().getPlayer().getRole().name());
		
		this.appendLogMessage("Wait till game starts");
	}

	public ServerRemote getLook_up() {
		return look_up;
	}

	public void setLook_up(ServerRemote look_up) {
		this.look_up = look_up;
	}
	
	
}
