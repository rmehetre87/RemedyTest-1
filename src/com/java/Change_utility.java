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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Change_utility {
	private static Properties properties =  new Properties();	
	
	public static String create_CR(WebDriver driver) throws FileNotFoundException, IOException, InterruptedException{
		String cqr_num = null;
		Actions action = new Actions(driver);
		
		properties.load(new FileReader(".//Data//ObjectReository.properties"));
		MethodLibrary.click(driver,"Applications_btn");
		MethodLibrary.click(driver,"ChangeMagt_btn");
		MethodLibrary.click(driver,"NewChange_btn");
		Thread.sleep(4000);
	    MethodLibrary.onclick(driver,"coordinatorGrp_xpath");
		Thread.sleep(2000);
		MethodLibrary.click(driver,"grp_sel1");
		MethodLibrary.click(driver,"grp_sel2");
		MethodLibrary.click(driver,"grp_sel3");
		
		MethodLibrary.onclick(driver,"changeCoordinator_xpath");
		Thread.sleep(2000);
		MethodLibrary.click(driver,"changeCoordinatorSel_xpath");
		
		Thread.sleep(3000);
	
		WebElement service = driver.findElement(By.xpath(properties.getProperty("service_CR_xpath")));
		
		service.click();
		//MethodLibrary.onclick(driver,"service_CR_xpath");
		Thread.sleep(3000);
		MethodLibrary.click(driver,"service_CR1_xpath");
		MethodLibrary.click(driver,"service_CR2_xpath");
		MethodLibrary.click(driver,"service_CR3_xpath");
		
		MethodLibrary.sendKeys(driver,"summary_CR_xpath", "For Testing");
		
		String windowhandle  = driver.getWindowHandle();
		Thread.sleep(3000);
		//MethodLibrary.checkEleclickable(driver,"class_CR_xpath");
		MethodLibrary.onclick(driver,"class_CR_xpath");
		
		
		//MethodLibrary.click(driver,"class_CR_xpath");
		MethodLibrary.click(driver,"class1_CR_xpath");
				
		Set<String> handles = driver.getWindowHandles();
		
		for(String handle : handles) {
			if(handle != windowhandle) {
				driver.switchTo().window(handle);
				
			}
		}
		 
		Thread.sleep(5000);
		driver.manage().window().maximize();
		MethodLibrary.sendKeys(driver,"changeType_xpath", "BAU");
		//MethodLibrary.click(driver,"changeType1_xpath");
		
		MethodLibrary.onclick(driver,"ok_btn_xpath");
		
		driver.switchTo().window(windowhandle);
		
		Thread.sleep(3000);
		
		MethodLibrary.checkEleclickable(driver,"changeReason_CR_xpath");
		MethodLibrary.onclick(driver,"changeReason_CR_xpath");
		MethodLibrary.click(driver,"changeReason1_CR_xpath");
		
		MethodLibrary.click(driver,"status_CR_xpath");
		MethodLibrary.click(driver,"status_CR1_xpath");
		Thread.sleep(3000);
		MethodLibrary.click(driver,"managerGrp_CR_xpath");
		Thread.sleep(2000);
		MethodLibrary.click(driver,"managerGrp_CR1_xpath");
		MethodLibrary.click(driver,"managerGrp_CR2_xpath");
		MethodLibrary.click(driver,"managerGrp_CR3_xpath");
	
		String woNum[] = MethodLibrary.getElement(driver,"new_CRQ_number").getText().split(" ");
		cqr_num = woNum[0];
		System.out.println("Created Inc number: "+cqr_num);
		
		MethodLibrary.click(driver,"save_CR_xpath");
		
		
		return null;
		
		
		
	}




	public static void search_CR(WebDriver driver, String crqId) {
	
		
		try {
			Thread.sleep(3000);
			MethodLibrary.click(driver,"Applications_btn");
			MethodLibrary.click(driver,"ChangeMagt_btn");
			MethodLibrary.click(driver,"Search_Change_btn");
			Thread.sleep(5000);
			MethodLibrary.onclick(driver,"search_CR_id");
			MethodLibrary.sendKeys(driver,"search_CR_id",crqId);
			MethodLibrary.sendEnterKey(driver,"search_CR_id",Keys.ENTER);	
			Thread.sleep(2000);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	
}
	
	
	
	
	
	
}