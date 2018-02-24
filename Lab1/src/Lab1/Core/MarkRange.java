package Lab1.Core;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MarkRange {
	private double from;
	private double to;
	private String mark;

	public MarkRange(double from, double to, String mark) {
		this.from = from;
		this.to = to;
		this.mark = mark;
	}

	public double getFrom() {
		return from;
	}

	public double getTo() {
		return to;
	}

	public String getMark() {
		return mark;
	}
	
	public boolean isInRange(double p) {
		double r = round(p);
		
		return from < r && r < to;
	}
	
	private double round(double v) {
		return new BigDecimal(v).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
}