package base;

import pl.advanced.Main;
import pl.advanced.generator.IntGenerator;

import java.util.*;

import org.apache.commons.collections4.map.ReferenceMap;
import org.apache.commons.collections4.map.AbstractReferenceMap;

public class SortSet {
    public SortSet(String keyType, String valueType, String quantity){
        AbstractReferenceMap.ReferenceStrength key;
        AbstractReferenceMap.ReferenceStrength value;
        key = setReference(keyType);
        value = setReference(valueType);
        elementsQuantity = Integer.parseInt(quantity);
        sortMap = Collections.synchronizedMap(new ReferenceMap<>(key,value));
    }

    private static int elementsQuantity = 0;

    public Map<Long, List<IElement>> sortMap;


    public List<IElement> getDataBySeed(long seed) {
        synchronized(sortMap) {
            if (sortMap.containsKey(seed)) {
                return null;
            } else {
                return IntGenerator.getIntData(elementsQuantity, 0, 1000, seed);
            }
        }
    }

    private AbstractReferenceMap.ReferenceStrength setReference(String id){
        switch(id) {
            case "HARD":
                return AbstractReferenceMap.ReferenceStrength.HARD;
            case "WEAK":
                return AbstractReferenceMap.ReferenceStrength.WEAK;
            case "SOFT":
                return AbstractReferenceMap.ReferenceStrength.SOFT;
            default:
                Main.safePrintln("Wpisz odpowiednie wartości referencji (HARD,WEAK,SOFT)");
                return null;
        }

    }
}
