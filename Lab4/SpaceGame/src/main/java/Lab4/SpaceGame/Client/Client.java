package Lab4.SpaceGame.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Lab4.SpaceGame.Core.Player;
import Lab4.SpaceGame.GUI.CaptainFrame;
import Lab4.SpaceGame.GUI.MechanicFrame;
import Lab4.SpaceGame.Server.GameEvent;

public class Client extends UnicastRemoteObject implements ClientRemote{

	Player player;
	
	CaptainFrame captainFrame;
	MechanicFrame mechanicFrame;
	
	public Client(Player player)  throws RemoteException {
		this.player = player;
	}
	
	public void setFrame(MechanicFrame frame) {
		mechanicFrame = frame;
	}
	
	public void setFrame(CaptainFrame frame) {
		captainFrame = frame;
	}
	
	
	@Override
	public void handleGameEvent(GameEvent event) throws RemoteException {
		
		if(captainFrame != null)
			captainFrame.handleGameEvent(event);
		
		if(mechanicFrame != null)
			mechanicFrame.handleGameEvent(event);
		
		System.out.println("GameEvent from client " + event.getMessage());
		
	}

	@Override
	public Player getPlayer() throws RemoteException {
		return player;
	}

}
