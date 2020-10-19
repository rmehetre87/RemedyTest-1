package com.test;

import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.java.ExcelUtility;
import com.java.ExtentReportUtility;
import com.java.Inc_Utility;
import com.java.MethodLibrary;
import com.listeners.ExtentITestListenerClassAdapter;

@Listeners({com.listeners.RetryListener.class,ExtentITestListenerClassAdapter.class})
	
public class TC_RemedyIncident_Add_VenderTask extends MethodLibrary{
	
	static WebDriver driver;
	static ExtentReports extent;
	static ExtentTest logger;
	private static String Path_TestData = ".//Data//"; 
    private static String File_TestData = "Remedy_GetData.xlsx";
    
    private static Properties properties;
    static Inc_Utility obj;
    int counter =1;
    
		
	@BeforeTest
	public void beforeTest(ITestContext context) {
		properties = new Properties();	
		try {
			properties.load(new FileReader(".//Data//ObjectReository.properties"));
			driver = openBrowser("Chrome");
			context.setAttribute("webDriver", driver);
			extent = ExtentReportUtility.reportSetup("TC_RemedyIncident_Add_VenderTask");
			context.setAttribute("extent", extent);
			openRemedy(driver,"username1","pwd1");
		}
		catch(Exception e) {

			e.printStackTrace();
		}
	}
  
     
	
    @Test
    public void Test_addVendorTask() throws Exception{
    	
    	ArrayList<String> details = null;
		ExcelUtility.setExcelFile(Path_TestData + File_TestData, "AddVandorTask");
		
		int incident_count = ExcelUtility.getNumberofIncidents();
		System.out.println("inc count"+incident_count);
	    
		details = ExcelUtility.getRowData(1);
		
		String incId = details.get(1);
		
		logger = ExtentITestListenerClassAdapter.getLogger();
		Inc_Utility.searchIncident(driver, incId, logger);
		
		for(int i = 1; i<= incident_count; i++ ) {

			details = ExcelUtility.getRowData(i);
			if(details.get(0).equalsIgnoreCase("Yes")){
				
					Inc_Utility.addRelationshipCI(driver, logger, details);
				
					Inc_Utility.addVendorTask(driver, logger, details);
			
				}
    			}
		}
    
    @AfterTest(enabled = true)
    public void teardown() {
    	try {
			Thread.sleep(5000);
			
			MethodLibrary.logout(driver);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
    	
    }
    
    
    
    }









