package com.ping.pingone.webdriver.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.ping.pingone.browser.Browser;
import com.ping.pingone.test.properties.TestProperties;


/**
 * The base class require for any tests using web driver
 * We can implement {@link AbstractWebDriverEventListener} to have some default behaviors on certain event,
 * for example to handle exceptions 
 * @author hehan
 *
 */
public abstract class AbstractWebDriverBase {
	static final String PROPERTIES_FILE_NAME = "webTest.properties";
	protected WebDriver webDriver = null;
	protected TestProperties testProperties = new TestProperties(PROPERTIES_FILE_NAME);
	
	private static final String REPORT_PATH = "target/surefire-reports/html/";
	private static final String SCREENSHOT = "screenshots/";

	private static final Logger log = Logger.getLogger(AbstractWebDriverBase.class);

	/**
	 * This method creates a WebDriver session for the test methods in each test class
	 * @param locale
	 * @param testEnvironment
	 * @param browser configured in testng.xml to start a browser to run test
	 */
	// Browser object can be expanded to handle browser versions and platforms
	// could also initialize a remote webdriver through selenium grid
	@Parameters({"browser"})
	@BeforeMethod(alwaysRun = true)
	public final void setup(@Optional("firefox") String browser) {
		Browser testBrowser = Browser.getBrowser(browser, Browser.FIREFOX);
		switch (testBrowser) {
			case SAFARI: webDriver = new SafariDriver(testBrowser.getCapability());
			case IE: webDriver = new InternetExplorerDriver(testBrowser.getCapability());
			default: webDriver = new FirefoxDriver(testBrowser.getCapability());
		}
		if (webDriver != null) {
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		else {
			log.debug("webDriver is not be created successfully.");
		}
		// turn off the html skipping for reportng format 
		System.setProperty("org.uncommons.reportng.escape-output", "false");
	}
	/**
	 * Shut down the wbeDriver session after each method run and capture screenshot on failure
	 * 
	 * @param testResult current test method result object
	 */
	@AfterMethod(alwaysRun = true)
	public final void shutdownWebDriver(ITestResult testResult){
		if (webDriver != null){
			// take screen
			if (testResult.getStatus() == ITestResult.FAILURE) {
				String screenshotFile = takeScreenshot();
				String link2Screenshot = "<a href=\"" + SCREENSHOT + screenshotFile + "\" target=\"_blank\"> SCRENNSHOT</a><br />";
				Reporter.setCurrentTestResult(testResult);
				Reporter.log(link2Screenshot);
			}
			webDriver.quit();
		}
	}
	
	/**
	 * Save the screenshot for current session
	 * @param webDriver the WebDriver
	 * @return the name of the screenshot
	 */
	private String takeScreenshot() {
		File screenshotFile = null;
		String fileName = "";
		try {
			screenshotFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
		} catch (ClassCastException e) {
			Reporter.log("Cannot take screenshot for this webdriver");
		} catch (SessionNotFoundException e) {
			Reporter.log("WebDriver is terminated!", 3);
		}
		try {
			fileName = webDriver.getWindowHandle() +"-"+ getCurrentTime() + ".jpg";
			FileUtils.copyFile(screenshotFile, new File(REPORT_PATH + SCREENSHOT + fileName));
		} catch (IOException exception) {
			Reporter.log(exception.getLocalizedMessage(), 3);
		}
		return fileName;
	}
	
	/**
	 * Return the current time in String format
	 * @return current time in yyyy-MM-dd-HH-mm-ss format
	 */
	public String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String currentTime = String.valueOf(dateFormat.format(new Date()));
		return currentTime;
	}
	
	public void reporterLog(String message) {
		Reporter.log(message + "<br>");
	}
}
