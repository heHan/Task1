package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * New application wizard # five page object
 * @author HenryH
 *
 */
public class NewApplicationWizardFive extends MyApplicationsPage {
	static final By SAVE_PUBLISH = By.id("wizardFinishButton");
	
	public NewApplicationWizardFive(WebDriver webDriver) {
		super(webDriver);
	}
	
	@Override
	protected List<By> verificationPoints() {
		List<By> verificationPoints = new ArrayList<By>();
		verificationPoints.add(SAVE_PUBLISH);
		return verificationPoints;
	}
	
	public void clickSaveButton() {
		findWebElementAndClick(SAVE_PUBLISH);
	}
}
