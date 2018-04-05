package Lab4.SpaceGame.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Lab4.SpaceGame.Client.ClientRemote;
import Lab4.SpaceGame.Core.GameSession;
import Lab4.SpaceGame.Core.Player;

public interface ServerRemote extends Remote {

    public String helloTo(String name) throws RemoteException;
    
    public boolean isGameStarted() throws RemoteException;
    
    public boolean joinGame(ClientRemote client, Player p) throws RemoteException;
    public boolean startGame(ClientRemote client) throws RemoteException;

}