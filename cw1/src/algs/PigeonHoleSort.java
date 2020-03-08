package algs;

import java.util.LinkedList;
import java.util.List;

import base.AbstractIntSorter;
import base.IntElement;

public class PigeonHoleSort extends AbstractIntSorter{
	String name = "Pigeonhole Sort";
	boolean stable = true;
	boolean inSitu = false;
	
	
	@Override
	public List<IntElement> solve(List<IntElement> list) {
		int min=(int) list.get(0).getValue(), max, range;
		max = min;
		
		for (int i = 1; i < list.size(); i++) {
			if(list.get(i).getValue() > max)
				max = (int) list.get(i).getValue();
			if(list.get(i).getValue() < min)
				min = (int) list.get(i).getValue();
		}
		
		range = max - min + 1;
		
	    @SuppressWarnings("unchecked")
	    LinkedList<IntElement>[] holes = (LinkedList<IntElement>[]) new LinkedList[range];
	    
	    for(int i = 0; i < range; i++) {
	        holes[i] = new LinkedList<IntElement>();
	    }
	    for(IntElement t : list) {
	        holes[(int) (t.getValue()-min)].add(t);
	    }
	    
	    int k = 0;
	    for(int i = 0; i < range; i++) {
	        for(int j=0; j< holes[i].size();j++,k++) {
	            list.set(k, holes[i].get(j));
	         
	        }
	    }

		return list;
	}
}
