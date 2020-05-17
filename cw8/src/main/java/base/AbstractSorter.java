package base;

import java.util.List;

/**
 * Class which holds all universal methods which are needed for each inheriting class with specific algorithm.
 * @author Radoslaw Lis
 */
public abstract class AbstractSorter {
    /**
     * Unimplemented abstract method to sort list of T objects.
     * <p>
     * @param list
     * 	List of objects T (objects from classes implementing IElement interface) to sort.
     * @return The finally sorted list.
    */
	public abstract <T extends IElement> List<T> solve(List<T> list);
	
    /**
     * Unimplemented abstract method to get description of the algorithm.
     * <p>
     * @return Algorithm's description name  how and it works.
    */
	public abstract String description();
	
    /**
     *  Unimplemented abstract method to get information whether algorithm is stable
     * <p>
     *  @return Logical value which gives information whether algorithm is stable. In other words it means whether
     *  two objects with the same values appear in the same order in output as they appear in input.
    */
	public abstract boolean isStable();
	
    /**
     * 
     * Unimplemented abstract method to get information whether algorithm works in situ
     * <p>
     *  @return Logical value which gives information whether algorithm works in situ. In other words it means whether
     *  algorithm sorts the items without using additional temporary space to hold data (if yes it works in situ).
    */
	public abstract boolean isInSitu();
}
