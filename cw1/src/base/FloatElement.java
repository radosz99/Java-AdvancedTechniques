package base;


public class FloatElement implements IElement {
	private String word;
	private float value;

	/**
	*@return the string giving all informations about the object - value and name
	 */
	@Override
	public String toString() {
		return value + ", " + word ;
	}

	
	public FloatElement(String word, float value) {
		super();
		this.word = word;
		this.value = value;
	}

	public String getWord() {
		return this.word;
	}

	public float getValue() {
		return this.value;
	}
}
