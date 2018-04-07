package Lab4.SpaceGame.Core;

import java.io.Serializable;

public class SpaceshipMeasurements implements Serializable {
	//Mechanic
	private int engineThrust = 10;
	
	//Steersman
	private int steeringWheelAngle = 0;
	private boolean lights = false;
	
	public SpaceshipMeasurements() {}

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

	public boolean isLights() {
		return lights;
	}

	public void setLights(boolean lights) {
		this.lights = lights;
	}
}
