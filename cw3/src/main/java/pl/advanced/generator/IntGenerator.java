package pl.advanced.generator;

import base.IElement;
import base.IntElement;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IntGenerator {
    public static List<IElement> getIntData(int quantity, int min, int max, long seed){
        List<base.IElement> data = new ArrayList<IElement>();
        Random generator = new Random();
        generator.setSeed(seed);
        for (int i=0; i<quantity; i++) {
            int value = generator.nextInt(max-min+1)+min;
            int valForWord = generator.nextInt(10000);
            //data.add(new base.IntElement(wordGenerator("C:\\Users\\Radek\\Desktop\\6semestr\\Java_Techniki_Zaawansowane\\cw3\\src\\main\\resources\\words.txt", valForWord),value));
            data.add(new IntElement(getRandomString(6),value));
        }

        return data;
    }

    public static String wordGenerator(String filename, int val) {
        RandomAccessFile raf = null;
        String word = null;
        try {
            raf = new RandomAccessFile(filename, "r");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            raf.seek(val);
            raf.readLine();
            word = raf.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return word;
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
