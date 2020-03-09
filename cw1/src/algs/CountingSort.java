package algs;

import java.util.LinkedList;
import java.util.List;

import base.*;

/**
 * Class which holds specific informations about Counting Sort algorithm and gives the generic sorting method to solve the problem
 * @author Radoslaw Lis
 */
public class CountingSort extends AbstractIntSorter{
    /**
     * @return algorithm's description - name  how and it works
    */
	@Override
	public String description() {
		return "Counting Sort";
	}

    /**
     *  @return logical value which gives information whether algorithm is stable. In other words it means whether
     *  two objects with the same values appear in the same order in output as they appear in input
    */
	@Override
	public boolean isStable() {
		return true;
	}

    /**
     *  @return logical value which gives information whether algorithm work in situ. In other words it means whether
     *  algorithm sorts the items without using additional temporary space to hold data (if yes it works in situ)
    */
	@Override
	public boolean isInSitu() {
		return false;
	}
	
    /**
     * h
     * @return the finally sorted list
    */
	@Override
	public <T extends IElement> List<T> solve(List<T> list) {
		int min=(int) list.get(0).getValue(), max;
		max = min;
		
		for (int i = 1; i < list.size(); i++) {
			if(list.get(i).getValue() > max)
				max = (int) list.get(i).getValue();
			if(list.get(i).getValue() < min)
				min = (int) list.get(i).getValue();
		}
		
		int range = max - min + 1;
		
		int[] holes = new int[range];
		
	    for(int i = 0; i < list.size(); i++) {
	        holes[(int) list.get(i).getValue()-min]++;
	    }
	    for(int i = 1; i <range;++i) {
	        holes[i] += holes[i - 1];
	    }
	    
	    LinkedList<T> output = new LinkedList<T>(); 
	    
	    for(int i = 0; i < list.size(); i++) {
	    	output.add(list.get(i));
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
