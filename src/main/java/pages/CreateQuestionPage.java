package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import base.ControlActions;

public class CreateQuestionPage extends ControlActions{

	@FindBy(xpath = "//div[@class='mcq-svg-container']")
	private WebElement mcqBtn;
	
	@FindBy(xpath = "//label[text()='Save']/parent::button")
	private WebElement saveBtn;
	
	@FindBy(xpath = "//input[@placeholder='Your question title']")
	private WebElement questionTitleInputElement;
	
	@FindBy(xpath = "//div[@class='Toastify__toast-body']")
	private WebElement toastMessageElement;
	
	@FindBy(xpath = "//input[@placeholder='Score']")
	private WebElement scoreInputElement;
	
	@FindBy(xpath = "//div[@class='add-problem-input-container']//div[contains(@class,'se-wrapper-inner')]/p")
	private WebElement enterQuestionInputElement;
	
	@FindBy(xpath = "(//div[@class='se-wrapper'])[1]")
	private WebElement enterQuestionTextElement;
	
	@FindBy(xpath = "//div[@tabindex='1']//div[contains(@class,'editor-editable')]/p")
	private WebElement questionFirstChoice;
	
	@FindBy(xpath = "//div[@tabindex='2']//div[contains(@class,'editor-editable')]/p")
	private WebElement questionSecondChoice;
	
	@FindBy(xpath = "//input[@placeholder='Search skill here...']")
	private WebElement searchSKillInputElement;
	
	public CreateQuestionPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnMCQ() {
		clickOnElement(mcqBtn);
	}
	
	public void clickOnSave() {
		clickOnElement(saveBtn);
	}
	
	public void enterQuestionTitle(String questionTitle) {
		setText(questionTitleInputElement, questionTitle);
	}
	
	public boolean isPopUpDisplayed(String message) {
		String locator = "//div[@class='Toastify__toast-body']/div[text()='"+message+"']";
		boolean flag = isElementDisplayed("XPATH", locator, true);
		clickOnElement("XPATH", locator, false);
		return flag;
	}
	
	public void enterScore(int scoreValue) {
		setText(scoreInputElement,String.valueOf(scoreValue));
	}
	
	public void enterScore(String scoreValue) {
		setText(scoreInputElement,scoreValue);
	}
	
	public void enterQuestion(String question) {
		setText(enterQuestionInputElement, question);
	}
	
	public String getQuestionText() {
		return getElementText(enterQuestionTextElement);
	}
	
	public void firstChoice(String choice){
		setText(questionFirstChoice,choice); 	
	}
	
	public void secondChoice(String choice){
		setText(questionSecondChoice,choice); 	
	}
	
	public enum Difficulty{
		EASY("Easy"),
		MEDIUM("Medium"),
		HARD("Hard");
		
		private String name;
		private Difficulty(String str) {
			name = str;
		}
		
		public String getValue() {
			return name;
		}
	}
	
	public void setDifficulty(Difficulty difficultyLevel) {
		String locator = "//button[text()='"+difficultyLevel.getValue()+"']";
		clickOnElement("XPATH", locator, true);
	}
	
	public void selectSkillFromSearchBox(String skill) {
		setText(searchSKillInputElement, skill);
		String locator = "//span[text()='"+skill+"']";
		clickOnElement("XPATH", locator, true);
	}
	
	public void selectTopic(String topic) {
		String locator = "//div[@id='add-topic']/p[text()='"+topic+"']";
		clickOnElement("XPATH", locator, true);
	}
	
	public void selectCorrectChoice(int choiceIndex) {
		String locator = "//div[@class='answer-box']["+choiceIndex+"]/div[@class='check-box']";
		clickOnElement("XPATH", locator, false);
	}
	
	public boolean closeToastPopupIfPresent(String toastMessage) {
		String locator = "//div[@class='Toastify__toast-body']/div[text()='"+toastMessage+"']";
		boolean flag = waitForElementVisibility(By.xpath(locator),2);
		if(flag)
			clickOnElement("XPATH", locator, false);
		return flag;
	}
}
