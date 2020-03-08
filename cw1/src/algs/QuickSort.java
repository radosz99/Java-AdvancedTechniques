package algs;

import java.util.List;

import base.*;

public class QuickSort extends AbstractFloatSorter {

	String name = "Quick Sort";
	boolean stable = true;
	boolean inSitu = true;
	
	
	@Override
	public List<FloatElement> solve2(List<FloatElement> list) {
		qs(list, 0, list.size()-1);
	    
		return list;
	}


	  private static void qs(List<FloatElement> list, int left, int right)
	  {
	    int i, j;
	    FloatElement x, y;
	    i = left; j = right;
	    x = list.get((left+right)/2);
	    
	    do {
	      while((list.get(i).getValue() < x.getValue()) && (i < right)) 
	    	  i++;
	      while((x.getValue() < list.get(j).getValue()) && (j > left)) 
	    	  j--;
	      
	      if (i <= j) {
	        y = list.get(i);
	        list.set(i,list.get(j));
	        list.set(j,y);
	        i++; j--;
	      }
	    } while(i <= j);
	    
	    if(left < j) 
	    	qs(list, left, j);
	    if(i < right) 
	    	qs(list, i, right);
	  }

}
