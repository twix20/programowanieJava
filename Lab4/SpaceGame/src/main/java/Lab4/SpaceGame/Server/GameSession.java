package Lab4.SpaceGame.Server;

import java.util.ArrayList;
import java.util.List;

public class GameSession {
	
	private Status status;
	private List<Player> players;
	
	public GameSession() {
		players = new ArrayList<>();
	}

	public boolean startGame() {
		
		if(getStatus() == Status.NotStarted) {
			this.status = Status.Running;
			return true;
		}
		
		return false;
	}
	
	public boolean addPlayer(Player newPlayer) {
		
		if(newPlayer.isCaptain() && isCaptainInSquad()) {
			Utils.log(String.format("Player %s cant be Captain cuz there is a catain in the team already", newPlayer.getName()));
			return false;
		}
		
		players.add(newPlayer);
		
		Utils.log(String.format("Added player %s to the squad with role %s", newPlayer.getName(), newPlayer.getRole().name()));
		return true;
	}
	
	public boolean isCaptainInSquad() {
		return getPlayers().stream().anyMatch(p -> p.isCaptain());
	}
	
	public Status getStatus() {
		return status;
	}
	
	public List<Player> getPlayers() {
		return players;
	}

	enum Status {
		NotStarted,
		Running,
		Finished
	}
}

