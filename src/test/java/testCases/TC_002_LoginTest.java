package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass
{
	@Test(groups = {"Master","Sanity"})
	public void verifyLogin()
	{
		try 
		{
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		LoginPage lp=new LoginPage(driver);
		lp.enterEmaild(p.getProperty("email"));
		lp.enterPassword(p.getProperty("password"));
		lp.clickLogin();
		
		MyAccountPage acc=new MyAccountPage(driver);
		boolean textStaus=acc.myAccountTitleVerification();
		Assert.assertTrue(textStaus);
		}
		catch (Exception e)
		{
			Assert.fail();
		}
		
	}

}
