import java.util.ArrayList;
import java.util.List;

import algs.PigeonHoleSort;
import base.FloatElement;
import base.IElement;

public class Test {
	public static void main(String[] args) {
		List<IElement> list = new ArrayList<IElement>();
		list.add(new FloatElement("word",5.6f));
		list.add(new FloatElement("word",7.3f));
		list.add(new FloatElement("word",5.2f));
		list.add(new FloatElement("word",3.4f));
		
		PigeonHoleSort pig = new PigeonHoleSort();
		pig.solve(list);
		
		for(IElement f : list){
			System.out.println(f);
		}
	}
}
