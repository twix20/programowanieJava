package Lab1.Core;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class Utilities {
	
	public static <T> Stream<T> toStream(List<T> list){
		return Arrays.stream(list.toArray((T[])(new Object[list.size()])));
	}

}
