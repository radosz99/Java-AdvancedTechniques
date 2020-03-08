package base;

import java.util.List;

public abstract class AbstractIntSorter {
		
	public abstract <T extends IElement> List<T> solve(List<T> list);
	public abstract String description();
	public abstract boolean isStable();
	public abstract boolean isInSitu();
}
