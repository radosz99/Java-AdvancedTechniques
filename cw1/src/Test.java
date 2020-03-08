import java.util.ArrayList;
import java.util.List;

import generators.*;
import algs.*;
import base.*;

public class Test {

	public static void main(String[] args) {
		IntElementGenerator data = new IntElementGenerator();
		FloatElementGenerator data2 = new FloatElementGenerator();
		List<IntElement> intList = new ArrayList<>();
		List<FloatElement> floatList = new ArrayList<>();
		PigeonHoleSort problem = new PigeonHoleSort();
		CountingSort problem2 = new CountingSort();
		QuickSort problem3 = new QuickSort();
		InsertSort problem4 = new InsertSort();
		
		intList = data.getIntData(20,10);		//liczba elementow i zakres (0-x)
		floatList = data2.getFloatData(500, 1); 	//liczba elementow i zakres (0-x)
		
		
		showList(intList);
		problem3.solve(intList);
		System.out.println("\n");
		showList(intList);
	}
	
	public static <T> void showList(List<T> list) {
		for(T i : list) {
			System.out.println(i);
		}
	}
	
	
}
	

