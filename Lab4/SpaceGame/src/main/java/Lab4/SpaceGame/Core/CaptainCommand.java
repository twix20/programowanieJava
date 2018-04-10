package Lab4.SpaceGame.Core;

import java.io.Serializable;

import Lab4.SpaceGame.Core.Player.Role;
import Lab4.SpaceGame.Server.GameEvent;

public abstract class CaptainCommand<T> implements Serializable {
	
	private String message;
	private Role playerRole;
	private String commandType;
	
	public CaptainCommand(String msg, Role playerRole, String commandType) {
		this.message = msg;
		this.playerRole = playerRole;
		this.commandType = commandType;
	}

	public String getMessage() {
		return message;
	}

	public Role getPlayerRole() {
		return playerRole;
	}
	
	public boolean validate(T newValue, String commandType) {
		
		if(!this.commandType.equalsIgnoreCase(commandType)) {
			Utils.log("Chce " + this.commandType + " dostalem " + commandType);
			return false;
		}
		
		return validateValue(newValue);
	}
	
	protected abstract boolean validateValue(T newValue);
	public abstract GameEvent successEvent();
	public abstract void modifyOnSuccess(SpaceshipMeasurements measurments);

}
