package Lab1.Core.Presentation;

public class StatisticTableRow {
	private String name;
	private Object value;
	
	public StatisticTableRow(String n, Object v) {
		this.name = n;
		this.value = v;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
