package com.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.java.ExcelUtility;
import com.java.ExtentReportUtility;
import com.java.Inc_Utility;
import com.java.MethodLibrary;
import com.java.WO_utility;
import com.listeners.ExtentITestListenerClassAdapter;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;

@Listeners(ExtentITestListenerClassAdapter.class)
public class TC_RemedyWorkOrder extends MethodLibrary {
 
	private static String Path_TestData = ".//Data//"; 
    private static String File_TestData = "Remedy_GetData.xlsx";
    static WebDriver driver;
    private static Properties properties;
    static ExtentTest logger;
    static ExtentReports extent;
	
    
	
    @Test(dependsOnMethods = "openRemedyTest",retryAnalyzer= com.listeners.Retry.class, enabled = true, priority = 2)
    public void test_createWorkOrder(ITestContext context) {
	 
    	try{
	 logger = ExtentITestListenerClassAdapter.getLogger();
	 ExcelUtility.setExcelFile(Path_TestData + File_TestData, "WO_creation");
		int wo_count = ExcelUtility.getNumberofIncidents();
		//System.out.println(""+ incident_count);
		for(int i = 1; i<= wo_count;i++ ) {
					
					ArrayList<String> rowdata = ExcelUtility.getRowData(i);
					System.out.println(" "+rowdata);
					Thread.sleep(2000);
					String woNum = WO_utility.create_WO(driver,rowdata, i,logger);
					break;
		}
    }
    	catch(UnhandledAlertException e) {
			Assert.assertTrue(false);
			System.out.println("User is currently connected from another machine.");
			logger.error("User is currently connected from another machine. \n"+e.getAlertText());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Test(dependsOnMethods = "openRemedyTest",retryAnalyzer= com.listeners.Retry.class, enabled = false, priority = 2)
	public void test_searchWorkOrder(ITestContext context) throws Exception {
				
			ExcelUtility.setExcelFile(Path_TestData + File_TestData, "WO_search");
			logger = ExtentITestListenerClassAdapter.getLogger();		
					int incident_count = ExcelUtility.getNumberofIncidents();
					for(int i = 1; i<= incident_count;i++ ) {
						
						String woId = ExcelUtility.getCellData(i, 0);
						System.out.println("Id is: "+woId);
						//remedyObj.searchIncident(driver,incId);
						Thread.sleep(2000);
						WO_utility.search_WO(driver, woId,logger);
					
					}
				}
		  
				
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
		
	
	@Test(priority = 1,retryAnalyzer= com.listeners.Retry.class)
	public void openRemedyTest() throws FileNotFoundException, IOException{
		
		openRemedy(driver,"username1","pwd1");
	}
		
   @AfterTest(enabled=false)
   public void teardown() {
   	try {
			Thread.sleep(5000);
			MethodLibrary.logout(driver);
			driver.quit();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
   }
		
 }