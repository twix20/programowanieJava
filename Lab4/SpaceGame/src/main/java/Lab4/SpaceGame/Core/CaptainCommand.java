package Lab4.SpaceGame.Core;

import java.io.Serializable;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import Lab4.SpaceGame.Core.Player.Role;
import Lab4.SpaceGame.Server.GameEvent;

public abstract class CaptainCommand<T> implements Serializable {
	
	private String message;
	private Role playerRole;
	
	public CaptainCommand(String msg, Role playerRole) {
		this.message = msg;
		this.playerRole = playerRole;
	}

	public String getMessage() {
		return message;
	}

	public Role getPlayerRole() {
		return playerRole;
	}
	
	public abstract boolean validate(T newValue);
	public abstract GameEvent successEvent();
	public abstract void modifyOnSuccess(SpaceshipMeasurements measurments);

}
