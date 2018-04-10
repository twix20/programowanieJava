package Lab5.GenClassificator.Entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "EXAMINED")
public class Examined {
	
	@DatabaseField(columnName = "GENOTYPE", canBeNull = false)
	private String genotype;
	
	@DatabaseField(columnName = "CLASS", canBeNull = false)
	private String clazz;
	
	public String getGenotype() {
		return genotype;
	}
	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
