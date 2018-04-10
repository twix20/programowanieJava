package Lab4.SpaceGame.GUI;

import java.awt.EventQueue;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import Lab4.SpaceGame.Client.ClientRemote;
import Lab4.SpaceGame.Core.CanHandleGameEvent;
import Lab4.SpaceGame.GUI.Beans.PlayerPanelBean;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.ServerRemote;

public class SteersmanFrame extends JFrame implements CanHandleGameEvent{

	private PlayerPanelBean playerPanelBean;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SteersmanFrame frame = new SteersmanFrame();
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
	public SteersmanFrame() {
		setTitle("Steersman Frame");
		getContentPane().setLayout(null);
		setBounds(100, 100, 584, 455);
		
		playerPanelBean = new PlayerPanelBean();
		playerPanelBean.setCheckboxName("Lights");
		playerPanelBean.setCheckboxEnabled(true);
		playerPanelBean.setSpinerName("Broken");
		playerPanelBean.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				
				if(e.getPropertyName() == "sliderValue") {
					try {
						playerPanelBean.getLook_up().trySetSteeringWheelAngle((int)e.getNewValue());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
				
				
				if(e.getPropertyName() == "checkbox") {
					try {
						playerPanelBean.getLook_up().trySetLights((boolean)e.getNewValue());
					} catch (RemoteException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		playerPanelBean.setSliderEnabled(true);
		playerPanelBean.setSliderName("Steer Wheel Angle");
		playerPanelBean.setBounds(10, 11, 545, 394);
		getContentPane().add(playerPanelBean);
	}
	
	public SteersmanFrame(ServerRemote look_up, ClientRemote client) throws RemoteException {
		this();
		
		playerPanelBean.setLook_up(look_up);
		playerPanelBean.setClient(client);
	}
	
	public void handleGameEvent(GameEvent event) {
		playerPanelBean.handleGameEvent(event);
	}
}
