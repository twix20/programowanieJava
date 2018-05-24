package Lab10.TicTacToe.Core;


public class BoardPosition {
	
	private int x, y;
	private TicTacToeValue value;
	
	public BoardPosition(int x, int y) {
		this.x = x;
		this.y = y;
		
		this.value = null;
	}

	public TicTacToeValue getValue() {
		return value;
	}

	public void setValue(TicTacToeValue value) {
		this.value = value;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
