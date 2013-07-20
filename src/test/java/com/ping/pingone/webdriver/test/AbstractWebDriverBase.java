package com.ping.pingone.webdriver.test;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
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
	//protected static RemoteWebDriver webDriver;
	// logger
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
	}
	/**
	 * Shut down the wbeDriver session after each method run
	 */
	@AfterMethod(alwaysRun = true)
	public final void shutdownWebDriver(){
		if (webDriver != null){ 
			webDriver.quit();
		}
	}
}
