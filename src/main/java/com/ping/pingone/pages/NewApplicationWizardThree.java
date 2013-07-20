package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * New application wizard # three page object
 * @author HenryH
 *
 */
public class NewApplicationWizardThree extends MyApplicationsPage {

	static final By ADD_ATTRIBUTE = By.id("addAttribute");
	public NewApplicationWizardThree(WebDriver webDriver) {
		super(webDriver);
	}
	
	@Override
	protected List<By> verificationPoints() {
		List<By> verificationPoints = new ArrayList<By>();
		verificationPoints.add(ADD_ATTRIBUTE);
		verificationPoints.add(CONTINUE_NEXT_BUTTON);
		return verificationPoints;
	}
	
	public void clickContinueButton() {
		findWebElementAndClick(CONTINUE_NEXT_BUTTON);
	}
}
