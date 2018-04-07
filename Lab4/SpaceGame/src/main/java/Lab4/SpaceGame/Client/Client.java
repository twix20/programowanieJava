package Lab4.SpaceGame.Client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Lab4.SpaceGame.Core.CanHandleGameEvent;
import Lab4.SpaceGame.Core.Player;
import Lab4.SpaceGame.Server.GameEvent;

public class Client extends UnicastRemoteObject implements ClientRemote{

	Player player;
	
	CanHandleGameEvent eventHandler;
	
	public Client(Player player)  throws RemoteException {
		this.player = player;
	}
	
	public void setEventHandler(CanHandleGameEvent handler) {
		this.eventHandler = handler;
	}
	
	@Override
	public void handleGameEvent(GameEvent event) throws RemoteException {
		
		if(eventHandler != null)
			eventHandler.handleGameEvent(event);
	}

	@Override
	public Player getPlayer() throws RemoteException {
		return player;
	}

}
