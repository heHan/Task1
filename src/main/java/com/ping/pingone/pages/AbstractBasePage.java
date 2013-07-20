package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


/**
 * A generic parent class of all Page objects. 
 * 
 * @author hehan
 *
 */

public abstract class AbstractBasePage {

	/**
	 * Number of seconds to wait until a timeout is thrown.
	 */
	public static final int TIMEOUT = 30;

	private static final Logger log = Logger.getLogger(AbstractBasePage.class);
	protected WebDriver webDriver;
	protected WebDriverWait wait;
	protected String windowHandle;
	protected boolean isLoaded;
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
		this.isLoaded = waitForPageToLoad();
	}

	/**
	 * @return the webDriver
	 */
	public WebDriver getWebDriver() {
		return webDriver;
	}

	/**
	 * @param webDriver the webDriver to set
	 */
	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	/**
	 * Waits until the "verification points" are found. If any are not found, it
	 * fails the test if the wait time runs out.
	 * @return True/false depends on page loading
	 */
	public boolean waitForPageToLoad() {
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
		return true;
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
	 * Wait for a element to load on a page
	 * @param element
	 * @return true is page is found, test fail otherwise
	 */
	public boolean waitForElement(By element) {
		List<By> elements = new ArrayList<By>();
		elements.add(element);
		return waitForElements(elements);
	}

	/**
	 * Waits until the "verification points" are found. If any are not found, it
	 * fails the test if the wait time runs out.
	 * @return True/false depends on page loading
	 */
	public boolean waitForElements(final List<By> elements) {
		return waitForElements(elements, null);
	}
	/**
	 * Waits until the "verification points" are found. If any are not found, it
	 * fails the test if the wait time runs out.
	 * 
	 * @param elements elements to find on the web page
	 * @param failMessage failed message if elements cannot be found within TIMEOUT
	 * @return true, otherwise throw AssertionError
	 */
	public boolean waitForElements(final List<By> elements, String failMessage) {
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver wd) {
					return isPageLoaded(elements);
				}
			});
		} 
		catch (TimeoutException timeOutException) {
			if (failMessage != null) { 
				throw new AssertionError("Missing" + returnMissingElements(elements) + "; " +failMessage);
			}
			else {
				throw new AssertionError("Missing" + returnMissingElements(elements));
			}
		}
		return true;
	}
	
	/**
	 * Simple helper to determine if the browser has loaded the correct page.
	 */
	private boolean isPageLoaded(List<By> vps) {
		try {
			for (By point : vps) {
				if(!findOptionalWebElement(point).isDisplayed()) {
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
	 * @return Locators to verify that the browser has loaded the correct page.
	 */
	protected abstract List<By> verificationPoints();

	/**
	 * @return the isLoaded
	 */
	public boolean isLoaded() {
		return isLoaded;
	}

	/**
	 * @param isLoaded the isLoaded to set
	 */
	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}

	/**
	 * Wrap the webdriver findElement function to throw proper message
	 * @param webElementSelector webElementSelector 
	 * @return WebElement reference to the web object
	 */
	protected WebElement findWebElement (By webElementSelector){
		try {
			return webDriver.findElement(webElementSelector);	
		}
		catch (NoSuchElementException noSuchElement) {
			throw new AssertionError("Cannot find the web element with selector " + webElementSelector.toString());
		}		
	}
	
	/**
	 * This function is for finding an element that is optional for the test case. Meaning if the element is not found
	 * the test case can still continue executing. If you wanted the test case to stop executing when it is unable to find
	 * the element please use the findWebElement function
	 * @param webElementSelector
	 * @return the {@link WebElement} if one is found
	 * @throws NoSuchElementException will be thrown if element is not found
	 * @throws ElementNotVisibleException will be thrown if element is found but not displayed so it cannot be interacted with
	 */
	protected WebElement findOptionalWebElement (By webElementSelector) throws NoSuchElementException, ElementNotVisibleException{
		WebElement webElement = webDriver.findElement(webElementSelector);
		if (!webElement.isDisplayed()) {
			//show the web elements
			throw new ElementNotVisibleException(webElementSelector.toString());
		}
		return webElement;	
	}
	
	/**
	 * This checks to make sure the element is on the page. Useful if the only verification needed is if the element exists.
	 * @param webElementSelector
	 * @return if the element is found or not
	 */
	protected Boolean checkWebElementExists(By webElementSelector) {
		Boolean exists;
		try {
			webDriver.findElement(webElementSelector);
			exists = Boolean.TRUE;
		} catch (NoSuchElementException noSuchElement) {
			exists = Boolean.FALSE;
		} catch(ElementNotVisibleException notVisible) {
			exists = Boolean.FALSE;
		}
		return exists;
	}
	
	/**
	 * @param webElementSelector should match multiple elements
	 * @return list of all {@link WebElement} that match the specified {@link webElementSelector}
	 */
	protected List<WebElement> findWebElements (By webElementSelector) {
		try {
			return webDriver.findElements(webElementSelector);
		} catch (NoSuchElementException noSuchElement) {
			throw new AssertionError(webElementSelector);
		}
	}
	
	/**
	 * This function is for finding elements that are optional for the test case. Meaning if the elements are not found
	 * the test case can still continue executing. If you wanted the test case to stop executing when it is unable to find
	 * the elements please use the findWebElements function
	 * @param webElementSelector should match multiple elements
	 * @return list of all {@link WebElement} that match the specified {@link webElementSelector}
	 * @throws NoSuchElementException will be thrown if elements are not found
	 * @throws ElementNotVisibleException will be thrown if elements are found but if any of the elements
	 * are not displayed so they cannot be interacted with
	 */
	protected List<WebElement> findOptionalWebElements (By webElementSelector) throws NoSuchElementException, ElementNotVisibleException{
		try {
			List<WebElement> webElements = webDriver.findElements(webElementSelector);
			for(WebElement webElement: webElements) {
				if(!webElement.isDisplayed()) {
					throw new ElementNotVisibleException(webElementSelector.toString());
				}
			}
			return webElements;
		} catch (NoSuchElementException noSuchElement) {
			throw new AssertionError(webElementSelector.toString());
		}
	}

}
