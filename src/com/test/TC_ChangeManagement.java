package com.test;

import org.testng.annotations.Test;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;



import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.java.Change_utility;
import com.java.ExcelUtility;
import com.java.MethodLibrary;
import com.java.WO_utility;
import com.listeners.ExtentITestListenerClassAdapter;

@Listeners(ExtentITestListenerClassAdapter.class)
public class TC_ChangeManagement extends MethodLibrary{

	private static String Path_TestData = ".//Data//"; 
    private static String File_TestData = "Remedy_GetData.xlsx";
    static WebDriver driver;
    private static Properties properties;
    
    @BeforeTest
	public void beforeTest(ITestContext context) {
					properties = new Properties();	
						try {
								properties.load(new FileReader(".//Data//ObjectReository.properties"));
								driver = openBrowser("Chrome");
								context.setAttribute("webDriver", driver);
								openRemedy(driver,"username1","pwd1");
								
							}
							catch(Exception e) {

									e.printStackTrace();
								}
		  }
		
	
	/*@Test(priority = 1,retryAnalyzer= com.listeners.Retry.class)
	public void openRemedyTest() throws FileNotFoundException, IOException{
		
		openRemedy(driver,"username1","pwd1");
	}*/
	
	@Test(dependsOnMethods = "openRemedyTest",retryAnalyzer= com.listeners.Retry.class, enabled = true, priority = 2)
    public void test_createCRQ() throws Exception {
		
		Change_utility.create_CR(driver);
	
	}
	
	
	@Test(enabled = true,retryAnalyzer= com.listeners.Retry.class, priority = 3, dependsOnMethods = "openRemedyTest")
	public void test_searchCRQ() throws Exception{
		
		ExcelUtility.setExcelFile(Path_TestData + File_TestData, "CRQ_search");
		
		int crq_count = ExcelUtility.getNumberofIncidents();
		for(int i = 1; i<= crq_count;i++ ) {
			
			String crqId = ExcelUtility.getCellData(i, 0);
			System.out.println("Id is: "+crqId);
			//remedyObj.searchIncident(driver,incId);
			Change_utility.search_CR(driver, crqId);
		
		}
	
	}
	
	@AfterTest
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
