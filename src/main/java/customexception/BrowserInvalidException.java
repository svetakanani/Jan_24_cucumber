package customexception;

@SuppressWarnings("serial")
public class BrowserInvalidException extends RuntimeException{
	
	public BrowserInvalidException() {
		super("Browser is unsupported, please selcted browser");
	}
	
	public BrowserInvalidException(String msg) {
		super(msg);
	}
	
}
