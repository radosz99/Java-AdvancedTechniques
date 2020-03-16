package generators;

import java.util.ArrayList;
import java.util.List;
import base.*;

/**
 * Class offers some methods generating random data types, such as float or integer, inheriting from FloatElementGenerator.
 * @author Radoslaw Lis
 */
public class IntElementGenerator extends FloatElementGenerator {
    /**
     * Generates some random data - IntElement class objects - by using intGenerator 
     * and wordGenerator which draws random String from words.txt file that stores all English words in random order.
     * @return List of IntElement class random generate objects.
     * @param quantity
     *		Quantity of number to generate.
     * @param max
     * 		Defines the range (from 0 to max) in which numbers will be drawn.
    */
	public List<IntElement> getIntData (int quantity, int max ){
		List<IntElement> data = new ArrayList<IntElement>();
		
		for (int i=0; i<quantity; i++) {
			 data.add(new IntElement(wordGenerator("words.txt"),intGenerator(max)));
		}
		 
		return data;
	}	
}
	