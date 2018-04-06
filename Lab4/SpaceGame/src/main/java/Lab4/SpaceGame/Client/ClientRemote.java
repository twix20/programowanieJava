package Lab4.SpaceGame.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Lab4.SpaceGame.Core.Player;
import Lab4.SpaceGame.Server.GameEvent;

public interface ClientRemote extends Remote {
	
	public Player getPlayer() throws RemoteException;
	
	public void handleGameEvent(GameEvent event)throws RemoteException;

}
