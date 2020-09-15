package com.test;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
import com.java.CMDBUtility;
import com.java.ExcelUtility;

import com.java.ExtentReportUtility;
import com.java.Inc_Utility;
import com.java.MethodLibrary;
import com.java.NetcoolUtility;
import com.listeners.ExtentITestListenerClassAdapter;
import com.aventstack.extentreports.ExtentTest;

@Listeners({com.listeners.RetryListener.class,ExtentITestListenerClassAdapter.class})

public class TC_VerifyAlarmKeyDB extends MethodLibrary {
	
	static WebDriver driver;
	static ExtentReports extent;
	static ExtentTest logger;
	private static String Path_TestData = ".//Data//"; 
	private static String File_TestData = "CMDB_DataSheet.xlsx";
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
			openCMDB(driver,"username","pwd");
		}
		catch(Exception e) {

			e.printStackTrace();
		}
	}

	/*@Test(priority = 1, enabled=true, retryAnalyzer= com.listeners.Retry.class)
	public void openCMDB() throws FileNotFoundException, IOException{

		openCMDB(driver,"username","pwd");
	}*/
	
	@Test(priority = 1, enabled=true)
	public void Test_AlarmkeyDB() throws Exception 
	{

			logger = ExtentITestListenerClassAdapter.getLogger();
			
			ExcelUtility.setExcelFile(Path_TestData + File_TestData, "Alarmkey_Data");
			int Alarmkey_count = ExcelUtility.getNumberofAlarmkeys();
			int NoAlarmkey_count = 0, YesAlarmkey_count = 0, EmptyComponentID_count = 0;
			System.out.println("Number Alarm keys : "+ Alarmkey_count);
		
			for(int i = 1; i<=Alarmkey_count; i++ )
			{
				String Alarmkey = ExcelUtility.getCellData(i, 1);
				System.out.println("Verifying Alarm key: "+Alarmkey);
				ArrayList<String> rowdata = ExcelUtility.getRowData(i);
				
				int numOfcol=6;
				
				boolean flag=ExcelUtility.verifyEmpty(rowdata,numOfcol);
			
			if(rowdata.get(0).equalsIgnoreCase("Yes") && flag==true)
			{
				logger = ExtentITestListenerClassAdapter.getLogger();
		    	System.out.println("Alarm key data Verified: "+rowdata);
		    	CMDBUtility.verifyAlarmkeyDetails(logger, driver, Alarmkey, rowdata, rowdata.get(2));
		    	YesAlarmkey_count++;
		    	
			}else if(flag==false)
			{
				System.out.println("Alarm key has empty fields:  "+rowdata.get(1));
				logger.log(Status.WARNING,"Alarm key has empty fields:  "+rowdata.get(1));
				EmptyComponentID_count++;
			}
			else
			{
				System.out.println("Alarm key Skipped: "+rowdata.get(1));
				logger.log(Status.SKIP,"Alarm key Skipped: "+rowdata.get(1));
				NoAlarmkey_count++;
			}
		}
		
			logger.info("Number of Alarmkeys  = "+Alarmkey_count);
			logger.info("Number of Alarmkeys verified = "+YesAlarmkey_count);
			logger.info("Number of Alarmkeys Skipped = "+NoAlarmkey_count);
			logger.info("Number of ComponentIDs has empty fields = "+EmptyComponentID_count);
	}

	@AfterTest(enabled = true)
	public void teardown() {
		try {
			Thread.sleep(500);
			MethodLibrary.cmdblogout(driver);
			driver.quit();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}	

}
