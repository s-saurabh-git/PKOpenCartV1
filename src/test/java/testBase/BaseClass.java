package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
public class BaseClass
{ 
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeMethod (groups= {"Master", "Sanity", "Regression"})
	@Parameters({"os", "browser"})
	public void setUp(String os, String browser) throws MalformedURLException	
	{
		try
		{
		FileReader file=new FileReader(".\\src\\test\\resources\\config.properties");
		p=new Properties();
		p.load(file);
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
		
		logger=LogManager.getLogger(this.getClass());
		
		if (p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
		    RemoteWebDriver driver;

		    if (browser.equalsIgnoreCase("chrome"))
		    {
		        ChromeOptions options = new ChromeOptions();
		        options.setPlatformName(os.equalsIgnoreCase("Linux") ? "LINUX" : "WINDOWS");

		        driver = new RemoteWebDriver(
		                URI.create("http://localhost:4444/wd/hub").toURL(),
		                options
		        );
		    }
		    else if (browser.equalsIgnoreCase("edge"))
		    {
		        EdgeOptions options = new EdgeOptions();
		        options.setPlatformName(os.equalsIgnoreCase("Linux") ? "LINUX" : "WINDOWS");

		        driver = new RemoteWebDriver(
		                URI.create("http://localhost:4444/wd/hub").toURL(),
		                options
		        );
		    }
		    else if (browser.equalsIgnoreCase("firefox"))
		    {
		        FirefoxOptions options = new FirefoxOptions();
		        options.setPlatformName(os.equalsIgnoreCase("windows") ? "LINUX" : "WINDOWS");

		        driver = new RemoteWebDriver(
		                URI.create("http://localhost:4444/wd/hub").toURL(),
		                options
		        );
		    }
		    else
		    {
		        System.out.println("Invalid browser for remote");
		        return;
		    }

		    BaseClass.driver = driver;
		}

		
		if (p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(browser.toLowerCase())
			{
			case "chrome": driver=new ChromeDriver(); break;
			case "edge": driver=new EdgeDriver(); break;
			case "safari": driver=new SafariDriver(); break;
			default: System.out.println("invalid browser"); return;
			}
			
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL"));
		driver.manage().window().maximize();
		
		
	}
	
	
	@AfterMethod (groups= {"Master", "Sanity", "Regression"})
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomStringGen()
	{
		
		return new RandomStringGenerator.Builder().withinRange('a', 'z').build().generate(5);
	}
	
	public String randomNumberGen()
	{
		return new RandomStringGenerator.Builder().withinRange('0', '9').build().generate(10);
		
	}
	
	public String randomAlphaNumeric()
	{
		RandomStringGenerator generator1 =
	            new RandomStringGenerator.Builder()
	                    .withinRange('a', 'z')
	                    .build();
	    String aplhaString= generator1.generate(3);
	    
	    RandomStringGenerator generator2 =
	            new RandomStringGenerator.Builder()
	                    .withinRange('0', '9')
	                    .build();
	    String numString= generator2.generate(3);
	    
	    return (aplhaString+"@"+numString);
	
	}
	
	public String captureScreeenshot(String tname) throws IOException
	{
		File src=((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String fileName=tname+"_"+System.currentTimeMillis()+".png";
		File target=new File(".\\screenshots\\"+fileName);
		Files.copy(src.toPath(), target.toPath());
		
		return fileName;
	} 
}
