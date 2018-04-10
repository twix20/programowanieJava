package Lab5.GenClassificator.Entities;

import javax.xml.bind.annotation.XmlElement;

public class Examined {
	
	private String genotype;
	
	private String clazz;
	
	public String getGenotype() {
		return genotype;
	}
	public void setGenotype(String genotype) {
		this.genotype = genotype;
	}
	
	@XmlElement(name="class")
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
