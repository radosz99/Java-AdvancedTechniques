package base;

import pl.advanced.generator.IntGenerator;

import java.util.HashMap;
import java.util.List;

public class SortSet {
    public HashMap<Long, List<IElement>> sortMap= new HashMap<>();

    public List<IElement> getDataBySeed(long seed){
        if (sortMap.containsKey(seed)){
            System.out.println("Ziarno " + seed + " już posortowane!");
            return null;
        }
        else {
            System.out.println("Ziarno " + seed + " jeszcze nieposortowane!");
            return IntGenerator.getIntData(10000, 0, 1000, seed);
        }
    }
}
