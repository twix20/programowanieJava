package Lab4.SpaceGame;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Lab4.SpaceGame.Client.*;
import Lab4.SpaceGame.Core.Player;
import Lab4.SpaceGame.Core.Utils;
import Lab4.SpaceGame.GUI.ClientFrame;
import Lab4.SpaceGame.Server.*;

public class ClientApp {
	
	private static ServerRemote look_up;
	
    public static void main( String[] args ) throws MalformedURLException, RemoteException, NotBoundException
    {
    	Utils.Env = "ClientApp";
    	
    	ClientFrame f = new ClientFrame();
    	f.setVisible(true);
    }

}
