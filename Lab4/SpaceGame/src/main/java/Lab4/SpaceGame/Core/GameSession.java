package Lab4.SpaceGame.Core;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Lab4.SpaceGame.Core.Player.Role;
import Lab4.SpaceGame.Core.CaptainCommands.EngineThrustCommand;
import Lab4.SpaceGame.Core.CaptainCommands.LightsCommand;
import Lab4.SpaceGame.Core.CaptainCommands.OilLevelCommand;
import Lab4.SpaceGame.Core.CaptainCommands.StearingWheelAngleCommand;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.GameEvent.EventType;

public class GameSession implements Serializable {

	private Status status;

	private SpaceshipMeasurements measurements;

	private Map<String, Player> players;

	private List<GameEvent> serverEvents;

	private CaptainCommand currentCaptainCommand;

	public GameSession() {

		players = new HashMap<>();
		serverEvents = new ArrayList<>();

		status = Status.NotStarted;
	}

	public GameEvent startGame() {

		if (isGameStarted())
			return null;

		this.status = Status.Running;
		this.setMeasurements(new SpaceshipMeasurements());

		GameEvent e = new GameEvent(EventType.EVENT_GAME_STARTED,
				String.format("Game started: players %d", players.values().size()),
				new HashSet<Role>(players.values().stream().map(p -> p.getRole()).collect(Collectors.toList())));
		serverEvents.add(e);

		Utils.log(String.format("Game started: players %d", players.values().size()));
		return e;
	}

	public GameEvent captainSendsCommend(CaptainCommand cmd) throws RemoteException {
		setCurrentCaptainCommand(cmd);

		GameEvent e = new GameEvent(EventType.EVENT_CAPTAIN_SENDS_COMMAND, "Captain sends commend: " + cmd.getMessage(),
				cmd);
		serverEvents.add(e);

		return e;
	}

	public GameEvent joinGame(Player newPlayer) throws RemoteException {

		String playerKey = newPlayer.getId();
		if ((newPlayer.isCaptain() && isCaptainInSquad()) || players.containsKey(playerKey)) {
			Utils.log(String.format("Player %s cant be Captain cuz there is a catain in the team already", playerKey));
			return null;
		}

		players.put(playerKey, newPlayer);

		GameEvent e = new GameEvent(EventType.EVENT_NEW_PLAYER_JOINED, "New player joined: " + newPlayer.getName(),
				newPlayer);
		serverEvents.add(e);

		Utils.log(String.format("Added player %s to the squad with role %s", newPlayer.getName(),
				newPlayer.getRole().name()));
		return e;
	}

	public GameEvent trySetSteeringWheelAngle(int newangle) throws RemoteException {
		return testCurrentCommand(newangle, StearingWheelAngleCommand.class.getSimpleName());
	}

	public GameEvent trySetEngineThrust(int newEngineThrust) throws RemoteException {
		return testCurrentCommand(newEngineThrust, EngineThrustCommand.class.getSimpleName());
	}

	public GameEvent trySetLights(boolean newLigths) {
		return testCurrentCommand(newLigths, LightsCommand.class.getSimpleName());
	}

	public GameEvent trySetOilLevel(int newOilLevel) {
		return testCurrentCommand(newOilLevel, OilLevelCommand.class.getSimpleName());
	}

	private GameEvent testCurrentCommand(Object newValue, String commandType) {

		GameEvent e = null;
		CaptainCommand currentCmd = getCurrentCaptainCommand();
		if (currentCmd == null)
			return e;

		try {
			if (currentCmd.validate(newValue, commandType)) {

				currentCmd.modifyOnSuccess(getMeasurements());
				e = currentCmd.successEvent();

				setCurrentCaptainCommand(null);
			}
		} catch (Exception ex) {
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

	public CaptainCommand getCurrentCaptainCommand() {
		return currentCaptainCommand;
	}

	public void setCurrentCaptainCommand(CaptainCommand cmd) {
		this.currentCaptainCommand = cmd;
	}

	public List<GameEvent> getServerEvents() {
		return serverEvents;
	}

	enum Status {
		NotStarted, Running, Finished
	}
}
