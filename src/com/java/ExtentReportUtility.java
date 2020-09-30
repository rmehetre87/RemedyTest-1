package com.java;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.ITestContext;
import org.testng.ITestNGListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


public class ExtentReportUtility implements ITestNGListener{

	 static ExtentReports extent;
	 static ExtentTest logger;
	 static WebDriver driver;
		 
	 	public static ExtentReports reportSetup(String TCname) {
	 		
	 		if(logger==null) {
	 			 				
	 		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");  
			LocalDateTime now = LocalDateTime.now();
	 		String filename = "./Reports/Report_"+TCname+"_"+dtf.format(now)+".html";
	 		ExtentHtmlReporter htmlReporter  = new ExtentHtmlReporter(filename);
	 		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
	        htmlReporter.config().setChartVisibilityOnOpen(false);
	        htmlReporter.config().setTheme(Theme.STANDARD);
	        htmlReporter.config().setDocumentTitle("Remedy Test Report");
	        htmlReporter.config().setEncoding("utf-8");
	        htmlReporter.config().setReportName("Remedy Test Automation Report");
	        
			extent = new ExtentReports();
		    
		    extent.attachReporter(htmlReporter);
		    
		    extent.setSystemInfo("Browser", "Chrome 83.0");
		    extent.setSystemInfo("Automation Tester", "Rahul Mehetre");
		    extent.setSystemInfo("Organization", "TUK-TechM");
		    
	 		}
		    return extent;
		    
	 	}
	 

		public static void tearDown() throws IOException 
		{
			extent.flush();
			//driver.quit();
			
		}


		
	
}
