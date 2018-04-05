package Lab4.SpaceGame.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Lab4.SpaceGame.Client.ClientRemote;
import Lab4.SpaceGame.Core.GameSession;
import Lab4.SpaceGame.Core.Player;

public class GameServer extends UnicastRemoteObject implements ServerRemote {

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
	public boolean joinGame(ClientRemote client, Player p) throws RemoteException {
		
		return gameSession.addPlayer(client, p);
	}

	@Override
	public boolean startGame(ClientRemote client) throws RemoteException {
		return gameSession.startGame();
	}

	@Override
	public boolean isGameStarted() throws RemoteException {
		return gameSession.isGameStarted();
	}

}
