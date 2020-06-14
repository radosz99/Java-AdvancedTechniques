package pl.advanced;

import base.IElement;
import base.IntElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntGenerator {
    public static List<IElement> getIntData(int quantity, int min, int max, long seed){
        List<base.IElement> data = new ArrayList<IElement>();
        Random generator = new Random();
        generator.setSeed(seed);
        for (int i=0; i<quantity; i++) {
            int value = generator.nextInt(max - min + 1) + min;
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
