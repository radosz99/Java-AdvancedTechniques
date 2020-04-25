package rmi.algorithms;

import java.io.Serializable;

/**
 *
 * Class which implements IElement interface. Object of this class are needed in arrays to sort.
 *
 * @author Radoslaw Lis
 */
public class FloatElement implements IElement, Serializable {
    /**
     * Private variable of String type assigned to the value.
     *  key by which list is sorted.
     */
    String word;

    /**
     *  Private variable of float type the key by which list is sorted.
     */
    float value;

    /**
     *@return The string giving all informations about the object value and name to show the list of objects in easy way.
     */
    @Override
    public String toString() {
        return value + ", " + word ;
    }

    /**
     *Constructor based on word and value fields. Used when the list of objects is creating.
     */
    public FloatElement(String word, float value) {
        super();
        this.word = word;
        this.value = value;
    }

    /**
     *@return String which is assigned to the value.
     */
    public String getWord(){
        return word;
    }

    /**
     *@return Value of the object the key by which list is sorted.
     */
    public float getValue() {
        return value;
    }
}
