package org.themoviedb.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class TestBase{
	
	private static Properties properties;
	private String baseDir=System.getProperty("user.dir");
	
	public TestBase() {
		
		try {
			properties=new Properties();
			FileInputStream fileInputStream=new FileInputStream(baseDir+"/src/main/java/org/themoviedb/config/config.properties");
			System.setProperty("webdriver.chrome.driver", baseDir+"/driver/chromedriver.exe");	
			properties.load(fileInputStream);
		}
		catch(IOException e) {
		e.printStackTrace();
		}
		
	}
	
	public String getBaseUri() {
		String baseURI=properties.getProperty("baseURI");
		return baseURI;
	}
	public String getApproveRequestTokenBaseUri() {
		String approveRequestTokenBaseURI=properties.getProperty("approveRequestTokenBaseURI");
		return approveRequestTokenBaseURI;
	}
	public String getApiKey() {
		String apiKey=properties.getProperty("apiKey");
		return apiKey;
	}
	public String getReadAccessToken() {
		String readAccessToken=properties.getProperty("readAccessToken");
		return readAccessToken;
	}
	public String getUserName() {
		String userName=properties.getProperty("userName");
		return userName;
	}
	public String getPassword() {
		String password=properties.getProperty("password");
		return password;
	}
	
	
}