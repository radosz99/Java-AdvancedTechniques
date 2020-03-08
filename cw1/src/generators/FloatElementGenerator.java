package generators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import base.*;


/**
 * Class offers some methods generating random data types, such as float or int
 * @author Radoslaw Lis
 */
public class FloatElementGenerator {
    /**
     * Generate some random data - FloatElement class objects - by using floatGenerator 
     * and wordGenerator which draws random String from words.txt file that stores all English words in random order
     *  @return List of FloatElement class random generate objects 
     * @param quantity
     *		Quantity of number to generate
     * @param max
     * 		Defines the range (from 0 to max) in which numbers will be drawn
    */
	public List<FloatElement> getFloatData (int quantity, int max ){
		List<FloatElement> data = new ArrayList<FloatElement>();
		
		for (int i=0; i<quantity; i++) {
			 data.add(new FloatElement(wordGenerator("words.txt"),floatGenerator(max)));
		}
		 
		return data;
	}
	
	public static float floatGenerator(int range) {
		Random generator = new Random();
		float random = generator.nextFloat() * (range);
		
		return random;
	}
	
	public static int intGenerator(int range) {
		Random generator = new Random();
		
		return generator.nextInt(range);
	}
	
	public static String wordGenerator(String filename) {
		RandomAccessFile raf = null;
		
		try {
			raf = new RandomAccessFile(filename, "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String word = null;
		
		 try {
			raf.seek(intGenerator(10000));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 try {
			raf.readLine();
			word = raf.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return word;
	}
}
