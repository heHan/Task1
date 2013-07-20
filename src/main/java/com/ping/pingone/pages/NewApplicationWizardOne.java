package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * New application wizard one page object
 * @author HenryH
 *
 */
public class NewApplicationWizardOne extends MyApplicationsPage {
	static final By CATEGORY_DROPDOWN = By.id("category");
	static final By APPLICATION_NAME_INPUT_BOX = By.id("name");
	static final By APPLICATION_DESCRIPTION = By.id("description");
	static final By APPLICATION_PRIVATE_VISIBILITY = By.id("visibility_private");
	static final By APPLICATION_PUBLIC_VISIBILITY = By.id("visibility_public"); // not been used yet
	public NewApplicationWizardOne(WebDriver webDriver) {
		super(webDriver);
	}
	
	@Override
	protected List<By> verificationPoints() {
		List<By> verificationPoints = new ArrayList<By>();
		verificationPoints.add(CATEGORY_DROPDOWN);
		verificationPoints.add(APPLICATION_NAME_INPUT_BOX);
		verificationPoints.add(APPLICATION_DESCRIPTION);
		verificationPoints.add(APPLICATION_PRIVATE_VISIBILITY);
		verificationPoints.add(CONTINUE_NEXT_BUTTON);
		return verificationPoints;
	}
	/**
	 * Select category from the dropdown
	 * @param category the cateogry to select from displayed text
	 */
	public void selectCategory(String category) {
		findWebElementAndSelect(CATEGORY_DROPDOWN, category);
	}
	/**
	 * Enter the application name
	 * @param name the application name
	 */
	public void enterApplicationName(String name){
		findWebElementAndType(APPLICATION_NAME_INPUT_BOX, name);
	}
	
	/**
	 * Enter the application short description
	 * @param description
	 */
	public void enterApplicationDescription(String description){
		findWebElementAndType(APPLICATION_DESCRIPTION, description);
	}
	
	/**
	 * click the continue button and return the next page object
	 * @return next wizard object #2
	 */
	public NewApplicationWizardTwo clickContinueButton() {
		findWebElementAndClick(CONTINUE_NEXT_BUTTON);
		return new NewApplicationWizardTwo(webDriver);
	}
	
	public void selectPrivateVisibility(){
		findWebElementAndClick(APPLICATION_PRIVATE_VISIBILITY);
	}
}
