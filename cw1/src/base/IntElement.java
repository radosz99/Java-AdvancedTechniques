package base;


public class IntElement implements IElement {
	private String word;
	private int value;

	@Override
	public String toString() {
		return value + ", " + word ;
	}

	public IntElement(String word, int value) {
		super();
		this.word = word;
		this.value = value;
	}	

	public String getWord() {
		return this.word;
	}

	public float getValue() {
		return (float) value;
	}
	
	
	
}
