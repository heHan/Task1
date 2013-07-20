package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * My Application page 
 * @author HenryH
 *
 */
public class MyApplicationsPage extends AbstractBasePage {
	static final By ADD_NEW_APPLICATION_BUTTON = By.xpath("//a[@class='btn btn-primary']");	
	static final By CONTINUE_NEXT_BUTTON = By.xpath("//input[@class='nextButton btn btn-primary']");
	static final By THE_FIREST_DELETE_BUTTON = By.xpath("//a[@class='btn std-btn last']");
	static final By FEEDBACK_PANEL = By.xpath("//span[@class='feedbackPanelINFO']");
	static final By DELETE_CONFIRM_BUTTON = By.id("confirmationDialogAction");
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
	 * Verify there is a new application shown
	 * @param applicationName
	 */
	public void verifyNewApplicationExisted(String applicationName){
		findWebElement(FEEDBACK_PANEL);
		By newApplicationName = By.xpath("//span[text()='" +applicationName + "']");
		findWebElement(newApplicationName);
	}
	public void clickFirstDeleteButton() {
		findWebElementAndClick(THE_FIREST_DELETE_BUTTON);
		findWebElementAndClick(DELETE_CONFIRM_BUTTON);
	}

	public void verifyDeleted() {
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver wd) {
					try {
						findWebElement(DELETE_CONFIRM_BUTTON);
					} catch (AssertionError e){
						// if assertionError is caught, it shows the button is not there, delete action is completed
						return true;
					}
					return false;
				}
			});
		} 
		catch (TimeoutException timeOutException) {
			throw new AssertionError("Deleting the application took too long.");
		}
	}
}
