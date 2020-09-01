package com.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class NetcoolUtility {
	
	static WebDriver driver;
	private static Properties properties;
	static Actions action;
	static ExtentTest logger;
	
	public static ArrayList<String>[]  searchDetails(WebDriver driver, String cds_id,ExtentTest logger) throws FileNotFoundException, IOException, InterruptedException{
		
		properties = new Properties();
		properties.load(new FileReader(".//Data//ObjectReository.properties"));
		ArrayList<String> details = new ArrayList<String>();
		
		Thread.sleep(6000);
		MethodLibrary.clicknHold(driver, "nc_eventFlag_xpath");
		MethodLibrary.click(driver, "nc_eventViewer_xpath");
		Thread.sleep(3000);
		driver.switchTo().frame("_30899058_iframe");
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(properties.getProperty("nc_filter1Field_xpath")))));
		MethodLibrary.sendKeys(driver, "nc_filter1Field_xpath", "test01");
		MethodLibrary.sendEnterKey(driver, "nc_filter1Field_xpath", Keys.ENTER);
		Thread.sleep(2000);
		MethodLibrary.sendKeys(driver, "nc_filter2Field_xpath", "test01");
		Thread.sleep(2000);
		MethodLibrary.sendKeys(driver, "nc_searchItem_xpath", cds_id);
		Thread.sleep(2000);
		MethodLibrary.sendEnterKey(driver, "nc_searchItem_xpath", Keys.ENTER);
		Thread.sleep(5000);
		MethodLibrary.click(driver, "nc_pauseBtn_xpath");
		Thread.sleep(2000);
		int count  = driver.findElements(By.xpath(properties.getProperty("nc_numberofdataTable_xpath"))).size();
		System.out.println("total tabel row count "+count);
		
		ArrayList<String>[] al = new ArrayList[1];
		int n=0;
		
		for(int i = 1;i<=1;i++){
			for(int j=1;j<=30;j++){

				String val = MethodLibrary.nc_GetTableElement(driver,i, j);
				System.out.println("value: "+val);
				details.add(val);
				
					
			}
			System.out.println("for"+n+" "+details);
			al[n] = details;
			n++;
			
		}
		
		
		return al;	
	}

	
	public static void editFilter(WebDriver driver,String cdsId, String alarmkey) throws FileNotFoundException, IOException, InterruptedException{
		
		MethodLibrary.click(driver, "nc_editFilter1_xpath");
		Thread.sleep(4000);
		String windowhandle = driver.getWindowHandle();
		Set<String> handles = driver.getWindowHandles();
		
		for (String handle : handles) {
			if (handle != windowhandle) {
				driver.switchTo().window(handle);
			}
		}
		
		MethodLibrary.sendKeys(driver, "nc_cdsId_xpath", cdsId);
		MethodLibrary.sendKeys(driver, "nc_alarmkey_xpath", alarmkey);
		
		MethodLibrary.click(driver, "nc_savencloseBtn_xpath");
		Thread.sleep(2000);
		MethodLibrary.click(driver, "nc_closeBtn_xpath");
		driver.switchTo().defaultContent();
	}
	
	
	/**
	 * verifyEventdetails method verifies event details with Netcool data, Order of fields are fixed as per filter and datasheet and is important for correct execution.  
	 * @param driver
	 * @param cds_id
	 * @param logger
	 * @param eventData
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	
	public static void verifyEventdetails(WebDriver driver, String cds_id, ExtentTest logger, ArrayList<String> eventData) throws FileNotFoundException, IOException, InterruptedException{
		
		properties = new Properties();
		properties.load(new FileReader(".//Data//ObjectReository.properties"));
		
		//ArrayList<String> details = new ArrayList<String>();
		
		SoftAssert softassert = new SoftAssert();
		
		Thread.sleep(6000);
		MethodLibrary.clicknHold(driver,"nc_eventFlag_xpath");
		MethodLibrary.click(driver, "nc_eventViewer_xpath");
		Thread.sleep(3000);
		driver.switchTo().frame("_30899058_iframe");
		Thread.sleep(3000);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(properties.getProperty("nc_filter1Field_xpath")))));
		MethodLibrary.sendKeys(driver, "nc_filter1Field_xpath", "test01");
		MethodLibrary.sendEnterKey(driver, "nc_filter1Field_xpath", Keys.ENTER);
		Thread.sleep(2000);
		MethodLibrary.sendKeys(driver, "nc_filter2Field_xpath", "test01");
		Thread.sleep(2000);
		MethodLibrary.sendKeys(driver, "nc_searchItem_xpath", cds_id);
		Thread.sleep(2000);
		MethodLibrary.sendEnterKey(driver, "nc_searchItem_xpath", Keys.ENTER);
		Thread.sleep(5000);
		MethodLibrary.click(driver, "nc_pauseBtn_xpath");
		Thread.sleep(2000);
		
		int i = 1; 
		if(i<eventData.size()){
			
			String Node_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : Node field actual value: '"+Node_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String Tally_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : Tally field actual value: '"+Tally_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String LastOccureence_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : LastOccureence field actual value: '"+LastOccureence_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String AlarmKey_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : AlarmKey field actual value: '"+AlarmKey_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String Summary_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : Summary field actual value: '"+Summary_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;			
			
			String CDS_ID_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : CDS_ID field actual value: '"+CDS_ID_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String AlertGroup_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : AlertGroup field actual value: '"+AlertGroup_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String OwnerUID_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : OwnerUID field actual value: '"+OwnerUID_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
						
			String AlertKey_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : AlertKey field actual value: '"+AlertKey_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String Identifier_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : Identifier field actual value: '"+Identifier_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String Class_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : Class field actual value: '"+Class_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String MasterService_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : MasterService field actual value: '"+MasterService_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String Manager_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : Manager field actual value: '"+Manager_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String TTOpCat1_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : TTOpCat1 field actual value: '"+TTOpCat1_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String TTOpCat2_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : TTOpCat2 field actual value: '"+TTOpCat2_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String TTOpCat3_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : TTOpCat3 field actual value: '"+TTOpCat3_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
		
			String AlarmImpact_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : AlarmImpact field actual value: '"+AlarmImpact_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String FaultImpact_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : FaultImpact field actual value: '"+FaultImpact_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String Urgency_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : Urgency field actual value: '"+Urgency_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String Severity_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : Severity field actual value: '"+Severity_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String TroubleTicket_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : TroubleTicket field actual value: '"+TroubleTicket_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String TTNote_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : TTNote field actual value: '"+TTNote_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String TTFlag_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : TTFlag field actual value: '"+TTFlag_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String FaultQueue_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : FaultQueue field actual value: '"+FaultQueue_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String FaultPriority_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : FaultPriority field actual value: '"+FaultPriority_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String FaultStatus_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : FaultStatus field actual value: '"+FaultStatus_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String Poll_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : Poll field actual value: '"+Poll_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String CMDBProductName_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : CMDBProductName field actual value: '"+CMDBProductName_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String CMDBCategory_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : CMDBCategory field actual value: '"+CMDBCategory_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
			
			String TTTitle_actualvalue = MethodLibrary.nc_verifyValue(driver, i, softassert, eventData.get(i));
			logger.info("Validatation point : TTTitle field actual value: '"+TTTitle_actualvalue+"' matches with expected value: "+eventData.get(i));
			i++;
		
		}
		
		
		
	}
	
}
