package steps;

import org.junit.Assert;

import base.ControlActions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CommonPageMethod;
import pages.DashboardPage;
import pages.LoginPage;

public class LoginStep {
	LoginPage loginPage;
	DashboardPage dashboardPage;
	CommonPageMethod commonPageMethod;
	
	@Given("User is on Login Page")
	public void user_is_on_login_page() {
		ControlActions.launchBrowser();
		loginPage = new LoginPage();
		dashboardPage = new DashboardPage();
		commonPageMethod = new CommonPageMethod();
	}
	
	@When("User enter username as {string}")
	public void user_enter_username_as(String username) {
		System.out.println("STEP - Enter valid username");
		loginPage.enterUsername(username);
	}
	
	@When("User enter password as {string}")
	public void user_enter_password_as(String password) {
		System.out.println("STEP - Enter valid password");
		loginPage.enterPassword(password);
	}
	
	@When("User click on login button")
	public void user_click_on_login_button() {
		System.out.println("STEP - Click on login button");
		loginPage.clickOnLoginBtn();
	}
	
	@Then("User is on dashboard Page")
	public void user_is_on_dashboard_page() {
		boolean isDashBoardVisible = dashboardPage.isDashboardMenuVisible();
		Assert.assertEquals("Dashboard Page is not visible", isDashBoardVisible, true);

		System.out.println("VERIFY - Dashboard page is displayed");
		String currentURL = commonPageMethod.getCurrentPageURL();
		Assert.assertEquals("Dashboard Page is not visible", currentURL.contains("dashboard"), true);
	}
	
	@Then("User verify authentication failure popup is displayed")
	public void user_verify_authentication_failure_popup_is_displayed() {
		System.out.println("VERIFY - Authentication failure popup is displayed");
		boolean isAuthPopUpDisplayed = loginPage.isAuthenticationFailurePopupDisplayed();
		Assert.assertEquals("Authentication failure popup was not dispalyed",isAuthPopUpDisplayed, true);
	}
	
	@Then("User verify user is on Login Page")
	public void user_verify_user_is_on_login_page() {
		System.out.println("VERIFY - Login page is still visible");
		String currentURL = loginPage.getCurrentPageURL();
		Assert.assertTrue(currentURL.endsWith("login"));
	}

}
