package com.test;

import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.sql.RowSetMetaData;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

@Listeners({com.listeners.RetryListener.class,ExtentITestListenerClassAdapter.class})
public class TC_CMDBvalidations extends MethodLibrary {
 
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
 
 
 @Test(priority = 2, enabled=true)
 public void Test_CID_ComputerSystem() throws Exception,RuntimeException  {

			logger = ExtentITestListenerClassAdapter.getLogger();
			
			ExcelUtility.setExcelFile(Path_TestData + File_TestData, "CID_ComputerSystem");
			
			int ComponentID_count = ExcelUtility.getNumberofComponentIDs();
			
			int NoComponentID_count = 0, YesComponentID_count = 0, EmptyComponentID_count = 0;
			
			System.out.println("Number of Component IDs: "+ ComponentID_count);
			
			for(int i = 1; i<=ComponentID_count; i++ ) 
			{
				
				String ComponentID = ExcelUtility.getCellData(i, 2);
				System.out.println("Verifying ComponentID: "+ComponentID);
				ArrayList<String> rowdata = ExcelUtility.getRowData(i);
				
				int numOfCol=14;
				
				boolean flag=ExcelUtility.verifyEmpty(rowdata,numOfCol);
								
				if(rowdata.get(0).equalsIgnoreCase("Yes") && flag==true)
				{
					logger = ExtentITestListenerClassAdapter.getLogger();
					System.out.println("ComponentID Verified: "+rowdata);
					CMDBUtility.verifyCID_ComputerSystem(logger, driver, ComponentID, rowdata);
					YesComponentID_count++;
				}
				else if(flag==false)
				{
					System.out.println("Component ID has empty fields:  "+rowdata.get(2));
					logger.log(Status.FAIL,"Component ID has empty fields:  "+rowdata.get(2));
					EmptyComponentID_count++;
				}
				else
				{
					System.out.println("Component ID Skipped: "+rowdata.get(2));
					logger.log(Status.SKIP,"Component ID Skipped: "+rowdata.get(2));
					NoComponentID_count++;
				}
			}			

		logger.info("Number of ComponentIDs = "+ComponentID_count);
		logger.info("Number of ComponentIDs verified = "+YesComponentID_count);
		logger.info("Number of ComponentIDs Skipped = "+NoComponentID_count);
		logger.info("Number of ComponentIDs has empty fields = "+EmptyComponentID_count);
		
		WebElement HomeButton = driver.findElement(By.xpath("//*[contains(text(),'Home')]"));
	    HomeButton.click();
	    Thread.sleep(1000);
	    
	    Set<String> handles = driver.getWindowHandles();
	       String currentHandle = driver.getWindowHandle();
	       for (String handle : handles) {
	  
	        if (!handle .equals(currentHandle))
	        {
	            driver.switchTo().window(handle);
	        }
	     }
		
}

	
 @Test(priority = 1, enabled=true)
 public void Test_CID_BusinessApplication() throws Exception,RuntimeException  {

			logger = ExtentITestListenerClassAdapter.getLogger();
			ExcelUtility.setExcelFile(Path_TestData + File_TestData, "CID_BusinessApplication");
			int ComponentID_count = ExcelUtility.getNumberofComponentIDs();
			int NoComponentID_count = 0, YesComponentID_count = 0, EmptyComponentID_count = 0;
			System.out.println("Number of Component IDs: "+ ComponentID_count);
			
			for(int i = 1; i<=ComponentID_count; i++ ) 
			{
				
				String ComponentID = ExcelUtility.getCellData(i, 1);
				System.out.println("Verifying ComponentID: "+ComponentID);
				ArrayList<String> rowdata = ExcelUtility.getRowData(i);
				
				int numOfCol=12;
				
				boolean flag=ExcelUtility.verifyEmpty(rowdata,numOfCol);
								
				if(rowdata.get(0).equalsIgnoreCase("Yes") && flag==true)
				{
					logger = ExtentITestListenerClassAdapter.getLogger();
					System.out.println("ComponentID Verified: "+rowdata);
					CMDBUtility.verifyCID_BusinessApplication(logger, driver, ComponentID, rowdata);
					YesComponentID_count++;
				}
				else if(flag==false)
				{
					System.out.println("Component ID has empty fields:  "+rowdata.get(1));
					logger.log(Status.FAIL,"Component ID has empty fields:  "+rowdata.get(1));
					EmptyComponentID_count++;
				}
				else
				{
					System.out.println("Component ID Skipped: "+rowdata.get(1));
					//logger.log(Status.SKIP,"Component ID Skipped: "+rowdata.get(1));
					logger.info("Component ID Skipped:  "+rowdata.get(1));
					NoComponentID_count++;
				}
			}			

		logger.info("Number of ComponentIDs = "+ComponentID_count);
		logger.info("Number of ComponentIDs verified = "+YesComponentID_count);
		logger.info("Number of ComponentIDs Skipped = "+NoComponentID_count);
		logger.info("Number of ComponentIDs has empty fields = "+EmptyComponentID_count);
		
		
 }

 
 @Test(priority = 3, enabled=true)
 public void Test_AlarmkeyDB() throws Exception 
	{

			logger = ExtentITestListenerClassAdapter.getLogger();
			
			ExcelUtility.setExcelFile(Path_TestData + File_TestData, "Alarmkey_Data");
			int Alarmkey_count = ExcelUtility.getNumberofAlarmkeys();
			int NoAlarmkey_count = 0, YesAlarmkey_count = 0, EmptyAlarmkey_count = 0;
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
				EmptyAlarmkey_count++;
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
			logger.info("Number of Alarmkeys has empty fields  = "+EmptyAlarmkey_count);
		
	}

 
  @AfterTest(enabled = true)
 public void teardown() {
  try {  
   MethodLibrary.cmdblogout(driver);
   Thread.sleep(500);
   driver.quit();
   } catch (InterruptedException e)
  		{e.printStackTrace();}
  } 

}
