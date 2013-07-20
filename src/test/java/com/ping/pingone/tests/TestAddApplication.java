package com.ping.pingone.tests;

import org.testng.annotations.Test;

import com.ping.pingone.pages.LoginPage;
import com.ping.pingone.webdriver.test.AbstractWebDriverBase;

public class TestAddApplication extends AbstractWebDriverBase{
	static final String PINGONE_URL_PROPERTIES_KEY = "test.environment.baseUrl";
	static final String PINGONE_LOGIN_USER_EMAIL_KEY = "test.environment.login.email";
	static final String PINGONE_LOGIN_USER_PASSWORD_KEY = "test.environment.login.password";
	
	static final String CANNOT_LOAD_PROPERTY = "Cannot load property: ";
	
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
		
		webDriver.get(pingOneUrl);
		LoginPage loginPage = new LoginPage(webDriver);
		loginPage.enterEmail(loginEmail);
		loginPage.enterPassword(loginPassword);
		loginPage.clickLogIn();
		
	}
}
