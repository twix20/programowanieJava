package Lab10.TicTacToe.Core;

import Lab10.TicTacToe.Utils.Utils;

public class Board {
	
	public static final int SAME_TIC_TAC_TOE_VALUE_WINNER_LENGTH = 5;
	
	private int size;
	private BoardPosition[][] positions;
	
	public Board(int size) {
		this.size = size;
		positions = new BoardPosition[this.size][this.size];
		
		restartBoard();
	}

	public int getSize() {
		return size;
	}

	public boolean tryMarkField(int x, int y, TicTacToeValue value) {
		
		BoardPosition pos = positions[x][y];
		
		if(pos.getValue() != null) //check if position is already taken, X or O
			return false;
		
		pos.setValue(value);

		return true;
	}
	
	public void restartBoard() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				positions[i][j] = new BoardPosition(i, j);
			}
		}
	}
	
	public boolean isFieldTaken(int x, int y) {
		return positions[x][y].getValue() != null;
	}
	
	
	public boolean isPlayerWinner(Player p) {
		
		if(checkVerticalyWin(p.getValue()))
			return true;
		
		if(checkHorizontalyWin(p.getValue()))
			return true;
		
		//TODO: add diagonaly winner
		
		return false;
	}
	
	public boolean areAllFieldsMarked() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(positions[i][j].getValue() == null)
					return false;
			}
		}
		
		return true;
	}
	
	
	//Winner Checcker
	private boolean checkVerticalyWin(TicTacToeValue v) {
		for (int col = 0; col < size; col++)
		{
		    Integer total = 0;
		    for (int row = 0; row < size; row++)
		    {
		    	TicTacToeValue currentPosV = positions[col][row].getValue();
		    	
		    	total = currentPosV == v ? total + 1 : 0;
		    	
		    	if(total.equals(SAME_TIC_TAC_TOE_VALUE_WINNER_LENGTH))
		    		return true;
		    }
		}
		return false;
	}
	
	private boolean checkHorizontalyWin(TicTacToeValue v) {
		for (int col = 0; col < size; col++)
		{
		    Integer total = 0;
		    for (int row = 0; row < size; row++)
		    {
		    	TicTacToeValue currentPosV = positions[row][col].getValue();
				    	
		    	total = currentPosV == v ? total + 1 : 0;
		    	
		    	if(total.equals(SAME_TIC_TAC_TOE_VALUE_WINNER_LENGTH))
		    		return true;
		    }
		}
		return false;
	}
	
	

}
