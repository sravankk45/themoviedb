package org.themoviedb.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.themoviedb.base.TestBase;

public class ApproveRequestTokenPage extends TestBase{
	
		//Page Factory
	
		@FindBy(xpath="//h2[contains(text(),'Login')]")
		WebElement login;
		
		@FindBy(xpath="//input[@id='username']")
		WebElement userName;
		
		@FindBy(xpath="//input[@id='password']")
		WebElement password;
		
		@FindBy(xpath="//div[@class='flex']/input")
		WebElement submmit;
		
		@FindBy(xpath="//input[@name='submit']")
		WebElement approve;
		
		WebDriver driver = new ChromeDriver();
		
		//Initializing the Page Objects:
		public ApproveRequestTokenPage(){
			PageFactory.initElements(driver, this);
		}
		
		
		public void launchApproveTokenRequestPage(String uri) {
			driver.get(uri);
		}
		public void clickLoginButton() {
			login.click();
		}
		public void enterUserName(String strUserName) {
			userName.sendKeys(strUserName);
		}
		public void enterPassword(String strPassword) {
			password.sendKeys(strPassword);
		}
		public void clickSubmitButton() {
			submmit.click();
		}
		public void clickApproveButton() {
			approve.click();
		}
		public void closeBrowser() {
			driver.quit();
		}

}
