import java.util.ArrayList;
import java.util.List;

import generators.*;
import algs.*;
import base.*;

public class Test {

	public static void main(String[] args) {
		IntElementGenerator data = new IntElementGenerator();
		List<IntElement> list = new ArrayList<>();
		 
		list = data.getData(10,10);
		PigeonHoleSort problem = new PigeonHoleSort();
		CountingSort problem2 = new CountingSort();

		problem.solve(list);
		showList(list);
	}
	
	public static void showList(List<IntElement> list) {
		for(IntElement i : list) {
			System.out.println(i);
		}
	}
	
	//http://web.cs.unlv.edu/larmore/Courses/CSC477/inSitu.html
}
	

