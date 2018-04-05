package Lab4.SpaceGame.Core;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import Lab4.SpaceGame.Client.ClientRemote;

public class GameSession {
	
	private Status status;
	
	private SpaceshipMeasurements measurements;
	private Map<ClientRemote, Player> players;
	
	public GameSession() {
		players = new HashMap<>();
	}

	public boolean startGame() {
		
		if(isGameStarted())
			return false;
		
		this.status = Status.Running;
		this.measurements = new SpaceshipMeasurements();
		
		notifyAll(c -> {
			try {
				c.handleGameStarted();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		Utils.log(String.format("Game started: players %d", players.values().size()));
		
		return true;
	}
	
	public boolean addPlayer(ClientRemote client, Player newPlayer) throws RemoteException {
		
		if(newPlayer.isCaptain() && isCaptainInSquad()) {
			Utils.log(String.format("Player %s cant be Captain cuz there is a catain in the team already", newPlayer.getName()));
			return false;
		}
		
		players.put(client, newPlayer);
		
		Utils.log(String.format("Added player %s to the squad with role %s", newPlayer.getName(), newPlayer.getRole().name()));
		return true;
	}
	
	public boolean isCaptainInSquad() {
		return getPlayers().values().stream().anyMatch(p -> p.isCaptain());
	}
	
	public boolean isGameStarted() {
		return getStatus() != Status.NotStarted;
	}
	
	private void notifyAll(Consumer<ClientRemote> c) {
		getPlayers().keySet().stream().forEach(client -> {
			c.accept(client);
		});
	}
	
	public Status getStatus() {
		return status;
	}
	
	public Map<ClientRemote, Player> getPlayers() {
		return players;
	}

	public SpaceshipMeasurements getMeasurements() {
		return measurements;
	}

	enum Status {
		NotStarted,
		Running,
		Finished
	}
}

