package Lab4.SpaceGame.GUI;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Lab4.SpaceGame.Client.ClientRemote;
import Lab4.SpaceGame.GUI.Beans.PlayerPanelBean;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.ServerRemote;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.beans.PropertyChangeEvent;

public class SteersmanFrame extends JFrame {

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
		setBounds(100, 100, 584, 408);
		
		playerPanelBean = new PlayerPanelBean();
		playerPanelBean.setSpinerName(" ");
		playerPanelBean.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				if(e.getPropertyName() == "slider") {
					try {
						playerPanelBean.getLook_up().trySetSteeringWheelAngle((int)e.getNewValue());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		playerPanelBean.setSliderEnabled(true);
		playerPanelBean.setSliderName("Steer Wheel Angle");
		playerPanelBean.setBounds(10, 11, 545, 351);
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
