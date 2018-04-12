package Lab5.GenClassificator.Entities;

public class Genotype {
	
	private char[] _genotype = new char[6];
	
	public Genotype(String genotype) {
	
		genotype = genotype.trim();
		
		boolean anyNotDigit = genotype.chars().anyMatch(c -> !Character.isDigit(c));
		if(genotype.length() != 6 || anyNotDigit)
			throw new IllegalArgumentException("genotype");
		
		_genotype = genotype.toCharArray();
	}
	
	public String getGenotype() {
		return String.copyValueOf(_genotype);
	}
	
	public int getAlpha() {
		return Character.getNumericValue(_genotype[0]) * 10 + Character.getNumericValue(_genotype[5]);
	}
	public int getBeta() {
		return Character.getNumericValue(_genotype[1]) * 10 + Character.getNumericValue(_genotype[4]);
	}
	public int getGamma() {
		return Character.getNumericValue(_genotype[2]) * 10 + Character.getNumericValue(_genotype[3]);
	}
}
