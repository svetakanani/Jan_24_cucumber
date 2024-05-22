package utility;

public class EnumExample {

	enum Day {
		MONDAY,
		TUESDAY,
		SUNDAY
	}
	
	
	void selectDay(Day day) {
		System.out.println(day);
	}
	
	void m1() {
		selectDay(Day.TUESDAY);
	}
	
	public static void main(String[] args) {
		new EnumExample().m1();
	}
}
