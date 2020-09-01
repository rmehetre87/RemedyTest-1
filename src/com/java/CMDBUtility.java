package com.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.google.inject.Key;


public class CMDBUtility extends MethodLibrary{
	
	static WebDriver driver;
	private static Properties properties = new Properties();
	static Actions action;

	
public static void verifyAlarmkeyDetails(ExtentTest logger, WebDriver driver, String Alarmkey, ArrayList<String> keyData, String className) throws FileNotFoundException,
	IOException, Exception {

		properties.load(new FileReader(".//Data//ObjectReository.properties"));
		
		SoftAssert softassert = new SoftAssert();
	 		
			try { 
					MethodLibrary.click(driver,"btn_Applications");
					MethodLibrary.click(driver,"btn_QuickLinks");
					WebElement AlarmEnrichmentDB = driver.findElement(By.xpath("//a/span[text()='Alarm Enrichment DB']"));
					AlarmEnrichmentDB.click();
				}
			catch(Exception e){	}
		
					MethodLibrary.click(driver, "btn_newearch_Alarmkey");
					MethodLibrary.sendKeys(driver, "Alarm_key", Alarmkey);
					//MethodLibrary.sendEnterKey(driver,"Alarm_key", Keys.ENTER);
					MethodLibrary.click(driver,"btn_search_Alarmkey");
	
				try
				{
					WebElement webtable=driver.findElement(By.xpath(".//*[@id=\"T1020\"]/tbody"));
					List<WebElement>rows=webtable.findElements(By.tagName("tr"));
					
					String Befor_xpath = ".//*[@id=\"T1020\"]/tbody/tr[";
					String After_xpath = "]/td[1]/nobr/span";
				
					for(int i=2; i<=rows.size(); i++)
					{
						String name = driver.findElement(By.xpath(Befor_xpath+i+After_xpath)).getText();
					
						if(name.equals(className))
						{
						driver.findElement(By.xpath(Befor_xpath+i+After_xpath)).click();
						}
				   }
				}
				catch(Exception e1) {}
	
			try{
				logger.info("Verifying details for Alarm key: " + Alarmkey);
				String windowhandle = driver.getWindowHandle();
				System.out.println("window handle: "+windowhandle);
				WebElement frame1 =  MethodLibrary.getElement(driver, "Nomatchfoundframe_xpath");
				driver.switchTo().frame(frame1);
				MethodLibrary.click(driver, "Nomatchfound_popup_ok");
				System.out.println("No match found for Alarmkey: "+Alarmkey);
				logger.warning("No match found for Alarmkey: "+Alarmkey);
				String temp = MethodLibrary.getScreenshot(driver);
				logger.warning("<font color="+"red>"+"Screenshot: "+"</font>"+"</b>", MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
				
							
			}catch(Exception e)
				{
				
				logger.info("Verifying details for Alarm key: " + Alarmkey);
		   
				// =====Get Alarm Key DB details====//
					
					int i = 1;
															
					if(i<keyData.size())
					{
						Thread.sleep(1000);
						
						MethodLibrary.verifyFieldValue(logger,driver, softassert, "Alarm_key", "value", keyData.get(i));
						i++;
						
						MethodLibrary.verifyFieldValue(logger,driver,softassert, "Class_Field", "value", keyData.get(i));
						i++;
						
						MethodLibrary.verifyFieldValue(logger,driver,softassert, "Service_impact_Field", "value", keyData.get(i));
						i++;
						
						MethodLibrary.verifyFieldValue(logger,driver,softassert, "Discard_Field", "value", keyData.get(i));
						i++;
						
						MethodLibrary.verifyFieldValue(logger,driver,softassert, "Impact_Field", "value", keyData.get(i));
						i++;
						
						MethodLibrary.verifyFieldValue(logger,driver, softassert, "Urgency_Field", "value", keyData.get(i));
						i++;
						
						MethodLibrary.verifyFieldValue(logger,driver, softassert, "OPSCAT1_Field", "value", keyData.get(i));
						i++;
						
						MethodLibrary.verifyFieldValue(logger,driver, softassert, "OPSCAT2_Field", "value", keyData.get(i));
						i++;
						
						MethodLibrary.verifyFieldValue(logger,driver, softassert, "OPSCAT3_Field", "value", keyData.get(i));
						i++;
						
					}
					logger.pass("Alarm key DB Validation sucessfull: "+Alarmkey);
					logger.info("-------------------------------------------------------------------------------------------------------------------------");
					WebElement searchNewAlarmkey = driver.findElement(By.xpath(properties.getProperty("btn_newsearch_AlarmEnrchDB")));
					searchNewAlarmkey.click();	
					
					}
	          }


public static void verifyCID_ComputerSystem(ExtentTest logger, WebDriver driver, String ComponentID, ArrayList<String> keyData) throws FileNotFoundException,
IOException, Exception {

		properties.load(new FileReader(".//Data//ObjectReository.properties"));

			SoftAssert softassert = new SoftAssert();
			
		   try {
				MethodLibrary.click(driver,"btn_Applications");
				MethodLibrary.click(driver,"btn_QuickLinks");
				WebElement ComponentIDEnrichmentDB = driver.findElement(By.xpath("//a/span[text()='Component Enrichment DB']"));
				ComponentIDEnrichmentDB.click();
			}
			catch(Exception e){	}
			
			MethodLibrary.click(driver, "btn_newearch_componentID");
			MethodLibrary.sendKeys(driver, "ComponentID", ComponentID);
			MethodLibrary.click(driver,"btn_search_componentID");
			
			try{
				
				String windowhandle = driver.getWindowHandle();
				System.out.println("window handle: "+windowhandle);
				WebElement frame1 =  MethodLibrary.getElement(driver, "Nomatchfoundframe_xpath");
				driver.switchTo().frame(frame1);
				MethodLibrary.click(driver, "Nomatchfound_popup_ok");
				logger.warning("NO match Found for component ID: "+ComponentID);
				String temp = MethodLibrary.getScreenshot(driver);
				logger.warning("<font color="+"red>"+"Screenshot: "+"</font>", MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

			}catch(Exception e)
			{  									
				logger.info("Verifying details for ComponentID : " + ComponentID);
				
				// =====Get ComponentID DB details====//
				
					int i = 2;
						
		    if(i<keyData.size())
			{
				Thread.sleep(1000);
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "ComponentID", "value", keyData.get(i));
				i++;

				MethodLibrary.verifyFieldValue(logger,driver, softassert, "CIname_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "TTremedyCi_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "DiscriptionC_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "Category_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "Type_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "Item_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "ProductName_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "Manufaturer_Field", "value", keyData.get(i));
				i++;
				
				String masterService=null;
				if(keyData.get(14).equals("Testing")){ masterService="9"; }
				else{ masterService="6"; }
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "MasterService_Field", "value", masterService);
				i++;
				//softassert.assertAll();	
								
			}
		    
			logger.pass("ComponentID Enrichment DB Validation sucessfull for component ID: "+ComponentID);
			logger.info("-------------------------------------------------------------------------------------------------------------------------");
			WebElement Newearch_componentID = driver.findElement(By.xpath(properties.getProperty("btn_newearch_componentID")));
			Newearch_componentID.click();
			
		  }
			
	}


public static void verifyCID_BusinessApplication(ExtentTest logger, WebDriver driver, String ComponentID, ArrayList<String> keyData) throws FileNotFoundException,
IOException, Exception {

		properties.load(new FileReader(".//Data//ObjectReository.properties"));

			SoftAssert softassert = new SoftAssert();
			
		   try {
				MethodLibrary.click(driver,"btn_Applications");
				MethodLibrary.click(driver,"btn_QuickLinks");
				WebElement ComponentIDEnrichmentDB = driver.findElement(By.xpath("//a/span[text()='Component Enrichment DB']"));
				ComponentIDEnrichmentDB.click();
			}
			catch(Exception e){	}
			
			MethodLibrary.click(driver, "btn_newearch_componentID");
			MethodLibrary.sendKeys(driver, "ComponentID", ComponentID);
			MethodLibrary.click(driver,"btn_search_componentID");
			
			try{
				
				String windowhandle = driver.getWindowHandle();
				System.out.println("window handle: "+windowhandle);
				WebElement frame1 =  MethodLibrary.getElement(driver, "Nomatchfoundframe_xpath");
				driver.switchTo().frame(frame1);
				MethodLibrary.click(driver, "Nomatchfound_popup_ok");
				logger.info("NO match Found for component ID: "+ComponentID);
				String temp = MethodLibrary.getScreenshot(driver);
				logger.info("<font color="+"red>"+"Screenshot: "+"</font>", MediaEntityBuilder.createScreenCaptureFromPath(temp).build());

			}catch(Exception e)
			{  									
				logger.info("Verifying details for ComponentID : " + ComponentID);
				
				// =====Get ComponentID DB details====//
				
					int i = 1;
						
		    if(i<keyData.size())
			{
				Thread.sleep(1000);
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "ComponentID", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "CIname_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "DiscriptionC_Field", "value", keyData.get(i));
				i++;

				MethodLibrary.verifyFieldValue(logger,driver, softassert, "Category_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "Type_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "Item_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "ProductName_Field", "value", keyData.get(i));
				i++;
				
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "Manufaturer_Field", "value", keyData.get(i));
				i++;
							
				String masterService=null;
				if(keyData.get(12).equals("Testing")){ masterService="9"; }
				else{ masterService="6"; }
				MethodLibrary.verifyFieldValue(logger,driver, softassert, "MasterService_Field", "value", masterService);
				i++;
				
								
			}
		    
			logger.pass("ComponentID Enrichment DB Validation sucessfull for component ID: "+ComponentID);
			logger.info("-------------------------------------------------------------------------------------------------------------------------");
			WebElement Newearch_componentID = driver.findElement(By.xpath(properties.getProperty("btn_newearch_componentID")));
			Newearch_componentID.click();
			
		  }
			
	}


}

