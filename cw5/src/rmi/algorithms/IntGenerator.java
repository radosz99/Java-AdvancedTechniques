package rmi.algorithms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntGenerator {
    public static List<IElement> getIntData(int quantity, int min, int max, String dataType){
        List<IElement> data = new ArrayList<IElement>();
        Random generator = new Random();
        for (int i=0; i<quantity; i++) {
            if(dataType.equals("integer")) {
                int value = generator.nextInt(max - min + 1) + min;
                data.add(new IntElement(getRandomString(6), value));
            }
            else if(dataType.equals("float")){
                float value = generator.nextFloat()*(max - min + 1) + min;
                data.add(new FloatElement(getRandomString(6), round(value,3)));
            }
        }

        return data;
    }

    @SuppressWarnings("deprecation")
    private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

        return bd.floatValue();
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
