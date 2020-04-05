package base;

import pl.advanced.generator.IntGenerator;

import java.util.*;

import org.apache.commons.collections4.map.ReferenceMap;
import org.apache.commons.collections4.map.AbstractReferenceMap;

public class SortSet {
    public Map<Long, List<IElement>> sortMap = Collections.synchronizedMap(new ReferenceMap<Long, List<IElement>>
            (AbstractReferenceMap.ReferenceStrength.HARD, AbstractReferenceMap.ReferenceStrength.HARD));

    public List<IElement> getDataBySeed(long seed) {
        if (sortMap.containsKey(seed)) {
            return null;
        } else {
            return IntGenerator.getIntData(7500, 0, 1000, seed);
        }
    }
}
