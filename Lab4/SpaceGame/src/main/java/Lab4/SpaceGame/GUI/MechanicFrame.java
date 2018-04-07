package Lab4.SpaceGame.GUI;

import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import Lab4.SpaceGame.Client.ClientRemote;
import Lab4.SpaceGame.Core.Player;
import Lab4.SpaceGame.Core.Utils;
import Lab4.SpaceGame.GUI.Beans.PlayerPanelBean;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.ServerRemote;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;

public class MechanicFrame extends JFrame {
	
	PlayerPanelBean playerPanelBean;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MechanicFrame frame = new MechanicFrame();
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
	public MechanicFrame() {
		setTitle("Mechanic Frame");
		getContentPane().setLayout(null);
		setSize(576, 440);
		
		playerPanelBean = new PlayerPanelBean();
		playerPanelBean.addVetoableChangeListener(new VetoableChangeListener() {
			public void vetoableChange(PropertyChangeEvent e) throws PropertyVetoException {
				if(e.getPropertyName() == "sliderValue") {
					int newValue = (Integer)e.getNewValue();

					if(newValue < 0 || newValue > 50) {
						throw new PropertyVetoException("sliderValue", e);
					}
				}
			}
		});
		playerPanelBean.setSliderName("Oil level");
		playerPanelBean.setSliderEnabled(true);
		playerPanelBean.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if(e.getPropertyName() == "spinner") {
					try {
						playerPanelBean.getLook_up().trySetEngineThrust((int)e.getNewValue());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				if(e.getPropertyName() == "sliderValue") {
					try {
						playerPanelBean.getLook_up().trySetOilLevel((int)e.getNewValue());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
				}
			}
		});
		playerPanelBean.setSpinerEnabled(true);
		playerPanelBean.setSpinerName("Engine Thrust");
		playerPanelBean.setBounds(10, 11, 541, 379);
		getContentPane().add(playerPanelBean);
	}
	
	public MechanicFrame(ServerRemote look_up, ClientRemote client) throws RemoteException {
		this();
		
		playerPanelBean.setLook_up(look_up);
		playerPanelBean.setClient(client);
	}
	
	public void handleGameEvent(GameEvent event) {
		playerPanelBean.handleGameEvent(event);
	}
}
