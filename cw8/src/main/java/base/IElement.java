package base;

/**
 * 
 * Interface which offers some methods getters to get data from object of classes which implements the interface.
 * 
 * @author Radoslaw Lis
 */

public interface IElement {
    /**
     * Variable of String type assigned to the value.
     *  key by which list is sorted.
     */
	public static final String word = "";
	
    /**
     * Variable of float type the key by which list is sorted.
     */
	public static final float value = 0;
	
	/**
	*@return String which is assigned to the value.
	 */
	public default String getWord(){
		return word;
	}
	
	/**
	*@return Value of the object the key by which list is sorted.
	 */
	public default float getValue() {
		return (float) value;
	}	
}
