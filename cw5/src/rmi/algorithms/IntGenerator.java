package rmi.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntGenerator {
    public static List<IElement> getIntData(int quantity, int min, int max){
        List<IElement> data = new ArrayList<IElement>();
        Random generator = new Random();
        for (int i=0; i<quantity; i++) {
            int value = generator.nextInt(max-min+1)+min;
            //data.add(new base.IntElement(wordGenerator("C:\\Users\\Radek\\Desktop\\6semestr\\Java_Techniki_Zaawansowane\\cw3\\src\\main\\resources\\words.txt", valForWord),value));
            data.add(new IntElement(getRandomString(6),value));
        }

        return data;
    }
    private static String getRandomString(int size) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = size;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }
}
