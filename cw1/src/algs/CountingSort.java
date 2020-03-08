package algs;

import java.util.LinkedList;
import java.util.List;

import base.AbstractIntSorter;
import base.IntElement;

public class CountingSort extends AbstractIntSorter{
	String name = "Counting Sort";
	boolean stable = true;
	boolean inSitu = false;
	
	@Override
	public List<IntElement> solve(List<IntElement> list) {
		int range = 10;
		int[] holes = new int[range];
		
	    for(int i = 0; i < list.size(); i++) {
	        holes[(int) list.get(i).getValue()]++;
	    }
	    for(int i = 1; i <range;++i) {
	        holes[i] += holes[i - 1];
	    }
	    
	    LinkedList<IntElement> output = new LinkedList<IntElement>(); 
	    
	    for(int i = 0; i < list.size(); i++) {
	    	output.add(new IntElement("", 0));
	    }
	    
	  
	    for(int i = list.size()-1;i>=0;i--) {
	    	output.set(holes[(int) list.get(i).getValue()]-1,list.get(i));
	    	--holes[(int) list.get(i).getValue()];
	    }
	    
	    for(int i=0;i<list.size();i++) {
	    	list.set(i, output.get(i));
	    }

		return list;
	}
	
}
