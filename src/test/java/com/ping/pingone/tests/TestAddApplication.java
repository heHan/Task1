package com.ping.pingone.tests;

import org.testng.annotations.Test;

import com.ping.pingone.pages.HomePage;
import com.ping.pingone.pages.LoginPage;
import com.ping.pingone.pages.MyApplicationsPage;
import com.ping.pingone.pages.NewApplicationWizardFive;
import com.ping.pingone.pages.NewApplicationWizardOne;
import com.ping.pingone.pages.NewApplicationWizardThree;
import com.ping.pingone.pages.NewApplicationWizardTwo;
import com.ping.pingone.webdriver.test.AbstractWebDriverBase;

public class TestAddApplication extends AbstractWebDriverBase{
	static final String PINGONE_URL_PROPERTIES_KEY = "test.environment.baseUrl";
	static final String PINGONE_LOGIN_USER_EMAIL_KEY = "test.environment.login.email";
	static final String PINGONE_LOGIN_USER_PASSWORD_KEY = "test.environment.login.password";

	static final String CANNOT_LOAD_PROPERTY = "Cannot load property: ";
	
	static final String NEW_APPLICATION_CATEGORY = "Other";
	static final String NEW_APPLICATION_NAME = "HenryTest" + getCurrentTime();
	static final String NEW_APPLICATION_DESCRIPTION = "A short description";
	static final String DOMAIN_NAME = "example.com";
	static final String APPLICATION_URL = "http://myapp.example.com/sso.php";
	
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
		NewApplicationWizardOne wizardOne = new NewApplicationWizardOne(webDriver);
		wizardOne.selectCategory(NEW_APPLICATION_CATEGORY);
		reporterLog("6. Enter the application name" + NEW_APPLICATION_NAME);
		wizardOne.enterApplicationName(NEW_APPLICATION_NAME);
		reporterLog("7. Enter application description: " + NEW_APPLICATION_DESCRIPTION);
		wizardOne.enterApplicationDescription(NEW_APPLICATION_DESCRIPTION);
		reporterLog("8. Select private visibility");
		wizardOne.selectPrivateVisibility();		
		reporterLog("9. Click Continue to Step 2");
		wizardOne.clickContinueButton();
		
		reporterLog("10. Choose enable SAML through PingOne ");
		NewApplicationWizardTwo wizardTwo = new NewApplicationWizardTwo(webDriver);
		wizardTwo.chooseSAMLEnabled();
		reporterLog("10. Enter domain name: " + DOMAIN_NAME);
		wizardTwo.enterDomainName(DOMAIN_NAME);
		reporterLog("11. Enter application url: " + APPLICATION_URL);
		wizardTwo.enterApplicationUrl(APPLICATION_URL);
		reporterLog("12. Click Continue to Step 3");
		wizardTwo.clickContinueButton();
		
		reporterLog("13. Click Continue to Step 5");
		NewApplicationWizardThree wizardThree = new NewApplicationWizardThree(webDriver);		
		wizardThree.clickContinueButton();
		
		reporterLog("14. Save application");
		NewApplicationWizardFive wizardFive = new NewApplicationWizardFive(webDriver);	
		wizardFive.clickSaveButton();
		
		reporterLog("Verify new application, " + NEW_APPLICATION_NAME + ", is created");
		myApplicationsPage.verifyNewApplicationExisted(NEW_APPLICATION_NAME);
		reporterLog("Delete application, " + NEW_APPLICATION_NAME);
		myApplicationsPage.clickFirstDeleteButton();
		myApplicationsPage.verifyDeleted();
		throw new AssertionError("fail for now");
	}
}
