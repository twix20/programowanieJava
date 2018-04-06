package Lab4.SpaceGame.Server;

import java.io.Serializable;

public class PropertyEvent implements Serializable{

	private String propertyName;
	private Object propertyValue;
	
	public PropertyEvent(String propertyName, Object propertyValue) {
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	public String getPropertyName() {
		return propertyName;
	}


	public Object getPropertyValue() {
		return propertyValue;
	}
}
