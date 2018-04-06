package Lab4.SpaceGame.Core.CaptainCommands;

import Lab4.SpaceGame.Core.CaptainCommand;
import Lab4.SpaceGame.Core.Player.Role;
import Lab4.SpaceGame.Core.SpaceshipMeasurements;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.PropertyEvent;
import Lab4.SpaceGame.Server.GameEvent.EventType;

public class StearingWheelAngleCommand extends CaptainCommand<Integer> {

	int desiredValue;
	
	public StearingWheelAngleCommand(int desiredValue, SpaceshipMeasurements futureMeasurments) {
		super("Set Steering Wheel Angle to " + desiredValue, Role.Steersman, futureMeasurments);
		
		this.desiredValue = desiredValue;
	}

	@Override
	public boolean validate(Integer newValue) {
		return getFutureMeasurments().getSteeringWheelAngle() == newValue;
	}

	@Override
	public GameEvent successEvent() {
		return new GameEvent(EventType.EVENT_GAME_MEASURMENT_PROPERTY_CHANGED, "Steering wheel angle is now " + desiredValue, new PropertyEvent("SteeringWheelAngle", desiredValue));
	}

}
