package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	static final By START_SSO = By.xpath("//a[text()= 'Start Single Sign-On']");
	public MyApplicationsPage(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	protected List<By> verificationPoints() {
		List<By> verificationPoints = new ArrayList<By>();
		verificationPoints.add(ADD_NEW_APPLICATION_BUTTON);
		return verificationPoints;
	}
	/**
	 * 
	 * @return {@link NewApplicationWizardOne}
	 */
	public NewApplicationWizardOne clickAddNewApplicationButton() {
		findWebElementAndClick(ADD_NEW_APPLICATION_BUTTON);
		return new NewApplicationWizardOne(webDriver);
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
	
	public void clickTestConnectionButtonByApplicationName(String applicationName) {
		String xpathString = "//span[text()='" + applicationName + "']/../../..//a[@class='btn sol-btn std-btn']";
		By testConnection = By.xpath(xpathString);
		findWebElementAndClick(testConnection);
	}
	
	/**
	 * Click the start sso link and switch window handler into the new window
	 * @param currentWindow existing window handler
	 * @return {@link IdpApplicationLogin}
	 */
	public IdpApplicationLogin clickStartSSOAndWaitForRedirect(String currentWindow){
		findWebElementAndClick(START_SSO);
		Set<String> openWindows = webDriver.getWindowHandles();
		Iterator<String> windowsIterator = openWindows.iterator();
		while(windowsIterator.hasNext())
		{
			String popupHandle=windowsIterator.next().toString();
			if(!popupHandle.contains(currentWindow))
			{
				webDriver.switchTo().window(popupHandle);
				return new IdpApplicationLogin(webDriver);
			}
		}
		throw new AssertionError("Cannot load idp application login page");
	}
}
