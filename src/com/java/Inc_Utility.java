package com.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class Inc_Utility {

	static WebDriver driver;
	private static Properties properties = new Properties();
	static Actions action;
	static ExtentTest logger;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static void handleAlertPresent(WebDriver driver) {

		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();

		} catch (NoAlertPresentException e) {
			System.out.println("No alert present!");

		}

	}

	public static String getAttribute(WebElement element, String attribute) {

		if (element.getAttribute(attribute) == null
				|| element.getAttribute(attribute) == "") {

			return "Null";
		} else {
			return element.getAttribute(attribute);
		}
	}

	public void logout(WebDriver driver) {
		properties = new Properties();
		try {
			properties.load(new FileReader(
					".//Data//ObjectReository.properties"));
			driver.findElement(By.xpath(properties.getProperty("Logout_xpath")))
			.click();
			driver.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void searchIncident(WebDriver driver, String incId,ExtentTest logger) {
        
		
		try {
			properties = new Properties();
			properties.load(new FileReader(".//Data//ObjectReository.properties"));
			handleAlertPresent(driver);

			MethodLibrary.click(driver, "Applications_btn");
			MethodLibrary.clicknHold(driver, "IncidentMgmt_btn");
			MethodLibrary.click(driver, "sraechInc_btn");
			Thread.sleep(3000);
			MethodLibrary.sendKeys(driver,"incident_id", incId);
			MethodLibrary.sendEnterKey(driver,"incident_id", Keys.ENTER);
			Thread.sleep(2000);
			
			logger.info("Incident " + incId + " details getting searched!!");
			Thread.sleep(5000);
			
		} catch (Exception e) {
			e.printStackTrace();
			// driver.close();
			

		}

	}
	
	
	/**
	 * addCI add CI relationship value of an Incident.
	 * 
	 * @param webdriver instance, 
	 * @param Test context
	 * @param CI value,  
	 */
	
	public static void addCI(WebDriver driver,ExtentTest logger, String value) throws FileNotFoundException, IOException, InterruptedException{
		
		if(value.length()>1){
						SoftAssert softassert = new SoftAssert();  
						String windowhandle = driver.getWindowHandle();
						softassert.assertTrue(MethodLibrary.checkElementPresent(driver,"CI_searchIcon"));
						MethodLibrary.click(driver,"CI_searchIcon");
						Thread.sleep(1000);
						
						Set<String> handles = driver.getWindowHandles();
				
						for (String handle : handles) {
							if (handle != windowhandle) {
								driver.switchTo().window(handle);
							}
						}
						
						MethodLibrary.sendKeys(driver,"CI_searchbox", value);
						MethodLibrary.click(driver,"CI_searchBtn");
						Thread.sleep(3000);
						softassert.assertTrue(MethodLibrary.checkElementPresent(driver,"CI_value"));
						MethodLibrary.click(driver,"CI_value");
						MethodLibrary.click(driver,"CI_selectBtn");
						Thread.sleep(2000);
						
						driver.switchTo().window(windowhandle);
						
						softassert.assertAll();
						Thread.sleep(2000);
				}
	
	}

	
	public static void removeCI(WebDriver driver,ITestContext context, String value) throws FileNotFoundException, IOException{
		
		WebElement ele = MethodLibrary.getCIbyValaue(driver,value);
		String windowhandle = driver.getWindowHandle();
		ele.click();
		MethodLibrary.click(driver,"CI_removeBtn");
		MethodLibrary.switchChildwindow();
		MethodLibrary.click(driver,"CI_removeYes");
		driver.switchTo().window(windowhandle);
		
	}
	

	public static void addRelationshipCI(WebDriver driver,ExtentTest logger, ArrayList<String> values) throws FileNotFoundException, IOException, InterruptedException{
		
		logger.info("On search incident details Page!!");
		WebDriverWait wait = new WebDriverWait(driver, 25);
		String windowhandle = driver.getWindowHandle();
		System.out.println("window handle: "+windowhandle);
		WebElement frame1 =  MethodLibrary.getElement(driver, "iFrameMsg_xpath");
		driver.switchTo().frame(frame1);
		
		MethodLibrary.click(driver, "iFrameMsg_ok");
		Thread.sleep(8000);
		MethodLibrary.click(driver, "Relationship_tab");
		logger.info("Adding CIs to Incident!!");
		MethodLibrary.click(driver, "relation_menu");
		MethodLibrary.click(driver, "relation_sel");
		
			for(int i=2;i<=4;i++){
				if(values.get(i).length()>1){	
						//MethodLibrary.click(driver, "relation_menu");
						//MethodLibrary.click(driver, "relation_sel");
						MethodLibrary.click(driver, "relation_search");
						
						Thread.sleep(1000);
						
						Set<String> handles = driver.getWindowHandles();
				
						for (String handle : handles) {
							if (handle != windowhandle) {
								driver.switchTo().window(handle);
							}
						}
						
						MethodLibrary.sendKeys(driver,"CI_searchbox", values.get(i));
						MethodLibrary.click(driver,"CI_searchBtn");
						Thread.sleep(2000);
						MethodLibrary.click(driver,"CI_value");
						MethodLibrary.click(driver, "CI_relateBtn");
						Thread.sleep(2000);
						WebElement frame2 =  MethodLibrary.getElement(driver, "CI_frame");
						System.out.println("Frame:" +frame2);
					   	driver.switchTo().frame(frame2);
					    MethodLibrary.click(driver, "iFrameMsg_ok");
					   if(MethodLibrary.getElement(driver, "iFrameMsg_xpath").isDisplayed()){
						   frame2 =  MethodLibrary.getElement(driver, "CI_frame");
						   System.out.println("Frame:" +frame2);
						   driver.switchTo().frame(frame2);
						   MethodLibrary.click(driver, "iFrameMsg_ok");
						   
					   }
					   
					    Thread.sleep(3000);
					    System.out.println("handles "+handles);
					    System.out.println("window handle "+windowhandle);
					    driver.switchTo().window(windowhandle);
					    
					}
				
			}	
			
			MethodLibrary.click(driver, "save_btn"); 				//Saving Incident 
			logger.info("CIs added and saved to the Incident!!");
	}
	
	/**
	 * updateIncident Updates Status, Priority, Assignee, CI value of an Incident.
	 * 
	 * @param webdriver instance, 
	 * @param incident number, 
	 * @param Test context 
	 * @return Array of incident details
	 */
	public static void updateIncident(WebDriver driver,ExtentTest logger, ArrayList<String> data) throws InterruptedException {
				
			    String inc_status = data.get(1);
				String inc_Impact= data.get(2);
				String inc_Urgency= data.get(3);
				String support_company = data.get(4);
				String support_Org = data.get(5);
				String assignee_Grp = data.get(6);
				String assignee = data.get(7);
				String ci_value = data.get(8);
				String inc_Notes = data.get(9);
				String workInfo_type = data.get(10);
		
		
				action = new Actions(driver);
				properties = new Properties();
				
				logger.info("Incident update started!!");
		
			try {
					properties.load(new FileReader(".//Data//ObjectReository.properties"));
					String windowhandle = driver.getWindowHandle();
					Thread.sleep(3000);
					WebElement status = driver.findElement(By.xpath(properties.getProperty("status_field")));
		
		
				switch (inc_status) {
		
					case "Assigned":
						MethodLibrary.click(driver,"Assignee_sel");
						Thread.sleep(1000);
						MethodLibrary.clickByValue(driver,assignee);
						action.moveToElement(status).perform();
						status.click();
						driver.findElement(By.xpath(properties.getProperty("status_assigned_xpath"))).click();
						logger.pass("Status is updated to Assigned state!!");
						break;
		
					case "In Progress":
						if(MethodLibrary.getElementvalue(driver, "Assignee", "title").equalsIgnoreCase(assignee)){
							Thread.sleep(3000);
							logger.info("updating status to 'In-Progress'");
							/*System.out.println("Value :"+MethodLibrary.getElementvalue(driver, "Assignee", "title"));
							JavascriptExecutor js = (JavascriptExecutor)driver;
							js.executeScript("alert('Incident must be assigned first!!');"); */ 
							System.out.println("Value is: "+MethodLibrary.getElementvalue(driver, "Assignee", "title"));
							action.moveToElement(status).perform();
							status.click();
							driver.findElement(By.xpath(properties.getProperty("status_Inprogress_xpath"))).click();
							logger.pass("Status is updated to In-progress state!!");
							break;
						}
						MethodLibrary.click(driver,"Assignee_sel");
						Thread.sleep(1000);
						MethodLibrary.clickByValue(driver,assignee);
				
						System.out.println("Value is: "+MethodLibrary.getElementvalue(driver, "Assignee", "title"));
						action.moveToElement(status).perform();
						status.click();
						driver.findElement(By.xpath(properties.getProperty("status_Inprogress_xpath"))).click();
						break;
		
					case "Pending":
						action.moveToElement(status).perform();
						status.click();
						driver.findElement(By.xpath(properties.getProperty("status_pending_xpath"))).click();
						MethodLibrary.click(driver,"status_reason");
						Thread.sleep(1000);
						MethodLibrary.click(driver,"status_reason_value");
						logger.pass("Status is updated to Pending state!!");
						break;
		
					case "Resolved":
						if(MethodLibrary.getElementvalue(driver, "CI_textarea", "title").length() < 1)
						{
							addCI(driver, logger,ci_value);
						}
						action.moveToElement(status).perform();
						status.click();
						driver.findElement(By.xpath(properties.getProperty("status_resolved_xpath"))).click();
						Thread.sleep(8000);
						modify_InC(driver, windowhandle);
						driver.switchTo().window(windowhandle);
						Thread.sleep(2000);
						logger.pass("Status is updated to Resolved state!!");
						break;
		
					case "Closed":
						if(MethodLibrary.getElementvalue(driver, "status_xpath", "title").equalsIgnoreCase("Resolved")){
							status.click();
							driver.findElement(By.xpath(properties.getProperty("status_closed_xpath"))).click();
							Set<String> handles = driver.getWindowHandles();
		
							for (String handle : handles) {
								if (handle != windowhandle) {
									driver.switchTo().window(handle);
								}
							}
							MethodLibrary.click(driver,"close_status_sel");
							Thread.sleep(1000);
							MethodLibrary.click(driver,"close_reason");
							MethodLibrary.click(driver,"close_okBtn");
							driver.switchTo().window(windowhandle);
							Thread.sleep(2000);
							logger.pass("Status is updated to Closed state!!");
							break;
		
						}
		
						action.moveToElement(status).perform();
						status.click();
						driver.findElement(By.xpath(properties.getProperty("status_closed_xpath"))).click();
						Thread.sleep(5000);
						modify_InC(driver, windowhandle);
						driver.switchTo().window(windowhandle);
						Thread.sleep(3000);
						break;
		
					default:
						break;
		
					}
					WebElement impact = driver.findElement(By.xpath(properties
							.getProperty("impact_field")));
					action.moveToElement(impact).perform();
		
		
				switch (inc_Impact) {
		
					case "1-Extensive/Widespread":
		
						// MethodLibrary.click(driver,"Impact_field");
						impact.click();
						MethodLibrary.click(driver,"Impact_field_1_value");
						logger.info("Impact Updated to Extensive!!");
						break;
					case "2-Significant/Large":
						// MethodLibrary.click(driver,"Impact_field");
						impact.click();
						MethodLibrary.click(driver,"Impact_field_2_value");
						logger.info("Impact Updated to Significant!!");
						break;
					case "3-Moderate/Limited":
						// MethodLibrary.click(driver,"Impact_field");
						impact.click();
						MethodLibrary.click(driver,"Impact_field_3_value");
						logger.info("Impact Updated to Moderate!!");
						break;
		
					case "4-Minor/Localized":
						// MethodLibrary.click(driver,"Impact_field");
						impact.click();
						MethodLibrary.click(driver,"Impact_field_4_value");
						logger.info("Impact Updated to Minor!!");
						break;
		
					default:
						break;
					}
		
					WebElement urgency = driver.findElement(By.xpath(properties.getProperty("urgency_filed")));
					action.moveToElement(urgency).perform();
		
		
				switch (inc_Urgency) {
		
					case "1-Critical":
						// MethodLibrary.click(driver,"Urgency_field");
						urgency.click();
						MethodLibrary.click(driver,"Urgency_field_1_value");
						logger.info("Ugrency Updated to Critical!!");
						break;
					case "2-High":
						// MethodLibrary.click(driver,"Urgency_field");
						urgency.click();
						MethodLibrary.click(driver,"Urgency_field_2_value");
						logger.info("Ugrency Updated to High!!");
						break;
					case "3-Medium":
						// MethodLibrary.click(driver,"Urgency_field");
						urgency.click();
						MethodLibrary.click(driver,"Urgency_field_3_value");
						logger.info("Ugrency Updated to Medium!!");
						break;
					case "4-Low":
						// MethodLibrary.click(driver,"Urgency_field");
						urgency.click();
						MethodLibrary.click(driver,"Urgency_field_4_value");
						logger.info("Ugrency Updated to Low!!");
						break;
		
					default:
						break;
					}
					Thread.sleep(1000);
		
					if(assignee_Grp.length()>1){
						System.out.println("grp value:"+assignee_Grp+" length "+assignee_Grp.length());
						MethodLibrary.click(driver,"Assigned_group");
						MethodLibrary.clicknHoldbyValue(driver,support_company);
						MethodLibrary.clicknHoldbyValue(driver,support_Org);
						MethodLibrary.clickByValue(driver,assignee_Grp);
						logger.info("Assigned Group Updated to "+assignee_Grp);
		
					}
		
					if(assignee.length()>1){
						System.out.println("assignee value:"+assignee+" length "+assignee.length());
						MethodLibrary.click(driver,"Assignee_sel");
						Thread.sleep(2000);
						MethodLibrary.clickByValue(driver,assignee);
						logger.info("Assignee Updated to "+assignee);
		
					}
		
					if(ci_value.length()>1){
						
						addCI(driver, logger, ci_value);
						logger.info("CI Updated!!");
					}
		
					if(inc_Notes.length()>1){
		
						MethodLibrary.sendKeys(driver,"Notes_xpath", inc_Notes);
						logger.info("Notes Updated!!");
					}
		
					if(workInfo_type.length()>1){
		
						MethodLibrary.click(driver,"workDetailTab_xpath");
						MethodLibrary.sendKeys(driver,"workNote_xpath", "Work details update");
						MethodLibrary.click(driver,"workInfo_moreDetail");
						MethodLibrary.click(driver,"workInfoType_xpath");
						MethodLibrary.clickByValue(driver,workInfo_type);
						MethodLibrary.click(driver,"workDetailView_checkbox");
						MethodLibrary.click(driver,"workDetailAdd_btn");
						logger.info("New Work Details added!!");
		
					}
		
					MethodLibrary.click(driver,"save_btn");
		
					if(driver.getWindowHandles().size()>1){
						Set<String> handles = driver.getWindowHandles();
						System.out.println("number of windows "+handles);
						for (String handle : handles) {
							if(handle!= windowhandle) {
								driver.switchTo().window(handle);
								System.out.println("window switched "+handle);
							}
						}	
		
						driver.manage().window().maximize();
						System.out.println("Maximize!!");
						MethodLibrary.onclick(driver,"incUpdate_okBtn");
					}
					driver.switchTo().window(windowhandle);
		
		
					logger.info("Incident updated successfully!!");
					Thread.sleep(3000);
					MethodLibrary.click(driver,"searchNewInc_xpath");
		
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}

	
	public static void addVendorTask(WebDriver driver,ExtentTest logger, ArrayList<String> data) throws FileNotFoundException, IOException, InterruptedException{
		
		properties.load(new FileReader(".//Data//ObjectReository.properties"));
		String windowhandle = driver.getWindowHandle();
		WebDriverWait wait = new WebDriverWait(driver, 25);
		/*logger.info("On search incident details Page!!");
		
		WebElement frame1 =  MethodLibrary.getElement(driver, "iFrameMsg_xpath");
		driver.switchTo().frame(frame1);
		
		MethodLibrary.click(driver, "iFrameMsg_ok");*/
		//driver.switchTo().defaultContent();
		Thread.sleep(5000);
		logger.info("Adding Vendor Task for "+data.get(1)+" Incident!!");
		
		
		if(data.get(5).equalsIgnoreCase("BEACON")){
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("Vendortab_xpath"))));
			MethodLibrary.onclick(driver, "Vendortab_xpath");
			
			MethodLibrary.click(driver, "sel_initiative_xpath");
			MethodLibrary.getElementbyvlaue(driver,data.get(5)).click(); //select initiative
			logger.info("Initiative "+data.get(5)+" Selected!!");
			
			MethodLibrary.click(driver, "sel_vandorComp_xpath");
			MethodLibrary.getElementbyvlaue(driver, data.get(6)).click(); //select vandor company
			logger.info("Vandor_company "+data.get(6)+" Selected!!");
			
			MethodLibrary.click(driver, "sel_managingOp_xpath");
			MethodLibrary.getElementbyvlaue(driver, data.get(7)).click(); //select managing operator
			logger.info("Managing operator "+data.get(7)+" Selected!!");
			
			MethodLibrary.click(driver, "createbtn");
			logger.info("Creating Task!!");
			
			Set<String> handles = driver.getWindowHandles();
			System.out.println("num of handle: "+handles.size());
			for (String handle : handles) {
				if (handle != windowhandle) {
					driver.switchTo().window(handle);
				}
			}
			   Thread.sleep(2000);
			WebElement frame2 =  MethodLibrary.getElement(driver, "createVandor_iframeXpath");
		    driver.switchTo().frame(frame2);
		    MethodLibrary.click(driver, "createVandor_ok");
		    		       
		    Thread.sleep(1000);
		   // MethodLibrary.sendKeys(driver, "summary_text", "Sample Vendor task Summary.");
		   // MethodLibrary.sendKeys(driver, "notes_text", "Sample Vendore task notes.");
		    MethodLibrary.click(driver, "close_btn");// closing Vandor task
		    
		   driver.switchTo().window(windowhandle);
		    
		}
		else if(data.get(5).equalsIgnoreCase("Field Services")){
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty("Vendortab_xpath"))));
			MethodLibrary.onclick(driver, "Vendortab_xpath");
			
			MethodLibrary.click(driver, "sel_initiative_xpath");
			MethodLibrary.getElementbyvlaue(driver,data.get(5)).click(); //select initiative
			logger.info("Initiative "+data.get(5)+" Selected!!");
			
			MethodLibrary.click(driver, "sel_vandorComp_xpath");
			MethodLibrary.getElementbyvlaue(driver, data.get(6)).click(); //select vandor company //ERICSSON
			logger.info("Vandor_company "+data.get(6)+" Selected!!");
			
			MethodLibrary.click(driver, "sel_managingOp_xpath");
			MethodLibrary.getElementbyvlaue(driver, data.get(7)).click(); //select managing operator
			logger.info("Managing operator "+data.get(7)+" Selected!!");
			
			MethodLibrary.click(driver, "createbtn");
			logger.info("Creating Task!!");
			
			Set<String> handles = driver.getWindowHandles();
			System.out.println("num of handle: "+handles.size());
			for (String handle : handles) {
				if (handle != windowhandle) {
					driver.switchTo().window(handle);
				}
			}
			   Thread.sleep(2000);

			   WebElement frame2 =  MethodLibrary.getElement(driver, "ericsson_iframeXpath");
			   driver.switchTo().frame(frame2);
			   MethodLibrary.click(driver, "ericsson_PhysicalCI");
			   MethodLibrary.click(driver, "ericsson_addCI");
			   Thread.sleep(1000);
			   MethodLibrary.click(driver, "ericsson_CI");			    
			   MethodLibrary.click(driver, "ericsson_addCI");
			   MethodLibrary.click(driver, "ericsson_addBtn");		       
			    
			   Thread.sleep(1000);

			   MethodLibrary.click(driver, "ericsson_catTab");
			   MethodLibrary.onclick(driver, "ericsson_tier1");
			   MethodLibrary.click(driver, "ericsson_tier1_value");
			   MethodLibrary.onclick(driver, "ericsson_tier2");
			   MethodLibrary.click(driver, "ericsson_tier2_value");
			   
			   MethodLibrary.sendKeys(driver, "summary_text", "Sample Vendor task Summary.");
			   MethodLibrary.sendKeys(driver, "notes_text", "Sample Vendore task notes.");
			
			   MethodLibrary.click(driver, "close_btn");
			// MethodLibrary.click(driver, "sendToVandeor_btn");
			   
		}
	    	Thread.sleep(4000);
		WebElement searchNewInc = driver.findElement(By.xpath(properties.getProperty("searchNewInc_xpath")));
		searchNewInc.click();
	}
	
	
	/**
	 * modify_InC modifies Incident resolution details.
	 * 
	 * @param webdriver instance, 
	 * @param window handle, 
	 * 
	 */

	public static void modify_InC(WebDriver driver, String windowhandle)throws FileNotFoundException, IOException, InterruptedException {
			
					properties.load(new FileReader(".//Data//ObjectReository.properties"));
			
					Thread.sleep(8000);
					// ------------Modify Inc------------//
			
					Set<String> handles = driver.getWindowHandles();
			
					for (String handle : handles) {
						if (handle != windowhandle) {
							driver.switchTo().window(handle);
						}
					}
			
					/*		MethodLibrary.click(driver,"IncMadify_assigneefield_xpath");
					MethodLibrary.click(driver,"IncMadify_assignee_xpath");
					 */
					MethodLibrary.sendKeys(driver,"IncModify_resolution_xpath", "Test Text");
					MethodLibrary.getElement(driver,"IncModify_statusReason_xpath").click();
			
					MethodLibrary.click(driver,"IncModify_selfRestored_xpath");
			
					MethodLibrary.click(driver,"IncModify_rescat_tire1");
					MethodLibrary.click(driver,"IncModify_autoResolved_tire1");
			
					MethodLibrary.click(driver,"IncModify_rescat_tire2");
					MethodLibrary.click(driver,"IncModify_resolution_tire2");
			
					MethodLibrary.click(driver,"IncModify_rescat_tire3");
					MethodLibrary.click(driver,"IncModify_resolution_tire3");
			
					MethodLibrary.sendKeys(driver,"IncModify_notes_xpath", "Test");
			
					WebElement savebtn = driver.findElement(By.xpath(properties.getProperty("IncModify_savebtn_xpath")));
					action.moveToElement(savebtn).perform();
					savebtn.click();
				}
	
	/**
	 * searchIncidentdtails fetches all the details of an Incident.
	 * 
	 * @param webdriver instance, 
	 * @param incident number, 
	 * @param Test context 
	 * @return Array of incident details
	 */

	public static ArrayList<String> searchIncidentdtails(WebDriver driver,String incID, ExtentTest logger) throws FileNotFoundException,IOException {
			
					ArrayList<String> incDetails = new ArrayList<String>();
					
					properties.load(new FileReader(".//Data//ObjectReository.properties"));
					try {
			
						WebDriverWait wait = new WebDriverWait(driver, 30);
						handleAlertPresent(driver);
			
						WebElement application_tab = driver.findElement(By.id("reg_img_304316340"));
						application_tab.click();
						WebElement incidentmgmt = driver.findElement(By.xpath("//a/span[text()='Incident Management']"));
						Actions action = new Actions(driver);
						action.moveToElement(incidentmgmt).click().perform();
						WebElement searchIncident = driver.findElement(By.xpath("//*[@id='FormContainer']/div[5]/div/div[8]/div/div[3]/a/span"));
						searchIncident.click();
			
						
						MethodLibrary.sendKeys(driver,"incident_id", incID);
						MethodLibrary.sendEnterKey(driver,"incident_id", Keys.ENTER);
						//incident_id.sendKeys(Keys.ENTER);
			
						logger.info("Searching details for Incident: " + incID);
			
						// =====Get Incident details====//
			
						Thread.sleep(10000);
						handleAlertPresent(driver);
						Thread.sleep(3000);
						String company_text = MethodLibrary.getElementvalue(driver,"company_xpath", "title"); 
						// company.getAttribute("title");
						incDetails.add(company_text);
						//MethodLibrary.verifyValue(driver,softassert, "company_xpath", "title","TELEFONICA UK");
						//logger.info("Validatation point : Company value matches as expected");
						WebElement customer = driver.findElement(By.xpath(properties.getProperty("customer_xpath")));
						action.moveToElement(customer).perform();
						String customer_text = customer.getAttribute("title");
						incDetails.add(customer_text);
			
			
						WebElement summary = driver.findElement(By.xpath(properties.getProperty("summary_xpath")));
						action.moveToElement(summary).perform();
						String summary_text = summary.getAttribute("value");
						System.out.println("Summary: " + summary_text);
			
						WebElement service = driver.findElement(By.xpath(properties.getProperty("service_xpath")));
						action.moveToElement(service).perform();
						String servicetxt = service.getAttribute("title");
						incDetails.add(servicetxt);
			
			
						String CI_value = MethodLibrary.getElementvalue(driver, "CI_textarea", "title");
						incDetails.add(CI_value);
			
						WebElement impact = driver.findElement(By.xpath("//div/label[contains(text(),'Impact*')]/following-sibling::div/input"));
						action.moveToElement(impact).perform();
						// wait.until(ExpectedConditions.attributeContains(impact, "title",
						// "4-Minor/Localized"));
						String impacttxt = impact.getAttribute("title");
						incDetails.add(impacttxt);
			
						WebElement urgency = driver.findElement(By.xpath(properties.getProperty("urgency_xpath")));
						action.moveToElement(urgency).perform();
						String urgencytxt = urgency.getAttribute("title");
						incDetails.add(urgencytxt);
			
						WebElement priority = driver.findElement(By.xpath(properties.getProperty("priority_xpath")));
						action.moveToElement(priority).perform();
						String prioritytxt = priority.getAttribute("title");
						incDetails.add(prioritytxt);
			
						WebElement incidentType = driver.findElement(By.xpath(properties.getProperty("incidentType_xpath")));
						action.moveToElement(incidentType).perform();
						String incidentTypetxt = incidentType.getAttribute("title");
						incDetails.add(incidentTypetxt);
			
						WebElement assignedGroup = driver.findElement(By.xpath(properties.getProperty("assignedGroup_xpath")));
						action.moveToElement(assignedGroup).perform();
						String assignedGrouptxt = assignedGroup.getAttribute("title");
						incDetails.add(assignedGrouptxt);
			
						String assignee = MethodLibrary.getElementvalue(driver, "Assignee", "title");
						incDetails.add(assignee);
			
						WebElement status = driver.findElement(By.xpath(properties.getProperty("status_xpath")));
						action.moveToElement(status).perform();
						String statustxt = status.getAttribute("title");
						incDetails.add(statustxt);
			
						WebElement categorizationTab = driver.findElement(By.xpath(properties.getProperty("categorizationTab_xpath")));
						action.moveToElement(categorizationTab).click().build().perform();
			
						WebElement opCat_tier1 = driver.findElement(By.xpath(properties.getProperty("opCat_tier1_xpath")));
						action.moveToElement(opCat_tier1).perform();
						String opCat_tier1_txt = getAttribute(opCat_tier1, "title");
						incDetails.add(opCat_tier1_txt);
			
						WebElement opCat_tier2 = driver.findElement(By.xpath(properties.getProperty("opCat_tier2_xpath")));
						action.moveToElement(opCat_tier2).perform();
						String opCat_tier2_txt = getAttribute(opCat_tier2, "title");
						incDetails.add(opCat_tier2_txt);
			
						String opCat_tier3_txt = MethodLibrary.getElementvalue(driver, "opCat_tier3_xpath", "title");
						incDetails.add(opCat_tier3_txt);
			
						WebElement prodCat_tier1 = driver.findElement(By.xpath(properties.getProperty("prodCat_tier1_xpath")));
						action.moveToElement(prodCat_tier1).perform();
						String prodCat_tier1_txt = getAttribute(prodCat_tier1, "title");
						incDetails.add(prodCat_tier1_txt);
			
						WebElement prodCat_tier2 = driver.findElement(By.xpath(properties.getProperty("prodCat_tier2_xpath")));
						action.moveToElement(prodCat_tier2).perform();
						String prodCat_tier2_txt = getAttribute(prodCat_tier2, "title");
						incDetails.add(prodCat_tier2_txt);
			
						String prodCat_tier3_txt = MethodLibrary.getElementvalue(driver, "prodCat_tier3_xpath", "title");
						incDetails.add(prodCat_tier3_txt);
			
						String productName = MethodLibrary.getElementvalue(driver, "productName_xpath", "title");
						incDetails.add(productName);
						
						String model = MethodLibrary.getElementvalue(driver, "model_xpath", "title");
						incDetails.add(model);
						
						String manufacturer = MethodLibrary.getElementvalue(driver, "manufacturer_readonly", "value");
						incDetails.add(manufacturer);

			
						WebElement contactMethod = driver.findElement(By.xpath(properties.getProperty("contactMethod_xpath")));
						action.moveToElement(contactMethod).perform();
						String contactMethod_txt = getAttribute(contactMethod, "title");
						incDetails.add(contactMethod_txt);
			
						logger.info("Search Completed.");
						
						System.out.println("Company name " + company_text
								+ " Customer Name: " + customer_text + " Service: "
								+ servicetxt + "\n impact is " + impacttxt + " Urgency is "
								+ urgencytxt + " Priority is " + prioritytxt
								+ " Incident Type " + incidentTypetxt + "\n assignedGroup:"
								+ assignedGrouptxt + "\n OpCat_1 " + opCat_tier1_txt
								+ "\n opcat2 " + opCat_tier2_txt);
						logger.pass("Incident search sucessfull!!");
						WebElement searchNewInc = driver.findElement(By.xpath(properties.getProperty("searchNewInc_xpath")));
						searchNewInc.click();
					} catch (Exception e) {
						e.printStackTrace();
					}
					return incDetails;
				}

	
	/**
	 * saveDetails saves all the details to excel file.
	 * @param String array, 
	 * @param row number, 
	 */
	
	public static void saveDetails(ArrayList<String> details, int row)throws Exception {
			
					int i = 1;
					for (String value : details) {
			
						if (i <= details.size()) {
			
							// System.out.println("Row: "+row+" inside for loop "+i+" value is "+value);
							ExcelUtility.setCellData(value, row, i);
							i = i + 1;
			
						}
					}
			
				}
	
	/**
	 * createInc method Creates an Incident with given details
	 * 
	 * @param String array of details, 
	 * @param row number, 
	 * @return Incident number
	 */

	public static String createInc(WebDriver driver,ArrayList<String> incData, int rownum,ExtentTest logger) throws Exception  {
				
						String incident_num = null;
						SoftAssert softassert = new SoftAssert();
						
						logger.info("****Creating New Incident****");
						String windowhandle = null;
						int i = 1;
						
						if (i <= incData.size()) {
				
							MethodLibrary.sendKeys(driver,"Company_xpath", incData.get(i));
							i++;
							MethodLibrary.click(driver,"Customer_xpath");
							Thread.sleep(3000);
							MethodLibrary.sendKeys(driver,"Customer_xpath", incData.get(i));
							Thread.sleep(3000);
							MethodLibrary.click(driver,"search_icon");
							i++;
							MethodLibrary.sendKeys(driver,"Notes_xpath", incData.get(i));
							i++;
							MethodLibrary.sendKeys(driver,"Summary_field", incData.get(i));
							i++;
							
							switch (incData.get(i)) {
				
							case "1-Extensive/Widespread":
								MethodLibrary.click(driver,"Impact_field");
								MethodLibrary.click(driver,"Impact_field_1_value");
								break;
							case "2-Significant/Large":
								MethodLibrary.click(driver,"Impact_field");
								MethodLibrary.click(driver,"Impact_field_2_value");
								break;
							case "3-Moderate/Limited":
								MethodLibrary.click(driver,"Impact_field");
								MethodLibrary.click(driver,"Impact_field_3_value");
								break;
				
							case "4-Minor/Localized":
								MethodLibrary.click(driver,"Impact_field");
								MethodLibrary.click(driver,"Impact_field_4_value");
								break;
							}
							logger.info("Incident Impact value "+incData.get(i)+" selected!!");
							i++;
							switch (incData.get(i)) {
				
							case "1-Critical":
								MethodLibrary.click(driver,"Urgency_field");
								MethodLibrary.click(driver,"Urgency_field_1_value");
								break;
							case "2-High":
								MethodLibrary.click(driver,"Urgency_field");
								MethodLibrary.click(driver,"Urgency_field_2_value");
								break;
							case "3-Medium":
								MethodLibrary.click(driver,"Urgency_field");
								MethodLibrary.click(driver,"Urgency_field_3_value");
								break;
							case "4-Low":
								MethodLibrary.click(driver,"Urgency_field");
								MethodLibrary.click(driver,"Urgency_field_4_value");
								break;
							}
							logger.info("Incident Urgency value "+incData.get(i)+" selected!!");
							
							i++;
							MethodLibrary.click(driver,"Incident_type");
							MethodLibrary.click(driver,"Inc_type_value");
							i++;
				
							MethodLibrary.click(driver,"Assigned_group");
							MethodLibrary.clicknHoldbyValue(driver,incData.get(i));
							i++;
							MethodLibrary.clicknHoldbyValue(driver,incData.get(i));
							i++;
							MethodLibrary.clickByValue(driver,incData.get(i));
							logger.info("Incident Assigned Group value "+incData.get(i)+" selected!!");
							i++;
														
							Thread.sleep(500);
							MethodLibrary.click(driver,"Service_field");
							Thread.sleep(500);
							MethodLibrary.clickByValue(driver,incData.get(i));
							i++;
							addCI(driver, logger, incData.get(i));
							i++;
							if(incData.get(i).length()>1){
							MethodLibrary.click(driver,"Assignee_sel");
							Thread.sleep(1000);
							MethodLibrary.clickByValue(driver,incData.get(i));
							logger.info("Incident Assignee value "+incData.get(i)+" selected!!");
							}
							i++;
							Thread.sleep(500);
							MethodLibrary.onclick(driver,"categorizationTab_xpath");
							System.out.println("categorizationTab clicked..");
							logger.info("categorizationTab  selected!!");
							
							MethodLibrary.click(driver,"Sel_opCat_tier1");
							MethodLibrary.clickByValue(driver,incData.get(i));
				
							i++;
							MethodLibrary.click(driver,"Sel_opCat_tier2");
							MethodLibrary.clickByValue(driver,incData.get(i));
							i++;
							MethodLibrary.click(driver,"Sel_opCat_tier3");
							MethodLibrary.clickByValue(driver,incData.get(i));
							i++;
							MethodLibrary.click(driver,"Sel_prodCat_tier1");
							MethodLibrary.clickByValue(driver,incData.get(i));
							i++;
							MethodLibrary.click(driver,"Sel_prodCat_tier2");
							MethodLibrary.clickByValue(driver,incData.get(i));
							i++;
							MethodLibrary.click(driver,"Sel_prodCat_tier3");
							MethodLibrary.clickByValue(driver,incData.get(i));
							i++;
							MethodLibrary.click(driver,"Sel_productName");
							MethodLibrary.clickByValue(driver,incData.get(i));
							i++;
							MethodLibrary.click(driver,"Sel_contactMethod_xpath");
							MethodLibrary.clickByValue(driver,incData.get(i));
				
							MethodLibrary.click(driver,"resolution_categorization_xpath");
							MethodLibrary.click(driver,"resolution_prodCat_clearBtn");
							MethodLibrary.click(driver,"show_Categorization_xpath");
				
							String incNum[] = MethodLibrary.getElement(driver,"new_Inc_number").getText().split(" ");
							System.out.println("INC: "+incNum[0]);
							i++;
							windowhandle = driver.getWindowHandle();
							System.out.println("Parent window "+windowhandle);
							softassert.assertTrue(MethodLibrary.checkElementPresent(driver,"save_btn"));
							MethodLibrary.click(driver,"save_btn");
				
							if(driver.getWindowHandles().size()>1){
								Set<String> handles = driver.getWindowHandles();
								System.out.println("number of windows "+handles);
								for (String handle : handles) {
									if(handle!= windowhandle) {
										driver.switchTo().window(handle);
										System.out.println("window switched "+handle);
									}
								}	
				
								driver.manage().window().maximize();
								System.out.println("Maximize!!");
								MethodLibrary.onclick(driver,"windowOk_btn");
							}
							driver.switchTo().window(windowhandle);
				
				
							if(MethodLibrary.isElementPresent(driver,"ErrorMsg_xpath")){
				
								String errorMsg = MethodLibrary.getElement(driver,"ErrorMsg_xpath").getText();
								logger.fail("Fail to create New Incident!! "+errorMsg);
								String incNumNew[] = MethodLibrary.getElement(driver,"new_Inc_number").getText().split(" ");
								System.out.println("NewINC: "+incNumNew[0]);
								Assert.assertNotEquals(incNum[0], incNumNew[0], "Error in New Incident creation.!!"+errorMsg);
							}
				
							incident_num = incNum[0];
							System.out.println("Created Inc number: " + incident_num);
							logger.pass("**** New Incident created " + incident_num + " ****");
							ExcelUtility.setCellData(incident_num, rownum, i);
				
				
						}
						return incident_num;
				
					}


	public static void verifyNetcoolIncDetails(ExtentTest logger, WebDriver driver,String incID,ArrayList<String> incData) throws FileNotFoundException,
			IOException {
		
		
		properties.load(new FileReader(".//Data//ObjectReository.properties"));
		try {

			SoftAssert softassert = new SoftAssert();
			handleAlertPresent(driver);
			MethodLibrary.click(driver,"Applications_btn");
			MethodLibrary.click(driver,"IncidentMgmt_btn");
			WebElement searchIncident = driver.findElement(By.xpath("//*[@id='FormContainer']/div[5]/div/div[8]/div/div[3]/a/span"));
			searchIncident.click();
			Thread.sleep(5000);
			MethodLibrary.sendKeys(driver, "incident_id", incID);
			MethodLibrary.sendEnterKey(driver,"incident_id", Keys.ENTER);
			
			logger.info("Verifying details for Incident: " + incID);

			// =====Get Incident details====//

			Thread.sleep(12000);
			handleAlertPresent(driver);
			Thread.sleep(2000);
			
			int i = 2;
			
			if(i<incData.size())
			{
				String company_actualvalue = MethodLibrary.verifyValue(driver, softassert, "company_xpath", "title", incData.get(i));
				logger.info("Validatation point : Company field actual value: '"+company_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String customer_actualvalue = MethodLibrary.verifyValue(driver,softassert, "customer_xpath", "title", incData.get(i));
				logger.info("Validatation point : Customer field actual value: '"+customer_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String summary_actualvalue = MethodLibrary.verifyValue(driver,softassert, "summary_xpath", "value", incData.get(i));
				logger.info("Validatation point : Summary field actual value: '"+summary_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String service_actualvalue = MethodLibrary.verifyValue(driver,softassert, "service_xpath", "title", incData.get(i));
				logger.info("Validatation point : Service field actual value: '"+service_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String CI_actualvalue = MethodLibrary.verifyValue(driver,softassert, "CI_textarea", "title", incData.get(i));
				logger.info("Validatation point : CI field actual value: '"+CI_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String impact_actualvalue = MethodLibrary.verifyValue(driver,softassert, "Impact_field", "title", incData.get(i));
				logger.info("Validatation point : Impact field actual value: '"+impact_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String urgency_actualvalue = MethodLibrary.verifyValue(driver,softassert, "urgency_xpath", "title", incData.get(i));
				logger.info("Validatation point : Urgency field actual value: '"+urgency_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;

				String priority_actualvalue = MethodLibrary.verifyValue(driver,softassert, "priority_xpath", "title", incData.get(i));
				logger.info("Validatation point : Urgency field actual value: '"+priority_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String incidentType_actualvalue = MethodLibrary.verifyValue(driver,softassert, "incidentType_xpath", "title", incData.get(i));
				logger.info("Validatation point : IncidentType field actual value: '"+incidentType_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String assignedGroup_actualvalue = MethodLibrary.verifyValue(driver,softassert, "assignedGroup_xpath", "title", incData.get(i));
				logger.info("Validatation point : AssignedGroup field actual value: '"+assignedGroup_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String Assignee_actualvalue = MethodLibrary.verifyValue(driver,softassert, "Assignee", "title", incData.get(i));
				logger.info("Validatation point : Assignee field actual value: '"+Assignee_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String status_actualvalue = MethodLibrary.verifyValue(driver,softassert, "status_xpath", "title", incData.get(i));
				logger.info("Validatation point : Status field actual value: '"+status_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				MethodLibrary.click(driver,"categorizationTab_xpath");
				
				String opCat_tier1_actualvalue = MethodLibrary.verifyValue(driver,softassert, "opCat_tier1_xpath", "title", incData.get(i));
				logger.info("Validatation point : OpCat_tier1 field actual value: '"+opCat_tier1_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String opCat_tier2_actualvalue = MethodLibrary.verifyValue(driver,softassert, "opCat_tier2_xpath", "title", incData.get(i));
				logger.info("Validatation point : OpCat_tier2 field actual value: '"+opCat_tier2_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String opCat_tier3_actualvalue = MethodLibrary.verifyValue(driver,softassert, "opCat_tier3_xpath", "title", incData.get(i));
				logger.info("Validatation point : OpCat_tier3 field actual value: '"+opCat_tier3_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String prodCat_tier1_actualvalue = MethodLibrary.verifyValue(driver,softassert, "prodCat_tier1_xpath", "title", incData.get(i));
				logger.info("Validatation point : ProdCat_tier1 field actual value: '"+prodCat_tier1_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String prodCat_tier2_actualvalue = MethodLibrary.verifyValue(driver,softassert, "prodCat_tier2_xpath", "title", incData.get(i));
				logger.info("Validatation point : ProdCat_tier2 field actual value: '"+prodCat_tier2_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String prodCat_tier3_actualvalue = MethodLibrary.verifyValue(driver,softassert, "prodCat_tier3_xpath", "title", incData.get(i));
				logger.info("Validatation point : ProdCat_tier3 field actual value: '"+prodCat_tier3_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String productName_actualvalue = MethodLibrary.verifyValue(driver,softassert, "productName_xpath", "title", incData.get(i));
				logger.info("Validatation point : ProductName field actual value: '"+productName_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String model_actualvalue = MethodLibrary.verifyValue(driver,softassert, "model_xpath", "title", incData.get(i));
				logger.info("Validatation point : Model/Verison field actual value: '"+model_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String manufacturer_actualvalue = MethodLibrary.verifyValue(driver,softassert, "manufacturer_readonly", "value", incData.get(i));
				logger.info("Validatation point : Manufacturer field actual value: '"+manufacturer_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String contactMethod_actualvalue = MethodLibrary.verifyValue(driver,softassert, "contactMethod_xpath", "title", incData.get(i));
				logger.info("Validatation point : ContactMethod field actual value: '"+contactMethod_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				
				String externalTicketid_actualvalue = MethodLibrary.verifyValue(driver,softassert, "externalTicketid_xpath", "value", incData.get(i));
				logger.info("Validatation point : External Ticket ID field actual value: '"+externalTicketid_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				MethodLibrary.click(driver,"Netcool_tab");
				logger.info("Under Netcool Tab!!");
				
				String AlarmFirstOccurence_actualvalue = MethodLibrary.verifyValue(driver,softassert, "Netcool_AlarmFirstOccurence", "value", incData.get(i));
				logger.info("Validatation point : AlarmFirstOccurence field actual value: '"+AlarmFirstOccurence_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String ServerSerial_actualvalue = MethodLibrary.verifyValue(driver,softassert, "Netcool_ServerSerial", "value", incData.get(i));
				logger.info("Validatation point : ServerSerial field actual value: '"+ServerSerial_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String ProductCategory_actualvalue = MethodLibrary.verifyValue(driver,softassert, "Netcool_ProductCategory", "value", incData.get(i));
				logger.info("Validatation point : ProductCategory field actual value: '"+ProductCategory_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String ClassID_actualvalue = MethodLibrary.verifyValue(driver,softassert, "Netcool_ClassID", "value", incData.get(i));
				logger.info("Validatation point : ClassID field actual value: '"+ClassID_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String ClassName_actualvalue = MethodLibrary.verifyValue(driver,softassert, "Netcool_ClassName", "value", incData.get(i));
				logger.info("Validatation point : ClassName field actual value: '"+ClassName_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String AlarmKey_actualvalue = MethodLibrary.verifyValue(driver,softassert, "Netcool_AlarmKey", "value", incData.get(i));
				logger.info("AlarmKey point : ClassName field actual value: '"+AlarmKey_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
				
				String AlarmSeverity_actualvalue = MethodLibrary.verifyValue(driver,softassert, "Netcool_AlarmSeverity", "value", incData.get(i));
				logger.info("AlarmKey point : AlarmSeverity field actual value: '"+AlarmSeverity_actualvalue+"' matches with expected value: "+incData.get(i));
				i++;
			}
			softassert.assertAll();
			logger.pass("Incident Validation sucessfull!!");
			WebElement searchNewInc = driver.findElement(By.xpath(properties.getProperty("searchNewInc_xpath")));
			searchNewInc.click();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


}
