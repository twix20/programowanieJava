package Lab10.TicTacToe.Core;

import java.util.function.BiFunction;
import java.util.function.Function;

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
		
		BoardPosition pos = getPos(x, y);
		
		if(pos.getValue() != null) //check if position is already taken, X or O
			return false;
		
		pos.setValue(value);

		return true;
	}
	
	public void restartBoard() {
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				positions[i][j] = new BoardPosition(j, i);
			}
		}
	}
	
	public boolean isFieldTaken(int x, int y) {
		return getPos(x, y).getValue() != null;
	}
	
	
	public boolean isPlayerWinner(Player p) {
		
		if(checkVerticalyWin(p.getValue()))
			return true;
		
		if(checkHorizontalyWin(p.getValue()))
			return true;
		
		if(checkDiagonalyRightTopToLeftBot(p.getValue()))
			return true;
		
		if(checkDiagonalyLeftTopToRightBot(p.getValue()))
			return true;
		
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
	
	public BoardPosition getPos(int x, int y) {
		return positions[y][x];
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
	
	private boolean checkDiagonalyLeftTopToRightBot(TicTacToeValue v) {
		return checkDiagonalyHelper(
				v, 
				(col, i) -> col + i, 
				(row, i) -> row + i
			);
	}
	
	private boolean checkDiagonalyRightTopToLeftBot(TicTacToeValue v) {
		
		return checkDiagonalyHelper(
			v, 
			(col, i) -> col - i, 
			(row, i) -> row + i
		);
	}
	
	private boolean checkDiagonalyHelper(TicTacToeValue v, BiFunction<Integer, Integer, Integer> modifyCol, BiFunction<Integer, Integer, Integer> modifyRow) {
		for (int col = 0; col < size; col++)
		{
		    for (int row = 0; row < size; row++)
		    {
		    	BoardPosition currentPos = getPos(col, row);
		    	Integer total = 0;
		    	
		    	if(currentPos.getValue() == v) {
			    	
		    		for(int i = 0; i < SAME_TIC_TAC_TOE_VALUE_WINNER_LENGTH; i++) {
		    			int x = modifyCol.apply(col, i);
		    			int y = modifyRow.apply(row, i);
		    			
		    			if(!isFieldValid(x, y)) break;
		    			
	    				TicTacToeValue tempV = getPos(x, y).getValue();
	    				if(tempV == v) {
	    					total += 1;

	    			    	if(total.equals(SAME_TIC_TAC_TOE_VALUE_WINNER_LENGTH))
	    			    		return true;
	    				}
	    				else {
	    					break;
	    				}
	    				
		    		}
		    	}
		    }
		}
		
		return false;
	}
	
	
	private boolean isFieldValid(int x, int y)
	{
		return x >= 0 && x < size &&
			   y >= 0 && y < size;
	}
	
}
