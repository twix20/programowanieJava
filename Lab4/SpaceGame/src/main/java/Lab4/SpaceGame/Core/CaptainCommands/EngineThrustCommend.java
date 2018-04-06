package Lab4.SpaceGame.Core.CaptainCommands;

import Lab4.SpaceGame.Core.CaptainCommand;
import Lab4.SpaceGame.Core.Player.Role;
import Lab4.SpaceGame.Core.SpaceshipMeasurements;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.PropertyEvent;
import Lab4.SpaceGame.Server.GameEvent.EventType;

public class EngineThrustCommend extends CaptainCommand<Integer> {

	int desiredValue;
	
	public EngineThrustCommend(int desiredValue, SpaceshipMeasurements futureMeasurments) {
		super("Set Engine Thrust to " + desiredValue, Role.Mechanic, futureMeasurments);
		// TODO Auto-generated constructor stub
		
		this.desiredValue = desiredValue;
	}

	@Override
	public boolean validate(Integer newValue) {
		return this.desiredValue == newValue;
	}

	@Override
	public GameEvent successEvent() {
		return new GameEvent(EventType.EVENT_GAME_MEASURMENT_PROPERTY_CHANGED, "Engine Thrust is now " + desiredValue, new PropertyEvent("EngineThrust", desiredValue));
	}
}
