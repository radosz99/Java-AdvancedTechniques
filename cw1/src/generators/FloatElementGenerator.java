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
	String path;
	
    public FloatElementGenerator(String path) {
		super();
		this.path = path;
	}

	/**
     * Generate some random data - FloatElement class objects - by using floatGenerator 
     * and wordGenerator which draws random String from words.txt file that stores all English words in random order.
     * @return List of FloatElement class random generate objects 
     * @param quantity
     *		Quantity of number to generate.
     * @param max
     * 		Defines the range (from 0 to max) in which numbers will be drawn.
    */
	public List<IElement> getFloatData (int quantity, int min, int max ){
		List<IElement> data = new ArrayList<IElement>();
		
		for (int i=0; i<quantity; i++) {
			 data.add(new FloatElement(wordGenerator(path+"words.txt"),floatGenerator(min,max)));
		}
		 
		return data;
	}
	
    /**
     * Method draws a random float number from a given range.
     * 
     * @return Float number round to four decimal places.
     * @param min
     * @param max
     * 	Defines the range (from min to max) in which numbers will be drawn.
     * 		
    */
	public static float floatGenerator(int min, int max) {
		Random generator = new Random();
		float random = generator.nextFloat() * (max-min)+min;
		
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
	@SuppressWarnings("deprecation")
	private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        
        return bd.floatValue();
    }
	
    /**
     * Method draws a random integer number from a given range.
     * 
     * @return The drawn number
     * @param min
     * @param max
     * 	Defines the range (from minrange to maxrange) in which numbers will be drawn.
     * 		
    */
	public static int intGenerator(int min, int max) {
		Random generator = new Random();
		
		return generator.nextInt(max-min+1)+min;
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
			raf.seek(intGenerator(0,100000));
			raf.readLine();
			word = raf.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return word;
	}
}
