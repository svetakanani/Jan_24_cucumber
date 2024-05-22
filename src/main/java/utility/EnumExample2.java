package utility;

public class EnumExample2 {

	static enum Menu{
		DASHBOARD("Dashboard"),
		ASSESSMENTS("Assessments"),
		LIBRARY("Library");
		
		private final String menuStr;
		
		private Menu(String menuStr) {
			this.menuStr = menuStr;
		}
		
		public String getValue(){
			return menuStr;
		}
	}
	
	public static void main(String[] args) {
		Menu selectMenu = Menu.DASHBOARD;
		System.out.println(selectMenu.getValue());
	}
	
	
}
