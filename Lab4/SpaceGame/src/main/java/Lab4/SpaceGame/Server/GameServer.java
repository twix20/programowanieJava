package Lab4.SpaceGame.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import Lab4.SpaceGame.Server.*;

public class GameServer extends UnicastRemoteObject implements RMIInterface {

	public static final String SERVER_LOOKUP = "//localhost/MyServer";
	public static final int SERVER_PORT = 1099;
	
	private GameSession gameSession;

	public GameServer() throws RemoteException {
		super();
		
		gameSession = new GameSession();
	}
	
	public String helloTo(String name) throws RemoteException {
		
        System.err.println(name + " is trying to contact!");
        return "Server says hello to " + name;
	}

	@Override
	public boolean joinGame(Player p) throws RemoteException {
		
		return gameSession.addPlayer(p);
	}

}
