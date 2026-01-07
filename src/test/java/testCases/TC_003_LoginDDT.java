package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC_003_LoginDDT extends BaseClass
{
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DataDriven")
	public void verify_loginDDT(String usename, String pwd, String exp)
	{
		logger.info("==== Started TC_003_LoginDDT extends BaseClass===");
		try 
		{
		//Home Page
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//Login Page
		LoginPage lp=new LoginPage(driver);
		lp.enterEmaild(usename);
		lp.enterPassword(pwd);
		lp.clickLogin();
		
		//MyAccountPage
		MyAccountPage mp=new MyAccountPage(driver);
		boolean targetPage=mp.myAccountTitleVerification();
		
		if(exp.equalsIgnoreCase("valid"))
		{
			if(targetPage==true)
			{
				mp.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		
		if(exp.equalsIgnoreCase("invalid"))
		{
			if (targetPage==true)
			{
				mp.clickLogout();
				Assert.assertTrue(false);
				
			} 
			else
			{
				Assert.assertTrue(true);
			}
		}
		}
		catch (Exception e)
		{
			Assert.fail("An exception occurred: " + e.getMessage());

		}
		logger.info("==== Finished TC_003_LoginDDT extends BaseClass===");
	}
	

}
