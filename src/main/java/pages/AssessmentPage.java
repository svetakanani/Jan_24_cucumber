package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.ControlActions;

public class AssessmentPage extends ControlActions{

	@FindBy(xpath = "//span[contains(text(),'published') and contains(@class,'root')]")
	private WebElement publishedElement;
	
	@FindBy(xpath = "//div[@class='settings-list']/div[@class='group'][2]//span[@class='slider round']")
	private WebElement snapShotsToggleElement;
	
	@FindBy(xpath = "//span[text()='Take Snapshots via webcam every 30 second']/*[name()='svg']")
	private WebElement snapShotsToggleSvgElement;
	
	public AssessmentPage() {
		PageFactory.initElements(driver, this);
	}
	
	
	public enum TestStatus{
		PUBLISHED("published"),
		COMPLETED("completed"),
		DRAFT("draft");
		
		private final String value;
		private TestStatus(String statusValue) {
			value = statusValue;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	public int getAssessmentCountBasedOnTestStatus(TestStatus status) {
		String statusLocator = "//span[text()='" + status.getValue() + "']";
		String str = getElementText("XPATH", statusLocator, false);
		int numberOfAssessment = Integer.parseInt((str.split(" ")[1].replace("(", "").replace(")", "")));
		return numberOfAssessment;
	}
	
	public void waitForAssessmentPageToBeLoaded() {
		waitForElementVisibility(publishedElement);
	}
	
	public void clickOnAssessmentByText(String assessmentName) {
		String locator = "//span[text()='"+assessmentName+"']";
		clickOnElement("XPATH", locator, true);
	}
	
	public enum ToggleStatus {
		ON, OFF;	
	}
	
	public void setSnapShotToggle(ToggleStatus status) {
		String attrValue = getElementAttribute(snapShotsToggleSvgElement, "height");
		if(status.toString().equals("ON")) {
			if(attrValue.equals("16"))
				clickOnElement(snapShotsToggleElement);
		}else {
			if(attrValue.equals("17"))
				clickOnElement(snapShotsToggleElement);
		}
	}
	
	public boolean isPopUpDisplayed(String popupText) {
		String locator = "//div[@role='alert']/div[text()='"+popupText+"']";
		boolean flag = isElementDisplayed("XPATH",locator,true);
		if(flag)
			clickOnElement("XPATH", locator, false);
		return flag;
	}
	
	public void clickOnBtnByText(String btnText) {
		String locator = "//span[text()='"+btnText+"']/parent::button";
		clickOnElement("XPATH", locator, true);
	}
	
	public AssessmentPreviewPage switchToExcellioPortalTab() {
		switchToWindowOrTabBasedOnTitle("Excellio Candidate Assesment Portal");
		return new AssessmentPreviewPage();
	}
	
	public AssessmentPreviewPage clickOnPreviewAndSwitchToPreviewPage() {
		clickOnBtnByText("Test Preview");
		return switchToExcellioPortalTab();
	}
	
}
