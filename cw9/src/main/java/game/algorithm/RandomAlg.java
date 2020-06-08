package game.algorithm;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class RandomAlg implements Algorithm {
    public ArrayList<Integer> execute(List<List<Integer>> tilesValue) {
        SecureRandom random = new SecureRandom();
        int randX = 0, randY = 0;
        while (true) {
            boolean founded = false;
            for (List<Integer> lis : tilesValue) {
                if (lis.contains(0)) {
                    founded = true;
                    break;
                }
            }
            if (!founded) {
                randX = Integer.MAX_VALUE;
                randY = Integer.MAX_VALUE;
                break;
            }

            randX = random.nextInt(5);
            randY = random.nextInt(5);
            if (tilesValue.get(randX).get(randY) == 0) {
                break;
            }
        }
        ArrayList<Integer> result = new ArrayList<>(2);
        result.add(randX);
        result.add(randY);
        return result;
    }
}
