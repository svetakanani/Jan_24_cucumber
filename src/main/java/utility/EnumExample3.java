package utility;

public class EnumExample3 {

	enum Status{
		PUBLISHED("published"),
		COMPLETED("Completed"),
		DRAFT("Draft");
		
		private final String value;
		private Status(String statusValue) {
			value = statusValue;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	public static void main(String[] args) {
		Status temp = Status.COMPLETED;
		System.out.println(temp.getValue());
	}
	
	
}
