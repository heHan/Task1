package com.ping.pingone.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
/**
 * New application wizard # two page object
 * @author HenryH
 *
 */
public class NewApplicationWizardTwo extends MyApplicationsPage {
	protected static final By ENABLE_THROUGH_PINGON = By.xpath("//li[@class='tab1 last']/a");
	protected static final By DOMAIN_NAME = By.id("domainName");
	protected static final By APPLICATION_URL = By.xpath("//input[@class='ping-textinput url']");
	
	public NewApplicationWizardTwo(WebDriver webDriver) {
		super(webDriver);
	}

	@Override
	protected List<By> verificationPoints() {
		List<By> verificationPoints = new ArrayList<By>();
		verificationPoints.add(ENABLE_THROUGH_PINGON);
		verificationPoints.add(CONTINUE_NEXT_BUTTON);
		return verificationPoints;
	}
	
	public void chooseSAMLEnabled(){
		findWebElementAndClick(ENABLE_THROUGH_PINGON);
		// javascript will also work here
//		WebElement button = findWebElement(ENABLE_THROUGH_PINGON);
//		if (webDriver instanceof JavascriptExecutor) {
//	        ((JavascriptExecutor)webDriver).executeScript("arguments[0].onclick()", button);
//	    }
	}
	
	/**
	 * Enter the domain name
	 * @param name
	 */
	public void enterDomainName(String name) {
		findWebElementAndType(DOMAIN_NAME, name);
	}
	
	/**
	 * Enter the application url
	 * @param url example: http://myapp.example.com/sso.php
	 */
	public void enterApplicationUrl(String url) {
		findWebElementAndType(APPLICATION_URL, url);
	}
	
	/**
	 * Click the continue button and return the next page object
	 * @return wizard # 3
	 */
	public NewApplicationWizardThree clickContinueButton() {
		findWebElementAndClick(CONTINUE_NEXT_BUTTON);
		return new NewApplicationWizardThree(webDriver);
	}
}
