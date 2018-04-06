package Lab4.SpaceGame.Core;

import java.io.Serializable;

public class SpaceshipMeasurements implements Serializable {
	//Mechanic
	private int engineThrust = 10;
	
	//Steersman
	private int steeringWheelAngle = 0;

	public int getEngineThrust() {
		return engineThrust;
	}

	public void setEngineThrust(int engineThrust) {
		this.engineThrust = engineThrust;
	}

	public int getSteeringWheelAngle() {
		return steeringWheelAngle;
	}

	public void setSteeringWheelAngle(int steeringWheelAngle) {
		this.steeringWheelAngle = steeringWheelAngle;
	}
}
