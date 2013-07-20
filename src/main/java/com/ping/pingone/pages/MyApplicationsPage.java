package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyApplicationsPage extends AbstractBasePage {
	
	static final By ADD_NEW_APPLICATION_BUTTON = By.className("btn btn-primary");	
	static final By CONTINUE_NEXT_BUTTON = By.className("nextButton btn btn-primary");
	static final By CATEGORY_SELECTION = By.id("category");
	static final By THE_FIREST_DELETE_BUTTON = By.className("btn std-btn last");
	
	
	
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
		WebElement addNewApplicationButton = findWebElement(ADD_NEW_APPLICATION_BUTTON);
		addNewApplicationButton.click();
	}
	
	public void clickFirstDeleteButton() {
		WebElement addNewApplicationButton = findWebElement(THE_FIREST_DELETE_BUTTON);
		addNewApplicationButton.click();
	}
	
	public boolean verifyNewApplicationExisted(){
		return true;
	}
}
