package algs;

import java.util.List;

import base.*;

/**
 * Class which holds specific informations about Insert Sort algorithm and gives the generic sorting method to solve the problem
 * @author Radoslaw Lis
 */
public class InsertSort extends AbstractFloatSorter{
    /**
     * @return algorithm's description - name  how and it works
    */
	@Override
	public String description() {
		return "Insert Sort";
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
		return true;
	}
	
    /**
     * h
     * @return the finally sorted list
    */
	@Override
	public <T extends IElement> List<T> solve(List<T> list) {

		T key;
	    for (int i = 1; i < list.size(); ++i) { 
	        key = list.get(i); 
	        int j = i - 1; 

	        while (j >= 0 && list.get(j).getValue()> key.getValue()) { 
	            list.set(j + 1,list.get(j)); 
	            j = j - 1; 
	        } 
	        list.set(j + 1,key); 
	    } 
	    
    
	return list;
	}
	
}
