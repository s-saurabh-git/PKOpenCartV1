package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage
{
	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	@FindBy(xpath="//input[@id='input-email']")
	WebElement txtEmail;
	
	@FindBy(id="input-password")
	WebElement txtPwd;
	
	@FindBy(xpath="//input[@value='Login']")
	WebElement btnLogin;
	
	public void enterEmaild(String emailId)
	{
		txtEmail.sendKeys(emailId);
	}
	
	public void enterPassword(String password)
	{
		txtPwd.sendKeys(password);
	}
	
	public void clickLogin()
	{
		btnLogin.click();
	}

}
