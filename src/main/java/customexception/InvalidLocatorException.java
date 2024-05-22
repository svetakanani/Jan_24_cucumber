package customexception;

@SuppressWarnings("serial")
public class InvalidLocatorException extends RuntimeException{

	public InvalidLocatorException() {
		super("Locator is not supported.");
	}
	
	public InvalidLocatorException(String msg) {
		super(msg);
	}
}
