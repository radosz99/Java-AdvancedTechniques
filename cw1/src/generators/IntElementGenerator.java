package generators;

import java.util.ArrayList;
import java.util.List;
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
	