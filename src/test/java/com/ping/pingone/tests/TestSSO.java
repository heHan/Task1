package com.ping.pingone.tests;

import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ping.pingone.pages.HomePage;
import com.ping.pingone.pages.IdpApplicationLogin;
import com.ping.pingone.pages.LoginPage;
import com.ping.pingone.pages.MyApplicationsPage;
import com.ping.pingone.test.properties.TestPropertiesInterface;
import com.ping.pingone.webdriver.test.AbstractWebDriverBase;

/**
 * Test the SSO workflow
 * @author HenryH
 *
 */
public class TestSSO extends AbstractWebDriverBase{
	static final String TEST_APPLICATION_NAME = "SampleAPSEnabledApp";

	@Test (description = "")
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
		MyApplicationsPage myApplicationsPage = new MyApplicationsPage(webDriver);
		myApplicationsPage.clickTestConnectionButtonByApplicationName(TEST_APPLICATION_NAME);
		
		String currentWindow = webDriver.getWindowHandle();
		IdpApplicationLogin idpApplicationLogin = myApplicationsPage.clickStartSSOAndWaitForRedirect(currentWindow);
		idpApplicationLogin.clickLoginButton();
		Reporter.log(webDriver.getCurrentUrl(), true);
		//https://myapp.example.com/sso.php?tokenid=ID50b3a0284d5f0a392033249b04ace42b333ff4e6afc4fcdb01&agentid=3b1ba308
	}
}
