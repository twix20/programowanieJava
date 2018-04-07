package Lab4.SpaceGame.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Lab4.SpaceGame.Core.CanHandleGameEvent;
import Lab4.SpaceGame.Core.Player;
import Lab4.SpaceGame.Server.GameEvent;

public interface ClientRemote extends Remote, CanHandleGameEvent {
	
	public Player getPlayer() throws RemoteException;
}
