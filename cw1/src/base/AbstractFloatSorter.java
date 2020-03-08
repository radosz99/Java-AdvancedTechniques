package base;

import java.util.List;


public abstract class AbstractFloatSorter extends AbstractIntSorter {
	
	@Override
	public abstract <T extends IElement> List<T> solve(List<T> list);

}
