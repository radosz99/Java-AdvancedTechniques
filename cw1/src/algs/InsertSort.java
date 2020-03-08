package algs;

import java.util.List;

import base.*;

public class InsertSort extends AbstractFloatSorter{
	
	String name = "Insert Sort";
	boolean stable = true;
	boolean inSitu = true;
	
	@Override
	public List<FloatElement> solve2(List<FloatElement> list) {

		FloatElement key;
	    for (int i = 1; i < list.size(); ++i) { 
	        key = list.get(i); 
	        int j = i - 1; 

	        while (j >= 0 && list.get(j).getValue() > key.getValue()) { 
	            list.set(j + 1,list.get(j)); 
	            j = j - 1; 
	        } 
	        list.set(j + 1,key); 
	    } 
    
	return list;
	}
	
}
