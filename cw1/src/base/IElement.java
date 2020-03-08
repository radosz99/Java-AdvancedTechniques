package base;

/**
 * 
 * Interface which offers some methods - getters - to get data from object of classes which implements the interface
 * 
 * @author Radoslaw Lis
 */

public interface IElement {
	
	/**
	*@return string which is connected with the value
	 */
	public String getWord();
	
	/**
	*@return value of the object
	 */
	public float getValue();
	
}
