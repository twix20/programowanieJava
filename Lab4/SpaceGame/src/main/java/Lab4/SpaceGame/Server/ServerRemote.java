package Lab4.SpaceGame.Server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import Lab4.SpaceGame.Client.ClientRemote;
import Lab4.SpaceGame.Core.CaptainCommend;
import Lab4.SpaceGame.Core.GameSession;
import Lab4.SpaceGame.Core.Player;

public interface ServerRemote extends Remote {

	public String helloTo(String name) throws RemoteException;

	public GameSession getGameSession() throws RemoteException;

	public GameEvent joinGame(ClientRemote client, Player newPlayer) throws RemoteException;

	public GameEvent startGame() throws RemoteException;
	public GameEvent endGame() throws RemoteException;
	
	public GameEvent captainSendsCommend(CaptainCommend cmd) throws RemoteException;
	
	public GameEvent trySetSteeringWheelAngle(int newangle) throws RemoteException;
	public GameEvent trySetEngineThrust(int newEngineThrust) throws RemoteException;

}