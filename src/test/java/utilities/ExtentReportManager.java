package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports reports;
	public ExtentTest test;
	
	String reportName;
	
	public void onStart(ITestContext context) 
	{
		//set report name at runtime
		String timeStamp=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm.ss.SSS"));
		reportName="Execution-Report"+timeStamp+".html";
		
		sparkReporter=new ExtentSparkReporter(".\\reports\\"+reportName);
		sparkReporter.config().setDocumentTitle("Opencart Automation Report");
		sparkReporter.config().setReportName("Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);
		
		reports=new ExtentReports();
		reports.attachReporter(sparkReporter);
		reports.setSystemInfo("Tester", System.getProperty("user.name"));
		reports.setSystemInfo("Appplication", "Opencart");
		reports.setSystemInfo("Environment", "Betemp");
		
		String os=context.getCurrentXmlTest().getParameter("os");
		reports.setSystemInfo("OS", os);
		
		String br=context.getCurrentXmlTest().getParameter("browser");
		reports.setSystemInfo("Browser", br);
		
		List<String> allGroups=context.getCurrentXmlTest().getIncludedGroups();
		if(!allGroups.isEmpty())
		{
			reports.setSystemInfo("Groups", allGroups.toString());
		}
		
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test=reports.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, result.getName()+" successfully executed");
	}
	public void onTestFailure(ITestResult result)
	{
		test=reports.createTest(result.getTestClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName()+" failed");
		
		try 
		{
			String imgPath=new BaseClass().captureScreeenshot(result.getName());
			test.addScreenCaptureFromPath(imgPath);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void onFinish(ITestContext context)
	{
		reports.flush();
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+reportName;
		File extentReport = new File(pathOfExtentReport);
		
		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
