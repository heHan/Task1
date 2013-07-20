package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FakeAppPage extends AbstractBasePage{
	static final By FAKE_HOMEVERIFICATION = By.id("this is not real");
	protected FakeAppPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<By> verificationPoints() {
		List<By> verificationPoints = new ArrayList<By>();
		verificationPoints.add(FAKE_HOMEVERIFICATION);
		return verificationPoints;
	}

}
