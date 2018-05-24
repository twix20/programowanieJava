package Lab10.TicTacToe.Core;

public abstract class Player {
	
	private String name;
	private TicTacToeValue value;
	
	public Player(String name, TicTacToeValue value) {
		this.name = name;
		this.value = value;
		
	}

	public String getName() {
		return name;
	}

	public TicTacToeValue getValue() {
		return value;
	}
}
