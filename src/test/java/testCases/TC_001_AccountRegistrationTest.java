package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC_001_AccountRegistrationTest extends BaseClass
{
	@Test(groups = {"Master","Regression"})
	public void verify_account_registration()
	{
		logger.info("***Started TC_001_AccountRegistrationTest ***");
		try {
		HomePage hp=new HomePage(driver);
		hp.clickMyAccount();
		hp.clickRegister();
		
		logger.info("Providing customer details");
		AccountRegistrationPage ap=new AccountRegistrationPage(driver);
		ap.setFirstname(randomStringGen());
		ap.setLastname(randomStringGen());
		ap.setEmailId(randomStringGen()+"@gmail.com");
		ap.setTelephone(randomNumberGen());
		
		String finalPassword=randomAlphaNumeric();
		ap.setPassword(finalPassword);
		ap.setConfirmPassword(finalPassword);
		
		ap.setPrivacyPolicy();
		ap.clkContinue();
		
		logger.info("Validating expected message..");
		String confirmMsg=ap.getConfirmationMsg();
		if(confirmMsg.equals("Your Account Has Been Created!"))
		{
			logger.info("Test Passed");
			Assert.assertTrue(true);
		}
		else 
		{
			logger.error("Test Failed");
			Assert.assertTrue(false);
		}
		}
		catch (Exception e)
		{
			Assert.fail();
		}
		logger.info("***Finished TC_001_AccountRegistrationTest ***");
	}

	
}
