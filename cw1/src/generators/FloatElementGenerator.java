package generators;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import base.*;

public class FloatElementGenerator {
	
	public List<FloatElement> getFloatData (int quantity, int max ){
		List<FloatElement> data = new ArrayList<FloatElement>();
		
		for (int i=0; i<quantity; i++) {
			 data.add(new FloatElement(wordGenerator(),floatGenerator(max)));
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
	
	public static String wordGenerator() {
		
		RandomAccessFile raf = null;
		
		try {
			raf = new RandomAccessFile("words.txt", "r");
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
