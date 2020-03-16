package algs;

import java.util.List;

import base.*;

/**
 * Class which holds specific informations about QuickSort algorithm and gives the generic sorting method to solve the problem.
 * @author Radoslaw Lis
 */
public class QuickSort extends AbstractSorter {
    /**
     * @return Algorithm's description - name  how and it works.
    */
	@Override
	public String description() {
		return "Quick Sort";
	}

    /**
     *  @return Logical value which gives information whether algorithm is stable. In other words it means whether
     *  two objects with the same values appear in the same order in output as they appear in input.
    */
	@Override
	public boolean isStable() {
		return false;
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
     *  Method starts the first Quicksort process with call quicksort method with the parameters such as list and first and last index to start first process of Quicksort.
     *  
     *  @return The finally sorted list.
    */
	@Override
	public <T extends IElement> List<T> solve(List<T> list) {
		quicksort(list, 0, list.size()-1);
	    
		return list;
	}

    /**
     * Method supports each of Quicksort elementary sorting called recursively with the given parameters.
     * The pivot is chosen and the idea is to have smaller values to the left of the pivot and have bigger values to the right of the pivot.
     * Using swaps we move values to the proper sides and next we call recursively two quicksort methods to sort the left and the right side.
     * 
     * @param list
     *		List of objects T (objects from classes implementing IElement interface) to sort
     * @param left
     * 		Index of the first element in (sub)array to sort
     * @param right
     * 		Index of the last element in (sub)array to sort
    */
	 private static  <T extends IElement> void quicksort(List<T> list, int left, int right) {
	    int i, j;
	    T pivot, y;
	    i = left; j = right;
	    pivot = list.get((left + right)/2);
	    
	    do {
	      while((list.get(i).getValue() < pivot.getValue()) && (i < right)) 
	    	  i++;
	      while((pivot.getValue() < list.get(j).getValue()) && (j > left)) 
	    	  j--;
	      
	      if (i <= j) {
	        y = list.get(i);
	        list.set(i,list.get(j));
	        list.set(j,y);
	        i++; 
	        j--;
	      }
	    } while(i <= j);
	    
	    if(left < j) 
	    	quicksort(list, left, j);
	    if(i < right) 
	    	quicksort(list, i, right);
	  }

}
