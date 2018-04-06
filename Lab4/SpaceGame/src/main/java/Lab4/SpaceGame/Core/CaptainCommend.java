package Lab4.SpaceGame.Core;

import java.io.Serializable;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import Lab4.SpaceGame.Core.Player.Role;

public class CaptainCommend<T> implements Serializable {
	
	private String message;
	private Role playerRole;
	private SpaceshipMeasurements futureMeasurments;
	private BiPredicate<T, SpaceshipMeasurements> predicate;
	
	public CaptainCommend(String msg, Role playerRole, SpaceshipMeasurements futureMeasurments, BiPredicate<T, SpaceshipMeasurements> p) {
		this.message = msg;
		this.playerRole = playerRole;
		this.futureMeasurments = futureMeasurments;
		this.predicate = (BiPredicate & Serializable)p;
	}

	public String getMessage() {
		return message;
	}

	public BiPredicate<T, SpaceshipMeasurements> getPredicate() {
		return predicate;
	}

	public Role getPlayerRole() {
		return playerRole;
	}

	public SpaceshipMeasurements getFutureMeasurments() {
		return futureMeasurments;
	}

}
