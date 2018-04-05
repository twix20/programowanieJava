package Lab4.SpaceGame.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Lab4.SpaceGame.Core.CaptainCommend;

public interface ClientRemote extends Remote {
    public <T> void handleMeasurmentPropertyChanged(String propertyName, T oldValue, T newValue) throws RemoteException;
    
    public void handleCaptainCommend(CaptainCommend cmd) throws RemoteException;
    
    public void handleGameStarted() throws RemoteException;
    public void handleGameEnded() throws RemoteException;
}
