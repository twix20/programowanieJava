package Lab4.SpaceGame.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {

    public String helloTo(String name) throws RemoteException;
    
    public boolean joinGame(Player p) throws RemoteException;

}