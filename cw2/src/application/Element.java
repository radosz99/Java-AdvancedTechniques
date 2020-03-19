package application;

public class Element {
	private String value;
	private String word;
	@Override
	public String toString() {
		return "Element [value=" + value + ", word=" + word + "]";
	}
	public Element(String value, String word) {
		super();
		this.value = value;
		this.word = word;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
}
