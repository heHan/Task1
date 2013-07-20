package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends AbstractBasePage {
	
	static final By EMAIL_INPUT = By.id("email");
	static final By PASSWORD_INPUT = By.id("password");
	static final By LOGIN_BUTTON = By.name("loginPanelAjaxSubmit");

	public LoginPage(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	protected List<By> verificationPoints() {
		List<By> verificationPoints = new ArrayList<By>();
		verificationPoints.add(EMAIL_INPUT);
		verificationPoints.add(PASSWORD_INPUT);
		verificationPoints.add(LOGIN_BUTTON);
		return verificationPoints;
	}
	
	public void enterEmail(String email) {
		findWebElementAndType(EMAIL_INPUT, email);
	}
	
	public void enterPassword(String password) {
		findWebElementAndType(PASSWORD_INPUT, password);
	}
	
	public void clickLogIn() {
		findWebElementAndClick(LOGIN_BUTTON);		
	}

}
