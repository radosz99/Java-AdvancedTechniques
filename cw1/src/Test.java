import java.util.ArrayList;
import java.util.List;

import generators.*;
import algs.*;
import base.*;

public class Test {

	public static void main(String[] args) {
		
		int range = 5;
		int size = 10;
		IntElementGenerator data = new IntElementGenerator();
		FloatElementGenerator data2 = new FloatElementGenerator();
		List<IntElement> intList = new ArrayList<>();
		List<FloatElement> floatList = new ArrayList<>();
		PigeonHoleSort ps = new PigeonHoleSort();
		CountingSort cs = new CountingSort();
		QuickSort qs = new QuickSort();
		InsertSort is = new InsertSort();
		
		intList = data.getIntData(size,range);			//liczba elementow i zakres (0-x)
		floatList = data2.getFloatData(size,range); 	//liczba elementow i zakres (0-x)
		
		
		showList(intList);
		System.out.println("\n");
		showList(ps.solve(intList));
	}
	
	public static <T> void showList(List<T> list) {
		for(T i : list) {
			System.out.println(i);
		}
	}
	
	
}
	

