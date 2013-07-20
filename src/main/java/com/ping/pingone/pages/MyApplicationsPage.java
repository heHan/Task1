package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyApplicationsPage extends AbstractBasePage {
	
	static final By ADD_NEW_APPLICATION_BUTTON = By.xpath("//a[@class='btn btn-primary']");	
	static final By CONTINUE_NEXT_BUTTON = By.xpath("//input[@class='nextButton btn btn-primary']");
	static final By CATEGORY_DROPDOWN = By.id("category");
	static final By APPLICATION_NAME_INPUT_BOX = By.id("name");
	static final By APPLICATION_DESCRIPTION = By.id("description");
	static final By APPLICATION_PRIVATE_VISIBILITY = By.id("visibility_private");
	static final By APPLICATION_PUBLIC_VISIBILITY = By.id("visibility_public"); // not been used yet
	static final By THE_FIREST_DELETE_BUTTON = By.xpath("//a[@class='btn std-btn last']");
	static final By CHANGE_LOGO_BUTTON = By.xpath("//span[@class='btn btn-file']");
	static final By LOGO_INPUT = By.xpath("//input[@name='view:logoFile:form:file']");
	public MyApplicationsPage(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	protected List<By> verificationPoints() {
		List<By> verificationPoints = new ArrayList<By>();
		verificationPoints.add(ADD_NEW_APPLICATION_BUTTON);
		return verificationPoints;
	}
	
	public void clickAddNewApplicationButton() {
		findWebElementAndClick(ADD_NEW_APPLICATION_BUTTON);
	}
	/**
	 * Select category from the dropdown
	 * @param category the cateogry to select from displayed text
	 */
	public void selectCategory(String category) {
		findWebElementAndSelect(CATEGORY_DROPDOWN, category);
	}
	
	public void enterApplicationName(String name){
		findWebElementAndType(APPLICATION_NAME_INPUT_BOX, name);
	}
	
	public void clickFirstDeleteButton() {
		findWebElementAndClick(THE_FIREST_DELETE_BUTTON);
	}
	public void clickContinueButton() {
		findWebElementAndClick(CONTINUE_NEXT_BUTTON);
	}
	public void enterApplicationDescription(String description){
		findWebElementAndType(APPLICATION_DESCRIPTION, description);
	}
	
	public void selectPrivateVisibility(){
		findWebElementAndClick(APPLICATION_PRIVATE_VISIBILITY);
	}
	
	public void changeLogo(String filePath){
		findWebElementAndClick(CHANGE_LOGO_BUTTON);
		findWebElementAndType(LOGO_INPUT, filePath);
	}
	public boolean verifyNewApplicationExisted(){
		return true;
	}
}
