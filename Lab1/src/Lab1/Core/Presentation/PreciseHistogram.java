package Lab1.Core.Presentation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PreciseHistogram <T extends Number> {
	
	List<T> orginalData;
	List<T> xAxis;
	List<Double> yAxis;
	
	public PreciseHistogram(List<T> data, List<T> xAxis) {
		this.orginalData = data;
		this.xAxis = xAxis;
		
		
		yAxis = this.xAxis.stream()
				.map(x -> (double)countOccurence(this.orginalData, x))
				.collect(Collectors.toList());
		
	}
	
	public List<T> getXAxis() {
		return this.xAxis;
	}
	
	public List<Double> getYAxis() {
		return this.yAxis;
	}
	
	private <T> int countOccurence(List<T> list, T value){
		int occurences = 0;
		for(T o:list) {
			if(o.equals(value)) 
				occurences++;
		}
		
		return occurences;
	}

}
