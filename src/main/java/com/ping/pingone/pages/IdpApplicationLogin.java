package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class IdpApplicationLogin  extends AbstractBasePage{	
	static final By LOGIN_BUTTON = By.xpath("//input[@value='Login']");
	public IdpApplicationLogin(WebDriver webDriver) {
		super(webDriver);
	}
	@Override
	protected List<By> verificationPoints() {
		List<By> verificationPoints = new ArrayList<By>();
		verificationPoints.add(LOGIN_BUTTON);
		return verificationPoints;
	}
	
	public void clickLoginButton(){
		findWebElementAndClick(LOGIN_BUTTON);
		// wait for the redirect to finish -- it's not a realSite
		try {
			FakeAppPage fakePage = new FakeAppPage(webDriver);
		} catch (AssertionError e) {
			// this is expected; just trying to wait the page to load;
		}
	}
}
