package Lab5.GenClassificator.Entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "FLAGELLA")
public class Flagella {
	
	@DatabaseField(columnName = "ALPHA", canBeNull = false)
	private int alpha;
	
	@DatabaseField(columnName = "BETA", canBeNull = false)
	private int beta;
	
	@DatabaseField(columnName = "NUMBER", canBeNull = false)
	private int number;
	
	public int getAlpha() {
		return alpha;
	}
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
	public int getBeta() {
		return beta;
	}
	public void setBeta(int beta) {
		this.beta = beta;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
