package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.ControlActions;
import constant.ConstantPath;

public class LibraryPage extends ControlActions{

	@FindBy(xpath="//button/p[text()='Create Question']")
	private WebElement createQBtn;
	
	@FindBy(xpath = "//button[contains(text(),'Delete (')]")
	private WebElement topdeleteBtn;
	
	@FindBy(xpath = "//button[text()='Delete']")
	private WebElement deleteBtnInPopupElement;
	
	@FindBy(xpath = "//div[@role='presentation' and contains(@style,'opacity: 0')]//button[text()='Delete']")
	private WebElement cancelBtnInPopupElement;
	
	@FindBy(xpath = "//label[text()='Source']")
	private WebElement sourceCheckboxElement;
	
	@FindBy(xpath = "//div[@class='libray-name-header']/parent::span/div[1]")
	private WebElement questionCountHeader;
	
	@FindBy(xpath = "//div[@class='loader']")
	private WebElement spinnerElement;
	
	public LibraryPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void waitForLibraryPageLoaded() {
		waitForElementVisibility(sourceCheckboxElement);
	}
	
	public CreateQuestionPage clickOnCreateQuestionBtn() {
		clickOnElement(createQBtn);
		return new CreateQuestionPage();
	}
	
	public boolean isLeftMenuOptionSelectedByText(String optionText) {
		String locator = "//p[text()='"+optionText+"']//preceding-sibling::div/*[name()='svg']";
		return isElementDisplayed(By.xpath(locator), 5);
	}
	
	public boolean isQuestionDisplayed(String title) {
		String locator = "//label[text()='"+title+"']";
		return isElementDisplayed(By.xpath(locator), 5);
	}
	
	public void selectQuestionByTitle(String title) {
		String locator = "//label[text()='"+title+"']//preceding-sibling::div[2]/*[name()='svg']";
		clickOnElement("XPATH", locator, false);		
	}
	
	public void clickOnTopdeleteButton() {
		clickOnElement(topdeleteBtn);
	}
	
	public void clickDeleteBtnPresentOnPopup() {
		clickOnElement(deleteBtnInPopupElement);
	}
	
	public void clickCancelBtnPresentOnPopup() {
		clickOnElement(cancelBtnInPopupElement);
	}
	
	public void deleteQuestion(String title) {
		selectQuestionByTitle(title);
		clickOnTopdeleteButton();
		clickDeleteBtnPresentOnPopup();
	}
	
	public void selectOptionFromLeftMenu(String leftMenuOptionText) {
		String locator = "//p[text()='"+leftMenuOptionText+"']/preceding-sibling::div";
		boolean flag = isLeftMenuOptionSelectedByText(leftMenuOptionText);
		if(!flag)
			clickOnElement("XPATH", locator, false);
	}
	
	public void deSelectOptionFromLeftMenu(String leftMenuOptionText) {
		String locator = "//p[text()='"+leftMenuOptionText+"']/preceding-sibling::div";
		boolean flag = isLeftMenuOptionSelectedByText(leftMenuOptionText);
		if(flag)
			clickOnElement("XPATH", locator, false);
	}
	
	public void waitUntilSpinnerIsDisappeared() {
		waitUntilElementIsInvisible(spinnerElement, ConstantPath.FASTWAIT);
	}
	
	public int getQuestionCount() {
		String questionCountText = getElementText(questionCountHeader);
		return Integer.parseInt(questionCountText.trim().split("\\(")[1].replace(")",""));
	}
	
	public void selectSkillByText(String skillName) {
		String locator = "//span[@data-tip='"+skillName.toLowerCase()+"']/div";
		clickOnElement("XPATH", locator, false);
	}
	
}
