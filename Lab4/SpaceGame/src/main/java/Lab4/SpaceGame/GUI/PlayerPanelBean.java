package Lab4.SpaceGame.GUI;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;

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
import Lab4.SpaceGame.Core.CaptainCommand;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.ServerRemote;

public class PlayerPanelBean extends JPanel {
	
	private ServerRemote look_up;
	private ClientRemote client;
	private CaptainCommand lastCaptainCommend;

	private JTextField txtPlayerName;
	private JTextField txtPlayerRole;
	private JTextArea txtAreaLog;
	
	String sliderName;
	JLabel lblSlider;
	JLabel lblSliderValue;
	JSlider slider;
	int sliderValue;

	String spinerName;
	JLabel lblSpinner;
	JSpinner spinner;
	
	
	
	private int spinnerOldValue;
	private int steerWheelAngleOldValue;
	JPanel panelMechanic;
	
	//Wlasciwosci proste
	private boolean isSpinerEnabled;
	private boolean isSliderEnabled;
	
	//Wlasciwosci ograniczone
	private VetoableChangeSupport vetoes = new VetoableChangeSupport(this); 
	
	//Wlasciwosci zlozone
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.getContentPane().add(new PlayerPanelBean());
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
	
		setBounds(100, 100, 548, 422);
		setLayout(null);

		panelMechanic = new JPanel();
		panelMechanic.setBounds(10, 249, 524, 161);
		add(panelMechanic);
		panelMechanic.setLayout(null);

		JLabel lblTools = new JLabel("Avalible Tools");
		lblTools.setBounds(0, 0, 104, 14);
		panelMechanic.add(lblTools);
		
		lblSpinner = new JLabel("Spinner label");
		lblSpinner.setBounds(10, 25, 94, 14);
		panelMechanic.add(lblSpinner);
		
		spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int newValue = (Integer)spinner.getValue();
				
				changes.firePropertyChange("spinner", spinnerOldValue, newValue);
				spinnerOldValue = newValue;
			}
		});
		spinner.setModel(new SpinnerNumberModel(0, 0, 150, 1));
		spinner.setBounds(20, 50, 207, 20);
		panelMechanic.add(spinner);
		
		lblSlider = new JLabel("Slider Label");
		lblSlider.setBounds(275, 25, 135, 14);
		panelMechanic.add(lblSlider);
		
		lblSliderValue = new JLabel("0");
		lblSliderValue.setBounds(465, 25, 46, 14);
		panelMechanic.add(lblSliderValue);
		
		slider = new JSlider();
		slider.setBounds(264, 50, 247, 26);
		panelMechanic.add(slider);
		slider.setMaximum(180);
		slider.setMinimum(-180);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				int newValue  = slider.getValue();
				lblSliderValue.setText(Integer.toString(newValue));
				
				changes.firePropertyChange("slider", steerWheelAngleOldValue, newValue);
				steerWheelAngleOldValue = newValue;
			}
		});
		steerWheelAngleOldValue = slider.getValue();

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
		
		setSliderEnabled(false);
		setSpinerEnabled(false);
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

				lastCaptainCommend = (CaptainCommand)event.getEventData();
				appendLogMessage("Captain sends commend: " + lastCaptainCommend.getMessage());
				break;
			case EVENT_GAME_MEASURMENT_PROPERTY_CHANGED:
				appendLogMessage(event.getMessage());
				break;
		}
		
		this.repaint();
	}
	
	public CaptainCommand getLastCaptainCommend() {
		return lastCaptainCommend;
	}

    //Proste
	public boolean getSpinerEnabled() {
		return isSpinerEnabled;
	}

	public void setSpinerEnabled(boolean enabled) {
		this.isSpinerEnabled = enabled;
		
		lblSpinner.setEnabled(enabled);
		spinner.setEnabled(enabled);
	}

	public boolean getSliderEnabled() {
		return this.isSliderEnabled;
	}

	public void setSliderEnabled(boolean enabled) {
		this.isSliderEnabled = enabled;
		
		lblSlider.setEnabled(enabled);
		lblSliderValue.setEnabled(enabled);
		slider.setEnabled(enabled);
	}
	
	
	
	//Wiazane
	public void addPropertyChangeListener(PropertyChangeListener p) {
		changes.addPropertyChangeListener(p);
	}

	public void removePropertyChangeListener(PropertyChangeListener p) {
		changes.removePropertyChangeListener(p);
	}
	
	//Ograniczone
	public void addVetoableChangeListener(VetoableChangeListener v) {
		vetoes.addVetoableChangeListener(v);
	}

	public void removeVetoableChangeListener(VetoableChangeListener v) {
		vetoes.removeVetoableChangeListener(v);
	}
	
	public int getSliderValue() {
		return sliderValue;
	}

	public void setSliderValue(int sliderValue) throws PropertyVetoException {
		
		Float oldSliderValue = new Float (this.sliderValue);
		 vetoes.fireVetoableChange ( "sliderValue", oldSliderValue, new Float (oldSliderValue));

		 this.sliderValue = sliderValue;
		 
		 changes.firePropertyChange ( "sliderValue", oldSliderValue, new Float (oldSliderValue)); 
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
	
	public String getSliderName() {
		return sliderName;
	}

	public void setSliderName(String sliderName) {
		this.sliderName = sliderName;
		this.lblSlider.setText(sliderName);
	}
	public String getSpinerName() {
		return spinerName;
	}
	
	public void setSpinerName(String spinerName) {
		this.spinerName = spinerName;
		this.lblSpinner.setText(spinerName);
	}
}
