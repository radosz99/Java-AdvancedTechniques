package base;


public class IntElement implements IElement {
	String name;
	int value;

	@Override
	public String toString() {
		return value + ", " + name ;
	}

	public IntElement(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}	

	public String getName() {
		return this.name;
	}

	public float getValue() {
		return (float) value;
	}
	
	
	
}
