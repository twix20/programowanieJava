package Lab4.SpaceGame.Core.CaptainCommands;

import Lab4.SpaceGame.Core.CaptainCommand;
import Lab4.SpaceGame.Core.SpaceshipMeasurements;
import Lab4.SpaceGame.Core.Player.Role;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.PropertyEvent;
import Lab4.SpaceGame.Server.GameEvent.EventType;

public class OilLevelCommand extends CaptainCommand<Integer> {

	int desiredValue;
	
	public OilLevelCommand(int desiredValue) {
		super("Set Oil Level to " + desiredValue, Role.Mechanic, OilLevelCommand.class.getSimpleName());
		
		this.desiredValue = desiredValue;
	}
	
	@Override
	public boolean validateValue(Integer newValue) {
		return desiredValue == newValue;
	}

	@Override
	public GameEvent successEvent() {
		return new GameEvent(EventType.EVENT_GAME_MEASURMENT_PROPERTY_CHANGED, "Oil Level is now " + desiredValue, new PropertyEvent("OilLevel", desiredValue));
	}

	@Override
	public void modifyOnSuccess(SpaceshipMeasurements measurments) {
		measurments.setOilLevel(desiredValue);
	}

}
