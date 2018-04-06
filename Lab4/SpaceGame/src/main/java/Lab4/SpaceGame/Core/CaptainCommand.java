package Lab4.SpaceGame.Core;

import java.io.Serializable;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import Lab4.SpaceGame.Core.Player.Role;
import Lab4.SpaceGame.Server.GameEvent;

public abstract class CaptainCommand<T> implements Serializable {
	
	private String message;
	private Role playerRole;
	private SpaceshipMeasurements futureMeasurments;
	
	public CaptainCommand(String msg, Role playerRole, SpaceshipMeasurements futureMeasurments) {
		this.message = msg;
		this.playerRole = playerRole;
		this.futureMeasurments = futureMeasurments;
	}

	public String getMessage() {
		return message;
	}

	public Role getPlayerRole() {
		return playerRole;
	}

	public SpaceshipMeasurements getFutureMeasurments() {
		return futureMeasurments;
	}
	
	public abstract boolean validate(T newValue);
	public abstract GameEvent successEvent();

}
