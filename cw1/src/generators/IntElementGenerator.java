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

public class IntElementGenerator extends FloatElementGenerator {

	public List<IntElement> getIntData (int quantity, int max ){
		List<IntElement> data = new ArrayList<IntElement>();
		
		for (int i=0; i<quantity; i++) {
			 data.add(new IntElement(wordGenerator(),intGenerator(max)));
		}
		 
		return data;
	}	
}
	