package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.ControlActions;

public class AssessmentPreviewPage extends ControlActions{
	
	@FindBy(xpath = "//span[contains(text(),'Terms of Use')]/preceding-sibling::input")
	private WebElement termsOfUseCheckBoxElement;
	
	@FindBy(xpath = "//span[text()='Start Test']/parent::button")
	private WebElement startTestBtn;
	
	@FindBy(xpath="//span[@class='message' and contains(text(),\"Can't start test, Please turn on camera\")]")
	private WebElement cameraErrorPopUpElement;
	
	@FindBy(xpath="//span[@class='message']")
	private WebElement cameraErrorMessageElement;
	
	@FindBy(xpath="//span[text()='Close']")
	private WebElement closeCameraPopupBtn;
	
	public AssessmentPreviewPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void selectTermsOfUse() {
		clickOnElement(termsOfUseCheckBoxElement);
	}
	
	public AssessmentPage switchToAssessmentPlatform() {
		switchToWindowOrTabBasedOnTitle("EliteQA - #1 Assessment Platform");
		return new AssessmentPage();
	}
	
	public void clickOnStartTestBtn() {
		clickOnElement(startTestBtn);
	}
	
	public boolean isCameraErrorPopupDisplayed() {
		try {
			return isElementDisplayed(cameraErrorPopUpElement, true);			
		}catch(NoSuchElementException | TimeoutException e) {
			return false;
		}
	}
	
	public String getCameraErrorPopupMessage() {
		return getElementText(cameraErrorMessageElement);
	}
	
	public void clickOnCloseBtn() {
		clickOnElement(closeCameraPopupBtn);
	}
	
}
