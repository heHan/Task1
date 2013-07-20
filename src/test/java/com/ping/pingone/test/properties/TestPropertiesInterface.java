package com.ping.pingone.test.properties;

import java.util.Properties;

public interface TestPropertiesInterface {
	static final String PINGONE_URL_PROPERTIES_KEY = "test.environment.baseUrl";
	static final String PINGONE_LOGIN_USER_EMAIL_KEY = "test.environment.login.email";
	static final String PINGONE_LOGIN_USER_PASSWORD_KEY = "test.environment.login.password";
	static final String CANNOT_LOAD_PROPERTY = "Cannot load property: ";
	
	public Properties getProperties();
	public String[] getKeys();
}
