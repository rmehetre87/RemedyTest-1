package com.test;

import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
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
import com.java.NetcoolUtility;
import com.listeners.ExtentITestListenerClassAdapter;

@Listeners({com.listeners.RetryListener.class,ExtentITestListenerClassAdapter.class})
public class TC_Netcool_SearchDetails extends MethodLibrary {

	static WebDriver driver;
	static ExtentReports extent;
	static ExtentTest logger;
	private static String Path_TestData = ".//Data//"; 
	private static String File_TestData = "Remedy_GetData.xlsx";

	private static Properties properties;
	
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
	
	
	@Test(priority = 1,enabled=true,retryAnalyzer= com.listeners.Retry.class)
	public void openNetcoolTest() throws FileNotFoundException, IOException{
		
		openNetcool(driver, "nc_username", "nc_password");
		
	}
	
	
	
	
	@Test(priority = 2, enabled=true)
	public void test_NetcoolSearchDetails() throws Exception {
		
		ArrayList<String> rowdata = null;
		ExcelUtility.setExcelFile(Path_TestData + File_TestData, "Netcool_E2E");
		int cds_count = ExcelUtility.getNumberofIncidents();
		ArrayList<String>[] details = null;
		
		for(int i = 1; i<= cds_count;i++ ) {
		
				int n = 0;	
					rowdata = ExcelUtility.getRowData(i);
					
				if(rowdata.get(0).equalsIgnoreCase("Yes")){
				logger = ExtentITestListenerClassAdapter.getLogger();	 
				String cdsId =  rowdata.get(1);
				System.out.println("CDS Id is: "+cdsId);
				Thread.sleep(4000);
				
				details = NetcoolUtility.searchDetails(driver, cdsId , logger);
				System.out.println("for"+i+"round deatils "+details[n]);
				Inc_Utility.saveDetails(details[n], i);
				n++;
				
			}
				else{
					System.out.println("Test Skipped!! "+rowdata.get(1));
					logger.info("Test Skipped!! "+rowdata.get(1));
					
				}
			}
	
	}
	
	
	@Test(priority=3,enabled=false)
	public void test_verifyNetcoolEvent() throws Exception{
		
		ArrayList<String> rowdata = null;
		ExcelUtility.setExcelFile(Path_TestData + File_TestData, "Netcool_E2E");
		int event_count = ExcelUtility.getNumberofIncidents();
		//ArrayList<String>[] al = new ArrayList[incident_count];

		for(int i = 1; i<= event_count;i++ ) {

			
			rowdata = ExcelUtility.getRowData(i);
			
		if(rowdata.get(0).equalsIgnoreCase("Yes")){
		logger = ExtentITestListenerClassAdapter.getLogger();	 
		String cdsId =  rowdata.get(1);
		System.out.println("cds Id is: "+cdsId);
		Thread.sleep(4000);
		NetcoolUtility.verifyEventdetails(driver, cdsId, logger, rowdata);
		}
			
	}
	}	
	
}
