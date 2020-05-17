package cipher;

import base.FloatElement;
import base.IElement;
import base.IntElement;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    public static float floatGenerator(int min, int max) {
        Random generator = new Random();
        float random = generator.nextFloat() * (max-min)+min;

        return round(random,4);
    }

    private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

        return bd.floatValue();
    }

    public static List<IElement> getFloatData (int quantity, int min, int max ){
        List<IElement> data = new ArrayList<>();

        for (int i=0; i<quantity; i++) {
            data.add(new FloatElement(WordGenerator.getRandomString(6),floatGenerator(min,max)));
        }

        return data;
    }

    public static List<IElement> getIntData (int quantity, int min, int max ){
        List<IElement> data = new ArrayList<>();

        for (int i=0; i<quantity; i++) {
            data.add(new IntElement(WordGenerator.getRandomString(6),intGenerator(min,max)));
        }

        return data;
    }

    public static int intGenerator(int min, int max) {
        Random generator = new Random();

        return generator.nextInt(max-min+1)+min;
    }
}
