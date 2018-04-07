package Lab4.SpaceGame.Core;

import java.rmi.RemoteException;

import Lab4.SpaceGame.Server.GameEvent;

public interface CanHandleGameEvent {
	public void handleGameEvent(GameEvent event) throws RemoteException;
}
