package com.java;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import com.aventstack.extentreports.ExtentTest;
import com.java.ExtentReportUtility;
import com.listeners.ExtentITestListenerClassAdapter;

public class WO_utility {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	static ExtentTest logger;

	public static String create_WO(WebDriver driver,ArrayList<String> incData, int rownum, ExtentTest logger) {
		String workOrder_num = null;
		
		logger.info("**** WorkOrder creation Started ****");
		int i=0;
		if(i<=incData.size()) {
				try {
							MethodLibrary.onclick(driver,"Applications_btn");
							MethodLibrary.click(driver,"Service_Request_Management_btn");
							MethodLibrary.click(driver,"New_Workorder_btn");
							Thread.sleep(4000);
							//MethodLibrary.checkEleVisibility("wo_company_xpath");
							//MethodLibrary.click(driver,"wo_company_xpath");
							//MethodLibrary.sendKeys(driver,"wo_company_xpath", "TELEFONICA UK");
						    //MethodLibrary.click(driver,"wo_company_search");
							MethodLibrary.checkEleclickable(driver,"Customer_xpath");
							//Thread.sleep(3000);
							MethodLibrary.click(driver,"Customer_xpath");
							//Thread.sleep(2000);
							MethodLibrary.sendKeys(driver,"Customer_xpath", incData.get(i));
							Thread.sleep(3000);
							MethodLibrary.click(driver,"wo_serch_btn");
							i++;
							Thread.sleep(3000);
							MethodLibrary.checkEleclickable(driver,"Contact");
							MethodLibrary.click(driver,"Contact");
							Thread.sleep(2000);
							MethodLibrary.sendKeys(driver,"Contact", incData.get(i));
							Thread.sleep(3000);
							MethodLibrary.click(driver,"wo_serch_btn");
							i++;
							//MethodLibrary.sendKeys(driver,"Note", incData.get(i));
							i++;
							//MethodLibrary.sendKeys(driver,"resolution", incData.get(i));
							i++;
							MethodLibrary.click(driver,"Template");
							Thread.sleep(3000);
							MethodLibrary.sendKeys(driver,"Template", incData.get(i));
							Thread.sleep(3000);
							MethodLibrary.click(driver,"wo_serch_btn");
							Thread.sleep(3000);
							i++;
							MethodLibrary.click(driver,"Service_selection");
							MethodLibrary.clicknHold(driver,"wo_service_grp1");
							MethodLibrary.clicknHold(driver,"wo_service_grp2");
							MethodLibrary.click(driver,"wo_service_grp3");
							
							String woNum[] = MethodLibrary.getElement(driver,"new_wo_number").getText().split(" ");
							i++;			
							MethodLibrary.captureScreenShotMethod("WorkOrder");
							MethodLibrary.click(driver,"WO_save_btn");
						    MethodLibrary.captureScreenShotMethod("WorkOrder_save");
							workOrder_num = woNum[0];
							System.out.println("Created Inc number: "+workOrder_num);
							ExcelUtility.setCellData(workOrder_num, rownum, i);
							driver.wait(7000);
						//Assert.assertEquals("", workOrder_num);
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
				
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
		
		logger.info("WorkOrder created :" +workOrder_num);
		return workOrder_num;
		
			
	}

	
	public static void search_WO(WebDriver driver,String woId, ExtentTest logger) throws FileNotFoundException, IOException, InterruptedException {
		
				
		MethodLibrary.click(driver,"Applications_btn");
		logger.info("Application started..");
		MethodLibrary.click(driver,"Service_Request_Management_btn");
		logger.info("Navigating to Service Req Mgmnt page..");
		MethodLibrary.click(driver,"search_workorder_btn");
		logger.info("On search work order page...");
		Thread.sleep(3000);
		MethodLibrary.click(driver,"wo_id_xpath");
		Thread.sleep(2000);
		MethodLibrary.sendKeys(driver,"wo_id_xpath", woId);
		MethodLibrary.getElement(driver,"wo_id_xpath").sendKeys(Keys.ENTER);
		
		logger.info("work order number entered..");
		
		logger.info("searched work order is: " + woId);
		
		
	}
	
	
}
