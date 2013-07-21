package com.ping.pingone.tests;

import org.testng.annotations.Test;

import com.ping.pingone.pages.HomePage;
import com.ping.pingone.pages.LoginPage;
import com.ping.pingone.pages.MyApplicationsPage;
import com.ping.pingone.pages.NewApplicationWizardFive;
import com.ping.pingone.pages.NewApplicationWizardOne;
import com.ping.pingone.pages.NewApplicationWizardThree;
import com.ping.pingone.pages.NewApplicationWizardTwo;
import com.ping.pingone.test.properties.TestPropertiesInterface;
import com.ping.pingone.webdriver.test.AbstractWebDriverBase;
/**
 * Test create new application workflow
 * @author HenryH
 *
 */
public class TestAddApplication extends AbstractWebDriverBase{
	static final String NEW_APPLICATION_CATEGORY = "Other";
	static final String NEW_APPLICATION_NAME = "HenryTest" + getCurrentTime();
	static final String NEW_APPLICATION_DESCRIPTION = "A short description";
	static final String DOMAIN_NAME = "example.com";
	static final String APPLICATION_URL = "http://myapp.example.com/sso.php";
	
	@Test (description = "login pingon; click add new application; create connection; " +
			"complete the form; verify the new application is added;")
	public void testAddNewApplication() {
		String pingOneUrl = testProperties.getProperties().getProperty(TestPropertiesInterface.PINGONE_URL_PROPERTIES_KEY);
		String loginEmail = testProperties.getProperties().getProperty(TestPropertiesInterface.PINGONE_LOGIN_USER_EMAIL_KEY);
		String loginPassword = testProperties.getProperties().getProperty(TestPropertiesInterface.PINGONE_LOGIN_USER_PASSWORD_KEY);
		if (pingOneUrl == null) {
			throw new AssertionError(TestPropertiesInterface.CANNOT_LOAD_PROPERTY + TestPropertiesInterface.PINGONE_URL_PROPERTIES_KEY);
		}
		if (loginEmail == null) {
			throw new AssertionError(TestPropertiesInterface.CANNOT_LOAD_PROPERTY + TestPropertiesInterface.PINGONE_LOGIN_USER_EMAIL_KEY);
		}
		if (loginPassword == null) {
			throw new AssertionError(TestPropertiesInterface.CANNOT_LOAD_PROPERTY + TestPropertiesInterface.PINGONE_LOGIN_USER_PASSWORD_KEY);
		}
		reporterLog("Steps");
		reporterLog("1. Load pingone login page: " + pingOneUrl);
		webDriver.get(pingOneUrl);
		reporterLog("2. Login in with account: " + loginEmail + "/" + loginPassword);
		LoginPage loginPage = new LoginPage(webDriver);
		loginPage.enterEmail(loginEmail);
		loginPage.enterPassword(loginPassword);
		HomePage homePage = loginPage.clickLogIn();
		reporterLog("3. Click my application tab on home page");		
		MyApplicationsPage myApplicationsPage = homePage.clickMyApplications();		
		reporterLog("4. Click add new application button");
		NewApplicationWizardOne wizardOne = myApplicationsPage.clickAddNewApplicationButton();		
		reporterLog("5. Select category: " + NEW_APPLICATION_CATEGORY);		
		wizardOne.selectCategory(NEW_APPLICATION_CATEGORY);
		reporterLog("6. Enter the application name" + NEW_APPLICATION_NAME);
		wizardOne.enterApplicationName(NEW_APPLICATION_NAME);
		reporterLog("7. Enter application description: " + NEW_APPLICATION_DESCRIPTION);
		wizardOne.enterApplicationDescription(NEW_APPLICATION_DESCRIPTION);
		reporterLog("8. Select private visibility");
		wizardOne.selectPrivateVisibility();		
		reporterLog("9. Click Continue to Step 2");
		NewApplicationWizardTwo wizardTwo = wizardOne.clickContinueButton();		
		reporterLog("10. Choose enable SAML through PingOne ");
		wizardTwo.chooseSAMLEnabled();
		reporterLog("10. Enter domain name: " + DOMAIN_NAME);
		wizardTwo.enterDomainName(DOMAIN_NAME);
		reporterLog("11. Enter application url: " + APPLICATION_URL);
		wizardTwo.enterApplicationUrl(APPLICATION_URL);
		reporterLog("12. Click Continue to Step 3");
		NewApplicationWizardThree wizardThree = wizardTwo.clickContinueButton();		
		reporterLog("13. Click Continue to Step 5");
		NewApplicationWizardFive wizardFive = wizardThree.clickContinueButton();		
		reporterLog("14. Save application");
		wizardFive.clickSaveButton();		
		reporterLog("Verify new application, " + NEW_APPLICATION_NAME + ", is created");
		myApplicationsPage.verifyNewApplicationExisted(NEW_APPLICATION_NAME);
		reporterLog("Delete application, " + NEW_APPLICATION_NAME);
		myApplicationsPage.clickDeleteButtonByApplicationName(NEW_APPLICATION_NAME);
		myApplicationsPage.verifyDeleted();
	}
}
