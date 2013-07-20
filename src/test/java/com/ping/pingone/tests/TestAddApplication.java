package com.ping.pingone.tests;

import org.testng.annotations.Test;

import com.ping.pingone.pages.HomePage;
import com.ping.pingone.pages.LoginPage;
import com.ping.pingone.pages.MyApplicationsPage;
import com.ping.pingone.webdriver.test.AbstractWebDriverBase;

public class TestAddApplication extends AbstractWebDriverBase{
	static final String PINGONE_URL_PROPERTIES_KEY = "test.environment.baseUrl";
	static final String PINGONE_LOGIN_USER_EMAIL_KEY = "test.environment.login.email";
	static final String PINGONE_LOGIN_USER_PASSWORD_KEY = "test.environment.login.password";

	static final String CANNOT_LOAD_PROPERTY = "Cannot load property: ";
	
	static final String NEW_APPLICATION_CATEGORY = "Other";
	static final String NEW_APPLICATION_NAME = "HenryTest" + getCurrentTime();
	static final String NEW_APPLICATION_DESCRIPTION = "A short description";
	static final String LOG_FILE_PATH = "pingLogo.jpg";
	
	@Test (description = "login pingon; click add new application; create connection; " +
			"complete the form; verify the new application is added;")
	public void testAddNewApplication() {
		String pingOneUrl = testProperties.getProperties().getProperty(PINGONE_URL_PROPERTIES_KEY);
		String loginEmail = testProperties.getProperties().getProperty(PINGONE_LOGIN_USER_EMAIL_KEY);
		String loginPassword = testProperties.getProperties().getProperty(PINGONE_LOGIN_USER_PASSWORD_KEY);
		if (pingOneUrl == null) {
			throw new AssertionError(CANNOT_LOAD_PROPERTY + PINGONE_URL_PROPERTIES_KEY);
		}
		if (loginEmail == null) {
			throw new AssertionError(CANNOT_LOAD_PROPERTY + PINGONE_LOGIN_USER_EMAIL_KEY);
		}
		if (loginPassword == null) {
			throw new AssertionError(CANNOT_LOAD_PROPERTY + PINGONE_LOGIN_USER_PASSWORD_KEY);
		}
		reporterLog("Steps");
		reporterLog("1. Load pingone login page");
		webDriver.get(pingOneUrl);
		reporterLog("2. Login in with account: " + loginEmail + "/" + loginPassword);
		LoginPage loginPage = new LoginPage(webDriver);
		loginPage.enterEmail(loginEmail);
		loginPage.enterPassword(loginPassword);
		loginPage.clickLogIn();
		reporterLog("3. Click my application tab on home page");
		HomePage homePage = new HomePage(webDriver);
		homePage.clickMyApplications();
		reporterLog("4. Click add new application button");
		MyApplicationsPage myApplicationsPage = new MyApplicationsPage(webDriver);
		myApplicationsPage.clickAddNewApplicationButton();
		reporterLog("5. Select category: " + NEW_APPLICATION_CATEGORY);
		myApplicationsPage.selectCategory(NEW_APPLICATION_CATEGORY);
		reporterLog("6. Enter the application name" + NEW_APPLICATION_NAME);
		myApplicationsPage.enterApplicationName(NEW_APPLICATION_NAME);
		reporterLog("7. Enter application description: " + NEW_APPLICATION_DESCRIPTION);
		myApplicationsPage.enterApplicationDescription(NEW_APPLICATION_DESCRIPTION);
		reporterLog("8. Select private visibility");
		myApplicationsPage.selectPrivateVisibility();
		reporterLog("9. Change logo");
		myApplicationsPage.changeLogo(LOG_FILE_PATH);
		throw new AssertionError("fail for now");
//		reporterLog("Click Continue to Next Step");
//		myApplicationsPage.clickContinueButton();
//		
	}
}
