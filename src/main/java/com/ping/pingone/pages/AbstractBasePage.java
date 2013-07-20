package com.ping.pingone.pages;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * A generic parent class of all Page objects. 
 * 
 * @author hehan
 *
 */

public abstract class AbstractBasePage{

	/**
	 * Number of seconds to wait until a timeout is thrown.
	 */
	public static final int TIMEOUT = 30;

	private static final Logger log = Logger.getLogger(AbstractBasePage.class);
	protected WebDriver webDriver;
	protected WebDriverWait wait;
	protected String windowHandle;
	private static final String NO_SUCH_ELEMENT = "Can not find such element on the page";
	private static final String NULL_WEB_ELEMENT = "Web element object is null";
	private static final String NOT_VISIBLE = "Found the element on the page, not it's not visible";
	
	
	/**
	 * Constructor for the Page object, includes verification that the page has
	 * loaded.
	 * 
	 * @param webDriver the driver to perform actions
	 * @param browser the browser to run test on
	 * @param locale to identify the different store front
	 */
	protected AbstractBasePage(WebDriver webDriver) {
		this.webDriver = webDriver;
		this.wait = new WebDriverWait(webDriver, TIMEOUT);
		this.windowHandle = webDriver.getWindowHandle();
		waitForPageToLoad();
	}
	
	/**
	 * @return Locators to verify that the browser has loaded the correct page.
	 */
	protected abstract List<By> verificationPoints();

	/**
	 * Waits until the "verification points" are found. If any are not found, it
	 * fails the test if the wait time runs out.
	 * @return True/false depends on page loading
	 */
	public void waitForPageToLoad() {
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver wd) {
					return isPageLoaded(verificationPoints());
				}
			});
		} 
		catch (TimeoutException timeOutException) {
			throw new AssertionError(this.getClass().getSimpleName() + " is not verified. Missing " + returnMissingElements(verificationPoints()));
		}
	}

	private String returnMissingElements(List<By> vps) {
		String notFoundVp = "";
		for (By vp : vps) {
			try {
				if(!findWebElement(vp).isDisplayed()) {
					if (!notFoundVp.isEmpty()){
						notFoundVp = notFoundVp + "and ";
					}
					notFoundVp = notFoundVp + vp.toString() + " ";
				}
				else {
					log.debug("found name " + vp.toString());
				}
			} catch (NoSuchElementException nse) {
				if (!notFoundVp.isEmpty()){
					notFoundVp = notFoundVp + "and ";
				}
				notFoundVp = notFoundVp + vp.toString() + " ";
			} catch (NullPointerException e){
				log.debug(e.getLocalizedMessage());
			} 
		}
		return notFoundVp;	
	}
	
	/**
	 * Simple helper to determine if the browser has loaded the correct page.
	 */
	private boolean isPageLoaded(List<By> vps) {
		try {
			for (By point : vps) {
				if(!webDriver.findElement(point).isDisplayed()) {
					return false;
				}
			}
			// If all verification points are found, we are on the correct page.
			return true;
		} catch (NoSuchElementException noSuchElementException) {
			log.debug(NO_SUCH_ELEMENT, noSuchElementException);
			return false;
		} catch (NullPointerException noPointerException) {
			log.debug(NULL_WEB_ELEMENT, noPointerException);
			return false;
		} catch (ElementNotVisibleException notVisibleException) {
			log.debug(NOT_VISIBLE, notVisibleException);
			return false;
		}
	}

	/**
	 * Wrap the webdriver findElement function to throw proper message
	 * @param webElementSelector webElementSelector 
	 * @return WebElement reference to the web object
	 */
	protected WebElement findWebElement (By webElementSelector){
		try {
			return webDriver.findElement(webElementSelector);	
		} catch (NoSuchElementException noSuchElement) {
			throw new AssertionError("Cannot find the web element with selector " + webElementSelector.toString());
		}		
	}
	
	/**
	 * Wrap the webdriver findElement and click functions and fail the test on exception thrown
	 * @param webElementSelector webElementSelector 
	 * @return WebElement reference to the web object
	 */
	protected void findWebElementAndClick (By webElementSelector){
		WebElement button =  findWebElement(webElementSelector);
		try {		
			button.click();
		} catch (StaleElementReferenceException e) {
			throw new AssertionError("Found the web element, but cannot perform click action. " + webElementSelector.toString());
		}		
	}
	
	/**
	 * Wrap the webdriver findElement and type function and fail the test on exception thrown
	 * @param webElementSelector webElementSelector 
	 * @param text the text to enter into the field
	 */
	protected void findWebElementAndType (By webElementSelector, String text){
		WebElement inputBox = findWebElement(webElementSelector);
		try {
			inputBox.sendKeys(text);
		} catch (ElementNotVisibleException e){
			throw new AssertionError("Found the web element, but it's not visible to send keys. " + webElementSelector.toString());
		}
	}
	
	/**
	 * Wrap the webdriver select function and fail the test on exception thrown
	 * @param webElementSelector webElementSelector 
	 * @param value the value to select from dropDown list
	 */
	protected void findWebElementAndSelect(By webElementSelector, String value){
		try {
			Select dropdown = new Select(findWebElement(webElementSelector));
			dropdown.selectByVisibleText(value);
		} catch (UnexpectedTagNameException e) {
			throw new AssertionError("Cannot create select item basing on webElement " + webElementSelector.toString());
		}		
	}
}
