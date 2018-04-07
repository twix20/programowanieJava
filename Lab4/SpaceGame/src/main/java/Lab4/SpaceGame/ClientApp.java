package Lab4.SpaceGame;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import Lab4.SpaceGame.Core.Utils;
import Lab4.SpaceGame.GUI.ClientFrame;

public class ClientApp {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		Utils.Env = "ClientApp";

		ClientFrame f = new ClientFrame();
		f.setVisible(true);
	}
}
