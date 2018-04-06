package Lab4.SpaceGame.Core;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.PropertyEvent;
import Lab4.SpaceGame.Server.GameEvent.EventType;

public class GameSession implements Serializable {
	
	private Status status;
	
	private SpaceshipMeasurements futureMeasurements;
	private SpaceshipMeasurements measurements;
	
	private Map<String, Player> players;
	
	private List<GameEvent> serverEvents;
	
	private CaptainCommend currentCaptainCommend;
	
	
	public GameSession() {
		
		players = new HashMap<>();
		serverEvents = new ArrayList<>();
		
		status = Status.NotStarted;
	}

	public GameEvent startGame() {
		
		if(isGameStarted())
			return null;
		
		this.status = Status.Running;
		this.setMeasurements(new SpaceshipMeasurements());
		this.setFutureMeasurements(new SpaceshipMeasurements(this.measurements));

		GameEvent e = new GameEvent(EventType.EVENT_GAME_STARTED, String.format("Game started: players %d", players.values().size()), null);
		serverEvents.add(e);
		
		Utils.log(String.format("Game started: players %d", players.values().size()));
		return e;
	}
	
	public GameEvent captainSendsCommend(CaptainCommend cmd) throws RemoteException {
		this.currentCaptainCommend = cmd;
		
		this.setFutureMeasurements(cmd.getFutureMeasurments());
		
		GameEvent e = new GameEvent(EventType.EVENT_CAPTAIN_SENDS_COMMEND, "Captain sends commend: " + cmd.getMessage(), cmd);
		serverEvents.add(e);
		
		return e;
	}
	
	public CaptainCommend getCurrentCaptainCommend() {
		return currentCaptainCommend;
	}
	
	public GameEvent joinGame(Player newPlayer) throws RemoteException {
		
		if((newPlayer.isCaptain() && isCaptainInSquad()) || players.containsKey(newPlayer.getName())) {
			Utils.log(String.format("Player %s cant be Captain cuz there is a catain in the team already or name is not unique", newPlayer.getName()));
			return null;
		}
		
		players.put(newPlayer.getName(), newPlayer);
		
		GameEvent e = new GameEvent(EventType.EVENT_NEW_PLAYER_JOINED, "New player joined: " + newPlayer.getName(), newPlayer);
		serverEvents.add(e);
		
		Utils.log(String.format("Added player %s to the squad with role %s", newPlayer.getName(), newPlayer.getRole().name()));
		return e;
	}
	
	public GameEvent trySetSteeringWheelAngle(int newangle) throws RemoteException {
		
		System.out.println("newangle " + newangle);
		
		CaptainCommend currentCmd = getCurrentCaptainCommend();
		
		GameEvent e = null;
		if(currentCmd.getPredicate().test(newangle, getFutureMeasurements())) {
			setMeasurements(getFutureMeasurements());
			
			e = new GameEvent(EventType.EVENT_GAME_MEASURMENT_PROPERTY_CHANGED, "Steering wheel angle is now " + newangle, new PropertyEvent("SteeringWheelAngle", newangle));
		}
		
		return e;
	}

	public GameEvent trySetEngineThrust(int newEngineThrust) throws RemoteException {
		
		System.out.println("newEngineThrust " + newEngineThrust);
		
		CaptainCommend currentCmd = getCurrentCaptainCommend();
		
		GameEvent e = null;
		if(currentCmd.getPredicate().test(newEngineThrust, getFutureMeasurements())) {
			setMeasurements(getFutureMeasurements());
			
			e = new GameEvent(EventType.EVENT_GAME_MEASURMENT_PROPERTY_CHANGED, "Engine Thrust is now " + newEngineThrust, new PropertyEvent("EngineThrust", newEngineThrust));
		}
		
		return e;
	}
	
	public boolean isCaptainInSquad() {
		return getPlayers().values().stream().anyMatch(p -> p.isCaptain());
	}
	
	public boolean isGameStarted() {
		return getStatus() != Status.NotStarted;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public Map<String, Player> getPlayers() {
		return players;
	}

	public SpaceshipMeasurements getMeasurements() {
		return measurements;
	}
	
	public void setMeasurements(SpaceshipMeasurements measurements) {
		this.measurements = measurements;
	}
	
	public List<GameEvent> getServerEvents() {
		return serverEvents;
	}

	public SpaceshipMeasurements getFutureMeasurements() {
		return futureMeasurements;
	}

	public void setFutureMeasurements(SpaceshipMeasurements futureMeasurements) {
		this.futureMeasurements = futureMeasurements;
	}

	enum Status {
		NotStarted,
		Running,
		Finished
	}
}

