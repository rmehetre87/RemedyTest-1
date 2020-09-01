package com.test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.java.ExcelUtility;
import com.java.ExtentReportUtility;
import com.java.Inc_Utility;
import com.java.MethodLibrary;
import com.listeners.ExtentITestListenerClassAdapter;

@Listeners({com.listeners.RetryListener.class,ExtentITestListenerClassAdapter.class})
public class TC_NetCoolRemedyInc extends MethodLibrary {


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
	public void openRemedyTest() throws FileNotFoundException, IOException{

		openRemedy(driver,"username1","pwd1");
	}


	@Test(priority = 2, enabled=true)
	public void test_NetcoolIncident() throws Exception  {

		logger = ExtentITestListenerClassAdapter.getLogger();
		ExcelUtility.setExcelFile(Path_TestData + File_TestData, "Netcool_Remedy");
		int incident_count = ExcelUtility.getNumberofIncidents();
		//System.out.println(""+ incident_count);

		for(int i = 1; i<= incident_count; i++ ) {

			String incId = ExcelUtility.getCellData(i, 1);
			System.out.println("Id is: "+incId);
			ArrayList<String> rowdata = ExcelUtility.getRowData(i);
			System.out.println("Row data: "+rowdata);
			Thread.sleep(5000);
			Inc_Utility.verifyNetcoolIncDetails(logger, driver, incId, rowdata);

		}


	}

	@DataProvider(name="NetcoolIncData")
	public Object[] dataProvider() throws Exception{
		
		int n =0;
		ArrayList<String> rowdata = null;
		ExcelUtility.setExcelFile(Path_TestData + File_TestData, "Netcool_Remedy");
		int incident_count = ExcelUtility.getNumberofIncidents();
		ArrayList<String>[] al = new ArrayList[incident_count];

		for(int i = 1; i<= incident_count;i++ ) {

			String incId = ExcelUtility.getCellData(i, 1);
			System.out.println("Id is: "+incId);
			rowdata = ExcelUtility.getRowData(i);
			al[n]= rowdata;
			n++;
		}
		
		return al;

	}
	
	
	
	@DataProvider(name="NetcoolIncData1")
	public Iterator<ArrayList<String>> newdataProvider() throws Exception{
		
		int n =0;
		ArrayList<String> rowdata = new ArrayList<String>();
		ExcelUtility.setExcelFile(Path_TestData + File_TestData, "Netcool_Remedy");
		int incident_count = ExcelUtility.getNumberofIncidents();
		ArrayList<String>[] al = new ArrayList[incident_count];

		for(int i = 1; i<= incident_count;i++ ) {

			String incId = ExcelUtility.getCellData(i, 1);
			System.out.println("Id is: "+incId);
			rowdata = ExcelUtility.getRowData(i);
			al[n]= rowdata;
			n++;
		}
		
		return Arrays.asList(al).iterator();
	}

	
	


	@Test(priority = 2, enabled=false)
	public void test_NetcoolIncidentData() throws Exception  {
		
		ArrayList<String> rowdata = null;
		ExcelUtility.setExcelFile(Path_TestData + File_TestData, "Netcool_Remedy");
		int incident_count = ExcelUtility.getNumberofIncidents();
		//ArrayList<String>[] al = new ArrayList[incident_count];

		for(int i = 1; i<= incident_count;i++ ) {


			rowdata = ExcelUtility.getRowData(i);

			if(rowdata.get(0).equalsIgnoreCase("Yes")){
				logger = ExtentITestListenerClassAdapter.getLogger();	 
				String incId =  rowdata.get(1);
				System.out.println("INC Id is: "+incId);
				Thread.sleep(4000);
				Inc_Utility.verifyNetcoolIncDetails(logger, driver, incId, rowdata);
			}
			else{
				System.out.println("Test Skipped!! "+rowdata.get(1));
				logger.info("Test Skipped!! "+rowdata.get(1));

			}
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

}