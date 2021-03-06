package com.ping.pingone.browser;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Browser enum class, browser has a name and a capability
 * 
 * @author hehan
 */
public enum Browser {
	/*
	 *  create browsers with [NAME][VERSION][PLATFORM]
	 *  the version and platform can be used to select browser from a grid environment
	 */
	FIREFOX ("firefox", setCapability(DesiredCapabilities.firefox(), "", "")),
	/*
	 * CHROME ("chrome", setCapability(DesiredCapabilities.chrome(), "", "")), need to setup the chrome driver
	 */
	IE ("ie", setCapability(DesiredCapabilities.internetExplorer(), "", "")),
	SAFARI ("safar", setCapability(DesiredCapabilities.safari(), "", ""));
	
	private String browserName;
	private DesiredCapabilities capability;
	
	public static final String defaultBrowser = "firefox";
	
	/**
	 * A mapping between the country and its corresponding Browser to facilitate lookup by browserName
	 */
	private static Map<String, Browser> browserNameToBrowserMapping;
	
	private Browser(String browserName, DesiredCapabilities capability) {
		this.browserName = browserName;
		this.capability = capability;
	}
	/**
	 * Setup the capability with the version
	 * @param capability the capability to add property to
	 * @param version the version of the browser
	 * @param platform the platform of the browser to run on, default is "ANY"
	 * @return capability has updated with version and platform
	 */
	private static DesiredCapabilities setCapability(DesiredCapabilities capability, String version, String platform) {
		capability.setVersion(version);
		if (platform != "") {
			capability.setPlatform(Platform.valueOf(platform));
		}
		return capability;
	}
	
	/**
	 * Get the browser name 
	 * @return the name of the browser
	 */
	public String getBrowserName() {
		return this.browserName;
	}
	
	/**
	 * Get the capability for the browser (selenium term)
	 * @return the capability of the browser to run on
	 */
	public DesiredCapabilities getCapability() {
		return this.capability;
	}
	/**
	 * Get the browser associated with the name.
	 * @param browserName the name of the browser
	 * @return the browser object
	 */
	public static Browser getBrowser (String browserName) {
		if (browserNameToBrowserMapping == null) {
			initMapping();
		} 
		return browserNameToBrowserMapping.get(browserName);
	}
	
	/**
	 * Get the browser associated with the name.
	 * @param browserName the name of the browser
	 * @param defaultBrowser the default browser if the browser name is not in the system
	 * @return the browser object
	 */
	public static Browser getBrowser (String browserName, Browser defaultBrowser) {
		Browser result = getBrowser(browserName);
		if (result == null) {
			return defaultBrowser;
		} 
		return result;
	}
	
	/**
	 * initial the map
	 */
	private static void initMapping() {
		browserNameToBrowserMapping = new HashMap<String, Browser>();
		for (Browser browser : values()) {
			browserNameToBrowserMapping.put(browser.browserName, browser);
		}
	}
}
