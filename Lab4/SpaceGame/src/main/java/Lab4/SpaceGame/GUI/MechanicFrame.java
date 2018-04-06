package Lab4.SpaceGame.GUI;

import java.awt.EventQueue;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import Lab4.SpaceGame.Client.ClientRemote;
import Lab4.SpaceGame.Core.Player;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.ServerRemote;

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
		setSize(576, 401);
		
		playerPanelBean = new PlayerPanelBean();
		playerPanelBean.setMechanic(true);
		playerPanelBean.setBounds(10, 11, 541, 339);
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
