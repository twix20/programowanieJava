package Lab4.SpaceGame.Core;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.UUID;

public class Player implements Remote, Serializable {
	
	private String id;
	private String name;
	private Role role;
	
	public Player(String name, Role role) {
		this.name = name;
		this.role = role;
		
		this.id = UUID.randomUUID().toString();
	}
	
	public boolean isCaptain() {
		return getRole() == Role.Captain;
	}
	
	public String getName() {
		return name;
	}

	public Role getRole() {
		return role;
	}

	public String getId() {
		return id;
	}

	public enum Role {
		Captain,
		
		Mechanic,
		Steersman
	}
}
