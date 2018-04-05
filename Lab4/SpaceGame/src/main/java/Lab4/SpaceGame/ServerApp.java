package Lab4.SpaceGame;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Lab4.SpaceGame.Server.GameServer;

/**
 * Hello world!
 *
 */
public class ServerApp
{
    public static void main( String[] args )
    {
        try {

        	Registry registry = null;
            try {
            	registry = LocateRegistry.createRegistry(GameServer.SERVER_PORT);
                System.out.println("java RMI registry created.");

            } catch(Exception e) {
            	System.out.println("Using existing registry");
            	registry = LocateRegistry.getRegistry(GameServer.SERVER_PORT);
            }
        	
        	registry.rebind(GameServer.SERVER_LOOKUP, new GameServer());            
            System.err.println("Server ready");

        } catch (Exception e) {

            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
