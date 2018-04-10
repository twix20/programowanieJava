package Lab5.GenClassificator.Entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "TOUGHNESS")
public class Toughness {
	
	@DatabaseField(columnName = "BETA", canBeNull = false)
	private int beta;
	
	@DatabaseField(columnName = "GAMMA", canBeNull = false)
	private int gamma;
	
	@DatabaseField(columnName = "RANK", canBeNull = false)
	private char rank;
	
	public int getBeta() {
		return beta;
	}
	public void setBeta(int beta) {
		this.beta = beta;
	}
	public int getGamma() {
		return gamma;
	}
	public void setGamma(int gamma) {
		this.gamma = gamma;
	}
	public char getRank() {
		return rank;
	}
	public void setRank(char rank) {
		this.rank = rank;
	}
}
