package base;


public class FloatElement implements IElement {
	String name;
	float value;

	public FloatElement(String name, float value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public float getValue() {
		return this.value;
	}
}
