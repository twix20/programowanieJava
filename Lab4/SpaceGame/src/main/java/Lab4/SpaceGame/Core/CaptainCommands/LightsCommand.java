package Lab4.SpaceGame.Core.CaptainCommands;

import Lab4.SpaceGame.Core.CaptainCommand;
import Lab4.SpaceGame.Core.Player.Role;
import Lab4.SpaceGame.Core.SpaceshipMeasurements;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.PropertyEvent;
import Lab4.SpaceGame.Server.GameEvent.EventType;

public class LightsCommand extends CaptainCommand<Boolean> {

	boolean desiredValue;
	
	public LightsCommand(boolean desiredValue) {
		super("Set Ligths to " + desiredValue, Role.Steersman);

		this.desiredValue = desiredValue;
	}

	@Override
	public boolean validate(Boolean newValue) {
		return desiredValue == newValue;
	}

	@Override
	public GameEvent successEvent() {
		return new GameEvent(EventType.EVENT_GAME_MEASURMENT_PROPERTY_CHANGED, "Ligths is now " + desiredValue, new PropertyEvent("Lights", desiredValue));
	}

	@Override
	public void modifyOnSuccess(SpaceshipMeasurements measurments) {
		measurments.setLights(desiredValue);
	}

}
