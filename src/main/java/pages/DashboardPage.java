package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.ControlActions;

public class DashboardPage extends ControlActions {

	@FindBy(xpath = "//span[text()='Dashboard']")
	WebElement dashboardMenuElement;
	
	@FindBy(xpath = "//button[@class='createAssementButton']")
	WebElement createAssessmentBtn;
	
	@FindBy(xpath = "//button[@class='createQuestionButton']")
	WebElement createQuestionBtn;
	
	public DashboardPage() {
		PageFactory.initElements(driver, this);
	}
	
	public enum Menu {
		ASSESSMENT("Assessments"),
		DASHBOARD("Dashboard"),
		LIBRARY("Library"),
		CANDIDATES("Candidates"),
		INTERVIEWS("Interviews");

		private final String value;

		private Menu(String statusValue) {
			value = statusValue;
		}

		public String getValue() {
			return value;
		}
	}
	
	public boolean isDashboardMenuVisible() {
		return isElementDisplayed(dashboardMenuElement, true);
	}
	
	public void waitForDashboardPageToBeLoaded() {
		waitForElementVisibility(dashboardMenuElement);
	}
	
	public boolean isCardDisplayed(String cardName) {
		String cardLocator = "//span[text()='"+cardName+"']";
		return isElementDisplayed("XPATH", cardLocator, true);
	}
	
	public int getValueFromCards(String cardName) {
		String cardLocator = "//span[text()='" + cardName + "']/b";
		String text = getElementText("XPATH", cardLocator, true);
		return Integer.parseInt(text);
	}
	
	public boolean isMenuVisible(Menu menu) {
		String menuLocator = "//span[text()='"+menu.getValue()+"']";
		return isElementDisplayed("XPATH", menuLocator, true);
	}
	
	public boolean isCreateAssessmentBtnClickable() {
		return isElementClickable(createAssessmentBtn);
	}
	
	public boolean isCreateQuestionBtnClickable() {
		return isElementClickable(createQuestionBtn);
	}
	
	public void navigate_To(Menu menu) {
		String menuLocator = "//span[text()='"+menu.getValue()+"']";
		clickOnElement("XPATH", menuLocator, true);
	}
	
	public int getAssessmentCount() {
		return getValueFromCards("Total Assessments");
	}
}
