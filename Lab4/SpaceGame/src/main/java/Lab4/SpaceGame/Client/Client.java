package Lab4.SpaceGame.Client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Lab4.SpaceGame.Core.CaptainCommend;

public class Client implements ClientRemote, Serializable{
    public Client() throws RemoteException {
        //UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public <T> void handleMeasurmentPropertyChanged(String propertyName, T oldValue, T newValue) throws RemoteException {
        System.out.println(String.format("%s: old %s new %s", propertyName, oldValue.toString(), newValue.toString()));
    }

	@Override
	public void handleGameStarted() throws RemoteException {
		System.out.println("Game started");
	}

	@Override
	public void handleGameEnded() throws RemoteException {
		System.out.println("Game ended");
	}

	@Override
	public void handleCaptainCommend(CaptainCommend cmd) throws RemoteException {
		System.out.println("handleCaptainCommend " + cmd.getMessage());
	}
}
