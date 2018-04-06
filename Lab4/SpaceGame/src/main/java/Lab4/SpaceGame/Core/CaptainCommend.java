package Lab4.SpaceGame.Core;

import java.io.Serializable;
import java.util.function.Predicate;

import Lab4.SpaceGame.Core.Player.Role;

public class CaptainCommend implements Serializable {
	
	private String message;
	private Role playerRole;
	private Predicate<SpaceshipMeasurements> predicate;
	
	public CaptainCommend(String msg, Role playerRole, Predicate<SpaceshipMeasurements> p) {
		this.message = msg;
		this.playerRole = playerRole;
		this.predicate = (Predicate & Serializable)p;
	}

	public String getMessage() {
		return message;
	}

	public Predicate<SpaceshipMeasurements> getPredicate() {
		return predicate;
	}

	public Role getPlayerRole() {
		return playerRole;
	}

}
