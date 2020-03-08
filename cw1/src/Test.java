import java.util.ArrayList;
import java.util.List;

import generators.*;
import algs.*;
import base.*;

public class Test {

	public static void main(String[] args) {
		IntElementGenerator data = new IntElementGenerator();
		FloatElementGenerator data2 = new FloatElementGenerator();
		List<IntElement> list = new ArrayList<>();
		List<FloatElement> list2 = new ArrayList<>();
		
		list = data.getIntData(10,10);		//liczba elementow i zakre (0-x)
		list2 = data2.getFloatData(10, 1); 	//liczba elementow i zakre (0-x)
		
		PigeonHoleSort problem = new PigeonHoleSort();
		CountingSort problem2 = new CountingSort();
		QuickSort problem3 = new QuickSort();

		problem3.solve2(list2);
		//problem.solve(list);
		showList(list2);
	}
	
	public static <T> void showList(List<T> list) {
		for(T i : list) {
			System.out.println(i);
		}
	}
	
	//http://web.cs.unlv.edu/larmore/Courses/CSC477/inSitu.html
}
	

