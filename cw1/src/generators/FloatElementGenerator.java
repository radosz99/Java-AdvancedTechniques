package generators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import base.*;


/**
 * Class offers some methods generating random data types, such as float or integer.
 * @author Radoslaw Lis
 */
public class FloatElementGenerator {
    /**
     * Generate some random data - FloatElement class objects - by using floatGenerator 
     * and wordGenerator which draws random String from words.txt file that stores all English words in random order.
     * @return List of FloatElement class random generate objects 
     * @param quantity
     *		Quantity of number to generate.
     * @param max
     * 		Defines the range (from 0 to max) in which numbers will be drawn.
    */
	public List<FloatElement> getFloatData (int quantity, int max ){
		List<FloatElement> data = new ArrayList<FloatElement>();
		
		for (int i=0; i<quantity; i++) {
			 data.add(new FloatElement(wordGenerator("words.txt"),floatGenerator(max)));
		}
		 
		return data;
	}
	
    /**
     * Method draws a random float number from a given range.
     * 
     * @return Float number round to four decimal places.
     * @param range
     * 	Defines the range (from 0 to range) in which numbers will be drawn.
     * 		
    */
	public static float floatGenerator(int range) {
		Random generator = new Random();
		float random = generator.nextFloat() * (range);
		
		return round(random,4);
	}
	
	  /**
     * Method round given float number to two decimal places.
     * 
     * @return Float number round to two decimal places.
     * @param d
     * 	Float number to round
     * @param decimalPlace
     * 	Number of decimal places after rounding.
     * 		
    */
	private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        
        return bd.floatValue();
    }
	
    /**
     * Method draws a random integer number from a given range.
     * 
     * @return The drawn number
     * @param range
     * 	Defines the range (from 0 to range) in which numbers will be drawn.
     * 		
    */
	public static int intGenerator(int range) {
		Random generator = new Random();
		
		return generator.nextInt(range);
	}
	
    /**
     * Method draws a word from txt file with all English words. Word is for attributing to the value.
     * <p>
     * First it opens file with by RandomAccessFile class. Then the seek is moved to the drawn index by using intGenerator (from range 0 to 10000).
     * Next we read the line to the end and because there is a chance that we cut the word, we must to read next line. This line is a String that is returned.
     * 
     * @return The drawn word
     * @param filename
     * 	File name of .txt file with words
     * 		
    */
	public static String wordGenerator(String filename) {
		RandomAccessFile raf = null;
		String word = null;
		try {
			raf = new RandomAccessFile(filename, "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 try {
			raf.seek(intGenerator(10000));
			raf.readLine();
			word = raf.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return word;
	}
}
