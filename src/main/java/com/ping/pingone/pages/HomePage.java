package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage extends AbstractBasePage{
	static final By MENU_BASHBOARD = By.id("headerLinkOverview");
	static final By MENU_MY_APPLICATION = By.id("headerLinkSaasApplications");
	static final By MENU_MY_CUSTOMER_CONNECTIONS = By.id("headerLinkSaasCustomerConnections");
	static final By MENU_ACCOUNT = By.id("headerLinkAccount");

	public HomePage(WebDriver webDriver) {
		super(webDriver);
	}
	@Override
	protected List<By> verificationPoints() {
		List<By> verificationPoints = new ArrayList<By>();
		verificationPoints.add(MENU_BASHBOARD);
		verificationPoints.add(MENU_MY_APPLICATION);
		verificationPoints.add(MENU_MY_CUSTOMER_CONNECTIONS);
		verificationPoints.add(MENU_ACCOUNT);
		return verificationPoints;
	}
	
	public void clickMyApplications(){
		WebElement myApplication = findWebElement(MENU_MY_APPLICATION);
		myApplication.click();
	}
}
