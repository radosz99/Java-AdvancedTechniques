package application;

public class FileFormatException extends Exception {
	public FileFormatException() {}
	public FileFormatException(String gripe) {
		super(gripe);
	}
}