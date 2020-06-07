package com.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.java.ExcelUtility;
import com.java.ExtentReportUtility;
import com.java.Inc_Utility;
import com.java.MethodLibrary;
import com.listeners.ExtentITestListenerClassAdapter;

//@Listeners({com.listeners.RetryListener.class,ExtentITestListenerClassAdapter.class})
public class TC_RemedyIncident_Search extends MethodLibrary{
	
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
			extent = ExtentReportUtility.reportSetup();
			context.setAttribute("extent", extent);
		}
		catch(Exception e) {

			e.printStackTrace();
		}
	}
		
  
    @AfterTest(enabled = false)
    public void teardown() {
    	try {
			Thread.sleep(5000);
			MethodLibrary.logout(driver);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    
    @Test(priority = 1,retryAnalyzer= com.listeners.Retry.class,enabled = true)
	public void openRemedyTest() throws FileNotFoundException, IOException{
		
		openRemedy(driver,"username2","pwd2");
	}
    
    
        
    @Test(retryAnalyzer= com.listeners.Retry.class,enabled = true,priority = 3)	
	public void test_searchIncDetails() throws Exception {
		
		logger = ExtentITestListenerClassAdapter.getLogger();
		ArrayList<String> details = null;
		
		ExcelUtility.setExcelFile(Path_TestData + File_TestData, "Search_Inc");
		
		int incident_count = ExcelUtility.getNumberofIncidents();
		for(int i = 1; i<= incident_count;i++ ) {
			
			String incId = ExcelUtility.getCellData(i, 0);
			System.out.println("Id is: "+incId);
			if(incId.length()>1){
			//remedyObj.searchIncident(driver,incId);
			Thread.sleep(5000);
			details =	Inc_Utility.searchIncidentdtails(driver, incId,logger);
			System.out.println("for"+i+"round "+details.toString());
			Inc_Utility.saveDetails(details, i);
			}
			else{
				System.out.println("Provide Incident Id:" + incId);
			}
			
		}
		
	}	
	
    }