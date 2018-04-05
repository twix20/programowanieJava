package Lab4.SpaceGame.Core;

import java.io.Serializable;

public class SpaceshipMeasurements implements Serializable {
	//Mechanic
	private int engineThrust = 10;
	
	//Steersman
	private float steeringWheelAngle = 90.0f;

	public int getEngineThrust() {
		return engineThrust;
	}

	public void setEngineThrust(int engineThrust) {
		this.engineThrust = engineThrust;
	}

	public float getSteeringWheelAngle() {
		return steeringWheelAngle;
	}

	public void setSteeringWheelAngle(float steeringWheelAngle) {
		this.steeringWheelAngle = steeringWheelAngle;
	}
}
