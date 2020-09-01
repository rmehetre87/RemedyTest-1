package com.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.java.ExcelUtility;
import com.java.ExtentReportUtility;
import com.java.Inc_Utility;
import com.java.MethodLibrary;
import com.listeners.ExtentITestListenerClassAdapter;

//@Listeners({com.listeners.RetryListener.class,ExtentITestListenerClassAdapter.class})

public class TC_RemedyIncident_Update extends MethodLibrary{
	
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
  
    @AfterTest(enabled = true)
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
		
		openRemedy(driver,"username1","pwd1");
	}
   
	
    @DataProvider(name="dp_UpdateInc")
    public Object[] dp_UpdateInc() throws Exception{
    	
    	int n = 0;
    	ArrayList<String> rowdata = null;
    	ExcelUtility.setExcelFile(Path_TestData + File_TestData, "Update_Inc");
		int incident_count = ExcelUtility.getNumberofIncidents();
		ArrayList<String>[] al = new ArrayList[incident_count];
		
		for(int i = 1; i<= incident_count;i++ ) {
			
			rowdata = ExcelUtility.getRowData(i);
			System.out.println(""+rowdata);
			al[n]=rowdata;
			n++;
		}
    	
		for(Object o : al){
			
			System.out.println("al: "+o);
		}
		
    	return al;
    }

    
    @Test(dataProvider="dp_UpdateInc",retryAnalyzer= com.listeners.Retry.class, enabled = true, priority = 4)	
	public void test_updateInc(ArrayList<String> rowdata,ITestContext context) throws Exception {
			
    		logger = ExtentITestListenerClassAdapter.getLogger();
    		String incId = rowdata.get(0);
    		
			System.out.println("Id is: "+incId);
			
			Thread.sleep(5000); 
			
			Inc_Utility.searchIncident(driver, incId,logger);
			
			Thread.sleep(5000);
			
			Inc_Utility.updateIncident(driver,logger,rowdata);
			
		}


}
