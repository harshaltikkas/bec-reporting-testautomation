package com.bec.reporting.utils;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class UtilityMethods {
	 /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(UtilityMethods.class);
    
	public static void scrollToElement(WebDriver driver, WebElement element) {
		Actions builder = new Actions(driver);
		Action mouseOver = builder.moveToElement(element).build();
		mouseOver.perform();

	}

	public static void scrollPageUp(WebDriver driver) {
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.PAGE_UP).build().perform();
	}

	public static void scrollPageDown(WebDriver driver, int count) {
		Actions builder = new Actions(driver);
		for (int i = 0; i < count; i++) {
			builder.sendKeys(Keys.ARROW_DOWN).build().perform();
		}
	}
	
	public static void processException(Exception e){
		CBTConfiguration.score = "fail";
		log.error(e.getMessage());
		Assert.fail();
	}

}
