package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * My Application page 
 * @author HenryH
 *
 */
public class MyApplicationsPage extends AbstractBasePage {
	static final By ADD_NEW_APPLICATION_BUTTON = By.xpath("//a[@class='btn btn-primary']");	
	static final By CONTINUE_NEXT_BUTTON = By.xpath("//input[@class='nextButton btn btn-primary']");
	static final By THE_FIREST_DELETE_BUTTON = By.xpath("//a[@class='btn std-btn last']");

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

	public void clickFirstDeleteButton() {
		findWebElementAndClick(THE_FIREST_DELETE_BUTTON);
	}
	
	public boolean verifyNewApplicationExisted(){
		return true;
	}
	
	public class NewApplicationFirstWizard{
		protected List<By> verificationPoints() {
			List<By> verificationPoints = new ArrayList<By>();
			verificationPoints.add(ADD_NEW_APPLICATION_BUTTON);
			return verificationPoints;
		}
		
	}
}
