package base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import constant.ConstantPath;
import customexception.BrowserInvalidException;
import customexception.InvalidLocatorException;
import utility.PropOperation;
import utility.TimeStamp;

abstract public class ControlActions {

	protected static WebDriver driver;
	protected static WebDriverWait wait;
	protected static Actions action;

	public static void launchBrowser() {
		System.out.println("STEP - Launch Browser");
		String browser = System.getProperty("BrowserType");
		PropOperation propOperation = new PropOperation(ConstantPath.CONFIG_FILEPATH);
		if(browser == null || browser.isEmpty()) {
			browser = propOperation.getValue("browser");
		}
		System.out.println("###############BROWSER###########" + browser);
		if (browser.equalsIgnoreCase("chrome"))
			driver = new ChromeDriver();
		else if (browser.equalsIgnoreCase("firefox"))
			driver = new FirefoxDriver();
		else if (browser.equalsIgnoreCase("IE"))
			driver = new InternetExplorerDriver();
		else
			throw new BrowserInvalidException(
					browser + " is not supported, only supported browsers are chrome, firefox and IE");

		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(ConstantPath.WAIT));
		action = new Actions(driver);

		System.out.println("STEP - load URL");
		
		String url = propOperation.getValue("url");
		driver.get(url);
	}
	
	public static void launchBrowser(String browserType) {
		System.out.println("STEP - Launch Browser");
		String browser = System.getProperty("BrowserType");
		if(browser.isEmpty()) {
			browser = browserType;
		}
		PropOperation propOperation = null;
		if(browser.isEmpty()) {
			propOperation = new PropOperation(ConstantPath.CONFIG_FILEPATH);
			browser = propOperation.getValue("browser");
		}
		
		
		System.out.println("###############BROWSER###########" + browser);
		if (browser.equalsIgnoreCase("chrome"))
			driver = new ChromeDriver();
		else if (browser.equalsIgnoreCase("firefox"))
			driver = new FirefoxDriver();
		else if (browser.equalsIgnoreCase("IE"))
			driver = new InternetExplorerDriver();
		else
			throw new BrowserInvalidException(
					browser + " is not supported, only supported browsers are chrome, firefox and IE");

		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(ConstantPath.WAIT));
		action = new Actions(driver);

		System.out.println("STEP - load URL");
		
		String url = propOperation.getValue("url");
		driver.get(url);
	}
	
	protected WebElement smartWaitOnVisibilityOfElement(By by, int pollingTime) {
		WebElement targetElement = new FluentWait<WebDriver>(driver)
			       .withTimeout(Duration.ofSeconds(ConstantPath.WAIT))
			       .pollingEvery(Duration.ofMillis(pollingTime))
			       .ignoring(NoSuchElementException.class)
		 		   .until(ExpectedConditions.visibilityOfElementLocated(by));
		return targetElement;
	}
	
	protected WebElement getElement(By by, boolean isWaitRequired) {
		if (isWaitRequired)
			return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		else
			return driver.findElement(by);
	}

	/*
	 * void m1() { getElement(By.xpath("//table[@id='id1']"),true);
	 * getElement("XPATH","//table[@id='id1']",true); }
	 */

	protected WebElement getElement(String locatorType, String locatorValue, boolean isWaitRequired) {
		return getElement(locatorType, locatorValue, isWaitRequired, 30);
	}

	protected WebElement getElement(String locatorType, String locatorValue, boolean isWaitRequired, int timeout) {
		WebElement element = null;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));

		switch (locatorType.toUpperCase()) {
		case "ID":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
			else
				element = driver.findElement(By.id(locatorValue));
			break;

		case "NAME":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			else
				element = driver.findElement(By.name(locatorValue));
			break;

		case "XPATH":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
			else
				element = driver.findElement(By.xpath(locatorValue));
			break;

		case "CSS":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorValue)));
			else
				element = driver.findElement(By.cssSelector(locatorValue));
			break;

		case "LINKTEXT":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorValue)));
			else
				element = driver.findElement(By.linkText(locatorValue));
			break;

		case "PARTIALLINKTEXT":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorValue)));
			else
				element = driver.findElement(By.partialLinkText(locatorValue));
			break;

		case "CLASSNAME":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorValue)));
			else
				element = driver.findElement(By.className(locatorValue));
			break;

		case "TAGNAME":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorValue)));
			else
				element = driver.findElement(By.tagName(locatorValue));
			break;

		default:
			throw new InvalidLocatorException(locatorType + " is not supported");
		}
		return element;
	}

	protected void clickOnElement(String locatorType, String locatorValue, boolean isWaitRequired) {
		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		clickOnElement(e);
	}

	protected void clickOnElement(WebElement e) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(e));
			e.click();
		} catch (ElementClickInterceptedException | TimeoutException ec) {
			scrollToElement(e);
			e.click();
		}
	}
	
	
	protected void jsClickOnElement(WebElement element) {
		//WebElement element = driver.findElement(By.xpath("//button[@id='btn1']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].click();",element);
	}
	

	protected void scrollToElement(WebElement e) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView(true);", e);
	}

	protected String getElementText(String locatorType, String locatorValue, boolean isWaitRequired) {
		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		return getElementText(e);
	}

	protected String getElementText(WebElement e) {
		try {
			return e.getText();
		} catch (Exception exception) {
			scrollToElement(e);
			return e.getText();
		}
	}

	protected String getInputElementText(WebElement e) {
		try {
			return e.getAttribute("value");
		} catch (Exception exception) {
			scrollToElement(e);
			return e.getAttribute("value");
		}
	}

	protected void setText(String locatorType, String locatorValue, boolean isWaitRequired, String textToBeSet) {
		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		setText(e, textToBeSet);
	}

	protected void setText(WebElement e, String textToBeSet) {
		try {
			e.sendKeys(textToBeSet);
		} catch (Exception exception) {
			wait.until(ExpectedConditions.visibilityOf(e));
			scrollToElement(e);
			e.sendKeys(textToBeSet);
		}
	}

	protected String getElementAttribute(String locatorType, String locatorValue, boolean isWaitRequired,
			String attribute) {
		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		return e.getAttribute(attribute);
	}

	protected String getElementAttribute(WebElement e, String attribute) {
		return e.getAttribute(attribute);
	}

	protected void mouseHoverToElement(String locatorType, String locatorValue, boolean isWaitRequired) {
		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		action.moveToElement(e).build().perform();
	}

	protected boolean isElementDisplayed(String locatorType, String locatorValue, boolean isWaitRequired) {
		try {
			WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
			return e.isDisplayed();
		} catch (Exception ne) {
			return false;
		}
	}
	
	protected boolean isElementDisplayed(By by, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(timeout));
			WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return e.isDisplayed();
		} catch (Exception ne) {
			return false;
		}
	}

	protected boolean isElementDisplayed(WebElement e, boolean isWaitRequired) {
		if (isWaitRequired)
			e = wait.until(ExpectedConditions.visibilityOf(e));
		return e.isDisplayed();
	}

	protected void waitForElementVisibility(WebElement e, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.visibilityOf(e));
	}
	
	protected boolean waitForElementVisibility(By by, int timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			return true;
		}catch(Exception e) {
			return false;
		}
	}

	protected void waitForElementVisibility(WebElement e) {
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	protected boolean isElementNotDisplayed(String locatorType, String locatorValue, boolean isWaitRequired) {
		try {
			WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
			return !e.isDisplayed();
		} catch (Exception ne) {
			return true;
		}
	}

	protected void selectOptionByVisibleText(WebElement e, String visibleText) {
		Select oselect = new Select(e);
		oselect.selectByVisibleText(visibleText);
	}

	protected void selectOptionByValue(WebElement e, String value) {
		Select oselect = new Select(e);
		oselect.selectByValue(value);
	}

	protected void selectOptionByIndex(WebElement e, int index) {
		Select oselect = new Select(e);
		oselect.selectByIndex(index);
	}

	private Alert switchToAlert() {
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		return alert;
	}

	protected void acceptJavaScriptBasedAlert() {
		Alert alert = switchToAlert();
		alert.accept();
	}

	protected void declineJavaScriptBasedAlert() {
		Alert alert = switchToAlert();
		alert.dismiss();
	}

	protected String getTextFromAlert() {
		Alert alert = switchToAlert();
		String alertText = alert.getText();
		alert.accept();
		return alertText;
	}

	protected void setTextIntoJavaScriptBasedPrompt(String alertText) {
		Alert alert = switchToAlert();
		alert.sendKeys(alertText);
	}

	protected boolean waitUntilElementIsInvisible(String locatorType, String locatorValue, boolean isWaitRequired) {
		try {
			WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
			return wait.until(ExpectedConditions.invisibilityOf(e));
		} catch (Exception e) {
			return true;
		}
	}

	protected boolean waitUntilElementIsInvisible(String locatorType, String locatorValue, boolean isWaitRequired,
			int timeOutInSec) {
		try {
			WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSec));
			return wait.until(ExpectedConditions.invisibilityOf(e));
		} catch (Exception exception) {
			return true;
		}
	}
	
	protected boolean waitUntilElementIsInvisible(WebElement e, int timeOutInSec) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSec));
			return wait.until(ExpectedConditions.invisibilityOf(e));
		} catch (Exception exception) {
			return true;
		}
	}

	protected void switchToFrame(WebElement e) {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(e));
	}

	protected void switchToFrame(String idOrName) {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}

	protected void switchToFrame(int index) {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(index));
	}

	protected void switchToWindowOrTabBasedOnTitle(String title) {
		Set<String> allWindowHandle = driver.getWindowHandles();
		for (String currentWindowHandle : allWindowHandle) {
			driver.switchTo().window(currentWindowHandle);
			String currentWindowTitle = getCurrentPageTitle();
			if (title.equals(currentWindowTitle))
				break;
		}
	}

	protected String getCurrentPageURL() {
		return driver.getCurrentUrl();
	}

	protected boolean waitForTitleToBe(String title) {
		return wait.until(ExpectedConditions.titleContains(title));
	}

	protected String getCurrentPageTitle() {
		return driver.getTitle();
	}

	protected void refreshCurrentPage() {
		driver.navigate().refresh();
	}

	List<WebElement> getAllElements(String locatorType, String locatorValue, boolean isWaitRequired) {
		List<WebElement> element = null;

		switch (locatorType.toUpperCase()) {
		case "ID":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(locatorValue)));
			else
				element = driver.findElements(By.id(locatorValue));
			break;

		case "NAME":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(locatorValue)));
			else
				element = driver.findElements(By.name(locatorValue));
			break;

		case "XPATH":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locatorValue)));
			else
				element = driver.findElements(By.xpath(locatorValue));
			break;

		case "CSS":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(locatorValue)));
			else
				element = driver.findElements(By.cssSelector(locatorValue));
			break;

		case "LINKTEXT":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText(locatorValue)));
			else
				element = driver.findElements(By.linkText(locatorValue));
			break;

		case "PARTIALLINKTEXT":
			if (isWaitRequired)
				element = wait
						.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.partialLinkText(locatorValue)));
			else
				element = driver.findElements(By.partialLinkText(locatorValue));
			break;

		case "CLASSNAME":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(locatorValue)));
			else
				element = driver.findElements(By.className(locatorValue));
			break;

		case "TAGNAME":
			if (isWaitRequired)
				element = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName(locatorValue)));
			else
				element = driver.findElements(By.tagName(locatorValue));
			break;

		default:
			throw new InvalidLocatorException(locatorType + " is not supported");
		}
		return element;
	}

	protected List<String> getAllElementText(String locatorType, String locatorValue, boolean isWaitRequired) {
		List<WebElement> listOfWebElement = getAllElements(locatorType, locatorValue, isWaitRequired);
		List<String> listOfElementText = new ArrayList<String>();

		for (WebElement e : listOfWebElement) {
			String elementText = getElementText(e);
			listOfElementText.add(elementText);
		}

		return listOfElementText;
	}

	protected boolean isElementClickable(WebElement e) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(e));
			return true;
		}catch(Exception exception) {
			return false;
		}
	}
	
	protected boolean isElementSelected(String locatorType, String locatorValue, boolean isWaitRequired) {
		WebElement e = getElement(locatorType, locatorValue, isWaitRequired);
		return e.isSelected();
	}
	
	public static void captureScreenShot(String screenShotName) {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		
		try {
			FileUtils.copyFile(srcFile, new File(".//screenshots//"+screenShotName+"_"+TimeStamp.getTimeStamp()+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void closeBrowser() {
		driver.quit();
	}
}
