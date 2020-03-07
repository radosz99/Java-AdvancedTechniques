package generators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import base.*;

public class IntElementGenerator {
	@SuppressWarnings({ "resource", "null" })
	public List<IntElement> getData (int quantity, int max ){
		List<IntElement> data = new ArrayList<IntElement>();
		String word = null;
		int number;
		
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile("words.txt", "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (int i=0; i<quantity; i++) {
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
			 
			 data.add(new IntElement(word,intGenerator(max)));
		}
		 
		return data;
	}
	
	public static int intGenerator(int range) {
		Random generator = new Random();
		
		return generator.nextInt(range);
	}
}
	