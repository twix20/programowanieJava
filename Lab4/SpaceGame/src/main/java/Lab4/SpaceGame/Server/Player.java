package Lab4.SpaceGame.Server;

import java.io.Serializable;

public class Player implements Serializable {
	
	private String name;
	private Role role;
	
	public Player(String name, Role role) {
		this.name = name;
		this.role = role;
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

	public enum Role {
		Captain,
		
		Mechanic,
		Steersman
	}
}