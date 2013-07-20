package com.ping.pingone.tests;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.ping.pingone.pages.HomePage;
import com.ping.pingone.pages.IdpApplicationLogin;
import com.ping.pingone.pages.LoginPage;
import com.ping.pingone.pages.MyApplicationsPage;
import com.ping.pingone.test.properties.TestPropertiesInterface;
import com.ping.pingone.webdriver.test.AbstractWebDriverBase;

/**
 * Test the SSO workflow
 * @author HenryH
 *
 */
public class TestSSO extends AbstractWebDriverBase{
	static final String TEST_APPLICATION_NAME = "SampleAPSEnabledApp";
	static final String CANNOT_VERIFY_TOKEN_MESSAGE = "Cannot verify token using tokenId obtained, there was exception thrown.";
	static final String TOKEN_LAST_NAME_KEY = "lname";
	static final String TOKEN_LAST_NAME_EXPECTED_VALUE = "test_lname";
	static final String TOKEN_FIRST_NAME_KEY = "fname";
	static final String TOKEN_FIRST_NAME_EXPECTED_VALUE = "test_fname";
	static final String TOKEN_DEPARTMENT_KEY = "department";
	static final String TOKEN_DEPARTMENT_EXPECTED_VALUE = "test_department";
	static final String TOKEN_SUBJECT_KEY = "subject";
	static final String TOKEN_PINGONE_FROM_SUBJECT_KEY = TOKEN_SUBJECT_KEY;
	static final String TOKEN_PINGONE_SUBJECT_KEY = TOKEN_SUBJECT_KEY;
	static final String TOKEN_SUBJECT_EXPECTED_VALUE = "testuser1@testidp.connect.pingidentity.com";	
	static final String TOKEN_PINGONE_NAMEID_FORMAT_KEY = "pingone.nameid.format";
	static final String TOKEN_PINGONE_NAMEID_FORMAT_EXPECTED_VALUE = "urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress";
	static final String TOKEN_PINGONE_AUTHN_CONTEXT_KEY = "pingone.authn.context";
	static final String TOKEN_PINGONE_AUTHN_CONTEXT_EXPECTED_VALUE = "urn:oasis:names:tc:SAML:2.0:ac:classes:Password";
	
	@Test (description = "")
	public void testAddNewApplication() {
		String pingOneUrl = testProperties.getProperties().getProperty(TestPropertiesInterface.PINGONE_URL_PROPERTIES_KEY);
		String loginEmail = testProperties.getProperties().getProperty(TestPropertiesInterface.PINGONE_LOGIN_USER_EMAIL_KEY);
		String loginPassword = testProperties.getProperties().getProperty(TestPropertiesInterface.PINGONE_LOGIN_USER_PASSWORD_KEY);
		if (pingOneUrl == null) {
			throw new AssertionError(TestPropertiesInterface.CANNOT_LOAD_PROPERTY + TestPropertiesInterface.PINGONE_URL_PROPERTIES_KEY);
		}
		if (loginEmail == null) {
			throw new AssertionError(TestPropertiesInterface.CANNOT_LOAD_PROPERTY + TestPropertiesInterface.PINGONE_LOGIN_USER_EMAIL_KEY);
		}
		if (loginPassword == null) {
			throw new AssertionError(TestPropertiesInterface.CANNOT_LOAD_PROPERTY + TestPropertiesInterface.PINGONE_LOGIN_USER_PASSWORD_KEY);
		}
		reporterLog("Steps");
		reporterLog("1. Load pingone login page: " + pingOneUrl);
		webDriver.get(pingOneUrl);
		reporterLog("2. Login in with account: " + loginEmail + "/" + loginPassword);
		LoginPage loginPage = new LoginPage(webDriver);
		loginPage.enterEmail(loginEmail);
		loginPage.enterPassword(loginPassword);
		HomePage homePage = loginPage.clickLogIn();
		reporterLog("3. Click my application tab on home page");
		MyApplicationsPage myApplicationsPage =  homePage.clickMyApplications();
		reporterLog("4. Click test button on application " + TEST_APPLICATION_NAME);
		myApplicationsPage.clickTestConnectionButtonByApplicationName(TEST_APPLICATION_NAME);
		reporterLog("5. Click start SSO link on popup");
		IdpApplicationLogin idpApplicationLogin = myApplicationsPage.clickStartSSOAndWaitForRedirect(webDriver.getWindowHandle());
		reporterLog("6. Wait for the idp appication to load; and click login with default user/pass");
		idpApplicationLogin.clickLoginButton();
		String tokenId = getTokenIdfromUrl(webDriver.getCurrentUrl());
		retrieveUserTokenAndVerify(tokenId);
	}

	/**
	 * Retrieve the tokenId from the redirect url
	 * @param url the url to parse, example //https://myapp.example.com/sso.php?tokenid=ID50b3a0284d5f0a392033249b04ace42b333ff4e6afc4fcdb01&agentid=3b1ba308
	 * @return tokenid value
	 */
	private String getTokenIdfromUrl(String url){
		try {
			List<NameValuePair> params = URLEncodedUtils.parse(new URI(url), "UTF-8");
			for (NameValuePair param: params){
				if (param.getName().equals("tokenid")){
					return param.getValue();
				}
			}
		} catch (URISyntaxException e) {
			reporterLog(e.toString());
		}
		throw new AssertionError("There is no tokenId in the url: " + url);
	}

	private void retrieveUserTokenAndVerify(String tokenId) {
		reporterLog("User tokenId is " + tokenId);
		DefaultHttpClient client = new DefaultHttpClient();
		/*
		 * The following url is used to retrieve the token from PingOne
		 */
		String txsUrl = "https://test-sso.connect.pingidentity.com/sso/TXS/2.0/1/" + tokenId;

		/*
		 * restUsername for your account can be found under the Account page in your APS account.
		 * restSecret is also set on the Acccount page.  By default it is set to Password1.
		 */
		String restUsername = "31c7d923-3a92-460a-817b-280e0fd76475";
		String restSecret = "Password1";

		/*
		 * The authorization header is base64 encoded string of the restUsername and restSecret.
		 */
		String authHeader = new String(Base64.encodeBase64((restUsername + ":" + restSecret).getBytes()));

		HttpPost httpPost = new HttpPost(txsUrl);
		httpPost.addHeader("Authorization", authHeader);

		try
		{
			HttpResponse httpResponse = client.execute(httpPost);
			HttpEntity responseEntity = httpResponse.getEntity();
			if(responseEntity!=null) {
				String responseString = EntityUtils.toString(responseEntity);				
				JSONObject result = new JSONObject(responseString);
				String verificationResultMessage = null;
				if (result.getString(TOKEN_LAST_NAME_KEY)!= TOKEN_LAST_NAME_EXPECTED_VALUE){
					verificationResultMessage = TOKEN_LAST_NAME_KEY;
				} 
				Reporter.log(responseString, true);
			}

			/*
			 * You should write code to validate the response.
			 * 
			 * The token will be returned in the response body in JSON format.  For example:
			 * 
			 * {"lname":"test_lname",
			 *  "pingone.nameid.format":"urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress",
			 *  "pingone.authn.context":"urn:oasis:names:tc:SAML:2.0:ac:classes:Password",
			 *  "department":"test_department",
			 *  "subject":"testuser1@testidp.connect.pingidentity.com",
			 *  "pingone.assertion.id":"KsvzPyqAzUdJmLUijGx9.Eu69d2",
			 *  "pingone.authninstant":"1374008723000",
			 *  "pingone.idp.id":"62ca2abd-ed4e-4aa4-91df-429b0ee606e4",
			 *  "pingone.nameid.qualifier":"",
			 *  "pingone.saas.id":"429d6cbe-73a6-4903-9734-690798ef0dc7",
			 *  "fname":"test_fname",
			 *  "pingone.subject":"testuser1@testidp.connect.pingidentity.com",
			 *  "pingone.subject.from.idp":"testuser1@testidp.connect.pingidentity.com"
			 * }
			 */
		}
		catch (Exception e)
		{
			reporterLog("Exception is caught during parsing http response; " + e.getMessage());
			throw new AssertionError(CANNOT_VERIFY_TOKEN_MESSAGE);
		}
		finally
		{		
			httpPost.releaseConnection();
		}	
	}
}
