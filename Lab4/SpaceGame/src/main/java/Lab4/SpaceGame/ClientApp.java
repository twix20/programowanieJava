package Lab4.SpaceGame;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Lab4.SpaceGame.Server.GameServer;
import Lab4.SpaceGame.Server.Player;
import Lab4.SpaceGame.Server.RMIInterface;

public class ClientApp {
	
	private static RMIInterface look_up;
	
    public static void main( String[] args ) throws MalformedURLException, RemoteException, NotBoundException
    {
    	Registry registry = LocateRegistry.getRegistry(GameServer.SERVER_PORT);
    	
		look_up = (RMIInterface) registry.lookup(GameServer.SERVER_LOOKUP);

		boolean response = look_up.joinGame(new Player("asdas", Player.Role.Captain));
		System.out.println(response);

    }

}
