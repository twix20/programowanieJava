package Lab4.SpaceGame.Server;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class GameEvent implements Serializable {
	
	private String id;
	private Date date;
	
	private EventType logType;
	private String message;
	private Object eventData;
	
	public GameEvent(EventType logType, String msg, Object data) {
		this.logType = logType;
		this.message = msg;
		this.eventData = data;
		
		id = UUID.randomUUID().toString();
		date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}

	public Object getEventData() {
		return eventData;
	}

	public String getId() {
		return id;
	}

	public EventType getLogType() {
		return logType;
	}
	
	public enum EventType {
		EVENT_GAME_STARTED,
		EVENT_GAME_ENDED,
		EVENT_GAME_MEASURMENT_PROPERTY_CHANGED,
		EVENT_CAPTAIN_SENDS_COMMAND,
		EVENT_NEW_PLAYER_JOINED,
	}

}
