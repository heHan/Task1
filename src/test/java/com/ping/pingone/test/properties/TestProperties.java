package com.ping.pingone.test.properties;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * wrapper class for properties loader
 * 
 * @author hehan
 */
public class TestProperties {
	private static final Logger log = Logger.getLogger(TestProperties.class); 
	private String propertiesFileName;
	private Properties properties;
	
	/**
	 * Creates properties class, loads property file immediately, overrides properties from system
	 * @param propertiesFileName Where the property file is defined
	 */
	public TestProperties(String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
		load();
	}
	
	/**
	 * Gets the properties object
	 * @return the properties object
	 */
	public Properties getProperties () {
		return this.properties;
	}
	
	/**
	 * Loads the properties file and overrides the properties from the system
	 */
	protected void load() {
		InputStream inputStream = null;
		properties = new Properties();
		log.debug("loading: " + propertiesFileName);

		try {
			inputStream = TestProperties.class.getClassLoader().getResourceAsStream(propertiesFileName);
			properties.load(inputStream);
		}
		catch (IOException exception) {
			final String message = "Can't read the .properties file '" + propertiesFileName + "', try checking the permissions";
			log.fatal(message, exception);
		}
		finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				}
				catch (IOException exception) {
					log.fatal(exception, exception);
				}
			}
		}
	}
}
