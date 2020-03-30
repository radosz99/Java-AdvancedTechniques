package algs;

import java.util.List;

import base.*;

/**
 * Class which holds specific informations about Insert Sort algorithm and gives the generic sorting method to solve the problem.
 * @author Radoslaw Lis
 */
public class InsertSort extends AbstractSorter{
    /**
     * @return Algorithm's description name  how and it works.
    */
	@Override
	public String description() {
		return "Insert Sort";
	}

    /**
     *  @return Logical value which gives information whether algorithm is stable. In other words it means whether
     *  two objects with the same values appear in the same order in output as they appear in input.
    */
	@Override
	public boolean isStable() {
		return true;
	}

    /**
     *  @return Logical value which gives information whether algorithm work in situ. In other words it means whether
     *  algorithm sorts the items without using additional temporary space to hold data (if yes it works in situ).
    */
	@Override
	public boolean isInSitu() {
		return true;
	}
	
    /**
     * The method used to sort the list of T objects (objects from classes implementing IElement interface) by the value.
     * <p>
     * The algorithm sorts the subarrays from 1- to n-elements subarray of size 1 is always sorted.
     * If value in a cell after sorted subarray is smaller than the last element is subarray we have to insert a value from that cell to the index of last element.
     * Then we have subarray of size-1 and still we have to compare with last element until the last element is smaller.
     * Stop condition is the end of the list the biggest subarrays of size n our list is sorted.
     * 
     * @param list
     * 	List of objects T (objects from classes implementing IElement interface) to sort.
     * @return The finally sorted list.
    */
	@Override
	public <T extends IElement> List<T> solve(List<T> list) {

		T key;
	    for (int i = 1; i < list.size(); i++) { 
	        key = list.get(i); 
	        int j = i- 1; 

	        while (j >= 0 && list.get(j).getValue()> key.getValue()) { 
	            list.set(j + 1,list.get(j)); 
	            j--;
	        } 
	        list.set(j + 1,key); 

	    } 
	    
    
	return list;
	}
	
}
