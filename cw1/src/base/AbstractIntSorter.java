package base;

import java.util.List;

public abstract class AbstractIntSorter {
	
	String name;
	boolean stable;
	boolean inSitu;
	
	
	public List<IntElement> solve(List<IntElement> list){
		return null;
	}
	
	public String description() {
		return this.name;
	}
	public boolean isStable() {
		return this.stable;
		
	}
	public boolean isInSitu() {
		return this.inSitu;
		
	}
}
